package com.cg.account.ledger.command.interceptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.command.AssetPostingCommand;
import com.cg.account.ledger.command.AssetPostingStatusChangeCommand;
import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.entity.Account;
import com.cg.account.ledger.entity.AccountLookup;
import com.cg.account.ledger.entity.Posting;
import com.cg.account.ledger.entity.Wallet;
import com.cg.account.ledger.exception.AccountNotFoundException;
import com.cg.account.ledger.exception.InoperativeAccountException;
import com.cg.account.ledger.exception.PostingIdNotFoundException;
import com.cg.account.ledger.repository.AccountLookupRepository;
import com.cg.account.ledger.repository.AccountRepository;
import com.cg.account.ledger.repository.AssetPostingRepository;
import com.cg.account.ledger.repository.WalletRepository;
import com.cg.account.ledger.util.AccountLedgerUtil;

/**
 * PostingInterceptor interceptor is validating the CreatePostingCommand with below rules.
 * 1. AccountLookup entity must contains the accountId supplied to the CreatePostingCommand
 * 2. fromWalletId, toWalletId must be exist in the WalletLookup entity
 */

@Component
public class PostingInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Autowired
    private AccountLookupRepository accountLookupRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired 
    private WalletRepository walletRepository;
    
    @Autowired
    private AccountLedgerUtil accountLedgerUtil;
    
    @Autowired
    AssetPostingRepository assetPostingRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostingInterceptor.class);
    
    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index,command) -> {
        	 logger.info("Post Intercepted command: " + command.getPayloadType());
            if(AssetPostingCommand.class.equals(command.getPayloadType())) {
            	 logger.info("Asset Posint Command Interceptor: " + command.getPayloadType());
            	AssetPostingCommand createPostingCommand = (AssetPostingCommand) command.getPayload();
                // Validate the Account status, if it's Open then only allow posting.
                Account account = accountRepository.findById(createPostingCommand.getAccountId()).orElseThrow(() -> new AccountNotFoundException(createPostingCommand.getAccountId()));
                if(account != null && !account.getStatus().equals(AccountStatus.OPEN)) {
                    throw new InoperativeAccountException(createPostingCommand.getAccountId(),account.getStatus());
                }
                // Validate the fromWalletId with accountId
                AccountLookup fromWalletAccountLookup = accountLookupRepository.findByAccountIdAndWalletId(createPostingCommand.getAccountId(),createPostingCommand.getFromWalletId());
                if(fromWalletAccountLookup == null) {
                	logger.error("fromWalletAccountLookup not avaliable  : " + createPostingCommand.getFromWalletId());
                    throw new IllegalStateException(
                            String.format("Account with accountId %s and walletId %s does not exist",
                                    createPostingCommand.getAccountId(),createPostingCommand.getFromWalletId())
                    );
                }
                // Validate the toWalletId with accountId.
                AccountLookup toWalletAccountLookup = accountLookupRepository.findByAccountIdAndWalletId(createPostingCommand.getAccountId(),createPostingCommand.getToWalletId());
                if(toWalletAccountLookup == null) {
                	logger.error("toWalletAccountLookup not avaliable  : " + createPostingCommand);
                    throw new IllegalStateException(
                            String.format("Account with accountId %s and walletId %s does not exist",
                                    createPostingCommand.getAccountId(),createPostingCommand.getToWalletId())
                    );
                }
                
                //Amount Validation 
                Wallet wallet=walletRepository.findByWalletId(fromWalletAccountLookup.getWalletId());
                BigDecimal availableAmount=accountLedgerUtil.getAmountFromAsset(wallet);
                if(createPostingCommand.getTxnAmount().compareTo(availableAmount) > 0) {
                	logger.error("Transaction Amount is more than available amount for accountId for wallet id  : " + fromWalletAccountLookup.getWalletId());
                    throw new IllegalStateException(
                            String.format("Transaction Amount is more than available amount for accountId %s and walletId %s",
                                    createPostingCommand.getAccountId(),createPostingCommand.getToWalletId())
                    );
                }
                
            }else if(AssetPostingStatusChangeCommand.class.equals(command.getPayloadType())) {
            	 logger.info("Asset Posting Status Change Command: " + command.getPayloadType());
            	AssetPostingStatusChangeCommand assetPostingStatusChangeCommand = (AssetPostingStatusChangeCommand) command.getPayload();
            	// Account Validation
                Account account = accountRepository.findById(assetPostingStatusChangeCommand.getAccountId()).orElseThrow(() -> new AccountNotFoundException(assetPostingStatusChangeCommand.getAccountId()));
                if(account != null && !account.getStatus().equals(AccountStatus.OPEN)) {
                	logger.error("Inoperative Account Exception for Asset Posting Status Change : " + assetPostingStatusChangeCommand.getAccountId());
                    throw new InoperativeAccountException(assetPostingStatusChangeCommand.getAccountId(),account.getStatus());
                }
              //Posting ID Validation   
               Posting posting= assetPostingRepository.findByPostingId(assetPostingStatusChangeCommand.getPostingId());
               if(posting==null) {
            	   logger.error("Posting Id Not Found Exception: " + assetPostingStatusChangeCommand.getAccountId());
            	   throw new PostingIdNotFoundException(assetPostingStatusChangeCommand.getAccountId());
               }
            }
            return command;
        };
    }
}
