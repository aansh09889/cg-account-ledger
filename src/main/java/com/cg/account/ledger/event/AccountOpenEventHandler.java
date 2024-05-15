package com.cg.account.ledger.event;

import java.time.LocalDateTime;

import org.axonframework.common.StringUtils;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.entity.Account;
import com.cg.account.ledger.entity.CryptoWallet;
import com.cg.account.ledger.entity.FundWallet;
import com.cg.account.ledger.entity.HKDWallet;
import com.cg.account.ledger.entity.StockWallet;
import com.cg.account.ledger.entity.USDWallet;
import com.cg.account.ledger.model.CryptoWalletModel;
import com.cg.account.ledger.model.FundWalletModel;
import com.cg.account.ledger.model.HKDWalletModel;
import com.cg.account.ledger.model.StockWalletModel;
import com.cg.account.ledger.model.USDWalletModel;
import com.cg.account.ledger.model.WalletModel;
import com.cg.account.ledger.repository.AccountRepository;

@Component
@ProcessingGroup("account-ledger")
public class AccountOpenEventHandler {

	
    @Autowired
    private AccountRepository accountRepository;

    final Logger logger = LoggerFactory.getLogger(AccountOpenEventHandler.class);

    @EventHandler
    public void on(AccountOpenedEvent accountOpenedEvent) {
        // Create Account
    	logger.info("Inside Event handler the account for Account opened Event");
        Account account = new Account();
        account.setAccountId(accountOpenedEvent.getAccountId());
        account.setStatus(AccountStatus.OPEN);

        // Create 5 Wallets (USDWallet,HKDWallet,CryptoWallet,StockWallet
        // Adding USD Wallet
        accountOpenedEvent.getWallets().values().stream().forEach(walletModel -> {
            addWalletFromWalletModel(walletModel,account,null);
        });
        accountRepository.save(account);
    }
    
    @EventHandler
    public void on(AccountStatusChangeEvent accountStatusChangedEvent) {
    	logger.info("Inside Event handler the account Status Change");
    	Account account =new Account();
      //  Account account = accountRepository.findById(accountStatusChangedEvent.getAccountId()).orElseThrow(() -> new AccountNotFoundException(accountStatusChangedEvent.getAccountId()));
        BeanUtils.copyProperties(accountStatusChangedEvent, account);
        accountRepository.save(account);
        logger.info("Persisted the account Status");
    }
    
    private void addWalletFromWalletModel(WalletModel walletModel, Account account, String symbol) {
        switch (walletModel.getAssetType()) {
            case FIAT_USD -> {
                USDWalletModel usdWalletModel = (USDWalletModel) walletModel;
                account.getWallets().add(USDWallet.builder()
                        .account(account)
                        .walletId(usdWalletModel.getWalletId())
                        .balance(usdWalletModel.getBalance())
                        .timestamp(LocalDateTime.now())
                        .build());
                break;
            }
            case FIAT_HKD -> {
                HKDWalletModel hkdWalletModel = (HKDWalletModel) walletModel;
                account.getWallets().add(HKDWallet.builder()
                        .account(account)
                        .walletId(hkdWalletModel.getWalletId())
                        .balance(hkdWalletModel.getBalance())
                        .timestamp(LocalDateTime.now())
                        .build());
                break;
            }
            case CRYPTO -> {
                CryptoWalletModel cryptoWalletModel = (CryptoWalletModel) walletModel;
                cryptoWalletModel.getCryptoData().values().stream().forEach(cryptoData -> {
                    if(StringUtils.emptyOrNull(symbol) || (StringUtils.nonEmptyOrNull(symbol) && cryptoData.getCryptoType().getSymbol().equals(symbol))) {
                        account.getWallets().add(CryptoWallet.builder()
                                .account(account)
                                .walletId(cryptoWalletModel.getWalletId())
                                .balanceQty(cryptoData.getBalance())
                                .cryptoType(cryptoData.getCryptoType())
                                .timestamp(LocalDateTime.now())
                                .build());
                    }
                });
                break;
            }
            case STOCK -> {
                StockWalletModel stockWalletModel = (StockWalletModel) walletModel;
                stockWalletModel.getStockData().values().stream().forEach(stockData -> {
                    if(StringUtils.emptyOrNull(symbol) || (StringUtils.nonEmptyOrNull(symbol) && stockData.getStockSymbol().getSymbol().equals(symbol))) {
                        account.getWallets().add(StockWallet.builder()
                                .account(account)
                                .walletId(stockWalletModel.getWalletId())
                                .balanceQty(stockData.getBalanceQty())
                                .stockSymbol(stockData.getStockSymbol())
                                .timestamp(LocalDateTime.now())
                                .build());
                    }
                });
                break;
            }
            case FUND -> {
                FundWalletModel fundWalletModel = (FundWalletModel)walletModel;
                fundWalletModel.getFundData().values().stream().forEach(fundData -> {
                    if(StringUtils.emptyOrNull(symbol) || (StringUtils.nonEmptyOrNull(symbol) && fundData.getFundType().getSymbol().equals(symbol))) {
                        account.getWallets().add(FundWallet.builder()
                                .account(account)
                                .walletId(fundWalletModel.getWalletId())
                                .balance(fundData.getBalance())
                                .fundName(fundData.getFundType())
                                .timestamp(LocalDateTime.now())
                                .build());
                    }
                });
                break;
            }
        }
    }
    
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception e) throws Exception{
        throw e;
    }
}
