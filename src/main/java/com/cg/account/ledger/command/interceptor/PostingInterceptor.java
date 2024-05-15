package com.cg.account.ledger.command.interceptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
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

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index,command) -> {
            if(AssetPostingCommand.class.equals(command.getPayloadType())) {
            	AssetPostingCommand createPostingCommand = (AssetPostingCommand) command.getPayload();
                // Validate the Account status, if it's Open then only allow posting.
                Account account = accountRepository.findById(createPostingCommand.getAccountId()).orElseThrow(() -> new AccountNotFoundException(createPostingCommand.getAccountId()));
                if(account != null && !account.getStatus().equals(AccountStatus.OPEN)) {
                    throw new InoperativeAccountException(createPostingCommand.getAccountId(),account.getStatus());
                }
                // Validate the fromWalletId with accountId
                AccountLookup fromWalletAccountLookup = accountLookupRepository.findByAccountIdAndWalletId(createPostingCommand.getAccountId(),createPostingCommand.getFromWalletId());
                if(fromWalletAccountLookup == null) {
                    throw new IllegalStateException(
                            String.format("Account with accountId %s and walletId %s does not exist",
                                    createPostingCommand.getAccountId(),createPostingCommand.getFromWalletId())
                    );
                }
                // Validate the toWalletId with accountId.
                AccountLookup toWalletAccountLookup = accountLookupRepository.findByAccountIdAndWalletId(createPostingCommand.getAccountId(),createPostingCommand.getToWalletId());
                if(fromWalletAccountLookup == null) {
                    throw new IllegalStateException(
                            String.format("Account with accountId %s and walletId %s does not exist",
                                    createPostingCommand.getAccountId(),createPostingCommand.getToWalletId())
                    );
                }
                
                //Amount Validation 
                Wallet wallet=walletRepository.findByWalletId(fromWalletAccountLookup.getWalletId());
                BigDecimal availableAmount=accountLedgerUtil.getAmountFromAsset(wallet);
                if(createPostingCommand.getTxnAmount().compareTo(availableAmount) > 0) {
                    throw new IllegalStateException(
                            String.format("Transaction Amount is more than available amount for accountId %s and walletId %s",
                                    createPostingCommand.getAccountId(),createPostingCommand.getToWalletId())
                    );
                }
                
            }else if(AssetPostingStatusChangeCommand.class.equals(command.getPayloadType())) {
            	AssetPostingStatusChangeCommand assetPostingStatusChangeCommand = (AssetPostingStatusChangeCommand) command.getPayload();
            	// Account Validation
                Account account = accountRepository.findById(assetPostingStatusChangeCommand.getAccountId()).orElseThrow(() -> new AccountNotFoundException(assetPostingStatusChangeCommand.getAccountId()));
                if(account != null && !account.getStatus().equals(AccountStatus.OPEN)) {
                    throw new InoperativeAccountException(assetPostingStatusChangeCommand.getAccountId(),account.getStatus());
                }
              //Posting ID Validation   
               Posting posting= assetPostingRepository.findByPostingId(assetPostingStatusChangeCommand.getPostingId());
               if(posting==null) {
            	   throw new PostingIdNotFoundException(assetPostingStatusChangeCommand.getAccountId());
               }
            }
            return command;
        };
    }
}
