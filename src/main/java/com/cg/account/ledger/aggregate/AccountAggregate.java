package com.cg.account.ledger.aggregate;

import static com.cg.account.ledger.constants.CryptoType.BTC;
import static com.cg.account.ledger.constants.CryptoType.ETH;
import static com.cg.account.ledger.constants.CryptoType.SHIB;
import static com.cg.account.ledger.constants.FundType.AIA_MID_CAP;
import static com.cg.account.ledger.constants.FundType.CITI_LARGE_CAP;
import static com.cg.account.ledger.constants.FundType.HSBC_SMALL_CAP;
import static com.cg.account.ledger.constants.StockSymbol.CAPGEMINI;
import static com.cg.account.ledger.constants.StockSymbol.CITI;
import static com.cg.account.ledger.constants.StockSymbol.HSBC;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.account.ledger.command.AccountStatusCommand;
import com.cg.account.ledger.command.OpenAccountCommand;
import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.constants.FiatCurrency;
import com.cg.account.ledger.event.AccountOpenedEvent;
import com.cg.account.ledger.event.AccountStatusChangeEvent;
import com.cg.account.ledger.model.CryptoDataModel;
import com.cg.account.ledger.model.CryptoWalletModel;
import com.cg.account.ledger.model.FundDataModel;
import com.cg.account.ledger.model.FundWalletModel;
import com.cg.account.ledger.model.HKDWalletModel;
import com.cg.account.ledger.model.StockDataModel;
import com.cg.account.ledger.model.StockWalletModel;
import com.cg.account.ledger.model.USDWalletModel;
import com.cg.account.ledger.model.WalletModel;
import com.cg.account.ledger.repository.AccountRepository;

@Aggregate
public class AccountAggregate {

	   	@AggregateIdentifier
	    private String accountId;
	   	
	    private AccountStatus accountStatus;
	    private Map<String, WalletModel> wallets = new HashMap<>();
	    
	    @Autowired
	    private AccountRepository accountRepository;

	    private static final Logger logger = LoggerFactory.getLogger(AccountAggregate.class);

	    public AccountAggregate() {}
	    
	    @CommandHandler
	    public AccountAggregate(OpenAccountCommand openAccountCommand, AccountRepository accountRepository) {
	        logger.info("Open Account Command command invoked command handler...");
	        this.accountRepository = accountRepository;
	        // Create Account and wallets.

	        openAccount(openAccountCommand);
	        apply(AccountOpenedEvent.builder().wallets(this.wallets).accountId(openAccountCommand.getAccountId()).status(AccountStatus.OPEN).build());
	    }
	    
	    @EventSourcingHandler
	    public void on(AccountOpenedEvent accountOpenedEvent) {
	        logger.info("Account Opened Event get called Event Sourcing...."+accountOpenedEvent.getAccountId());
	        this.accountId=accountOpenedEvent.getAccountId();
	        this.accountStatus=accountOpenedEvent.getStatus();
	        this.wallets = accountOpenedEvent.getWallets();
	    }
	    
	    @CommandHandler
	    public void handle(AccountStatusCommand accountStatusCommand) {
	    	logger.info("Account Status Change command handler ...."+accountStatusCommand.getAccountId());
	    	apply(AccountStatusChangeEvent.builder().accountId(accountStatusCommand.getAccountId()).status(accountStatusCommand.getStatus()).build());
	    }
	    
	    @EventSourcingHandler
	    public void on(AccountStatusChangeEvent accountStatusChangeEvent) {
	        logger.info("Account Status Change get called in event sourcing...."+accountStatusChangeEvent.getAccountId());
	        this.accountId=accountStatusChangeEvent.getAccountId();
	        this.accountStatus=accountStatusChangeEvent.getStatus();
	     //   this.wallets = accountOpenedEvent.getWallets();
	    }
	    
	    private void openAccount(OpenAccountCommand openAccountCommand) {
	        // Create Account
	        // Create 5 Wallets (USDWallet,HKDWallet,CryptoWallet,StockWallet
	        // Adding USD Wallet
	        String usdWalletId = UUID.randomUUID().toString();
	        this.wallets.put(usdWalletId, USDWalletModel.builder()
	                .accountId(openAccountCommand.getAccountId())
	                .walletId(usdWalletId.toString())

	                .balance(FiatCurrency.USD.getInitialBalance()).build());

	        // Adding HKD Wallet
	        String hkdWalletId = UUID.randomUUID().toString();
	        this.wallets.put(hkdWalletId, HKDWalletModel.builder()
	                .accountId(openAccountCommand.getAccountId())
	                .walletId(hkdWalletId.toString())
	                .balance(FiatCurrency.HKD.getInitialBalance()).build());

	        // Adding the Crypto Wallet
	        String cryptoWalletId = UUID.randomUUID().toString();
	        this.wallets.put(cryptoWalletId, CryptoWalletModel.builder()
	                .accountId(openAccountCommand.getAccountId())
	                .walletId(cryptoWalletId.toString())
	                .cryptoData(getInitialCryptoDataModel())
	                .build());

	        // Adding the Stock Wallet
	        String stockWalletId = UUID.randomUUID().toString();
	        this.wallets.put(stockWalletId, StockWalletModel.builder()
	                .accountId(openAccountCommand.getAccountId())
	                .walletId(stockWalletId.toString())
	                .stockData(getInitialStockDataModel())
	                .build());

	        // Adding the Fund Wallet
	        String fundWalletId = UUID.randomUUID().toString();
	        this.wallets.put(fundWalletId,FundWalletModel.builder()
	                .accountId(openAccountCommand.getAccountId())
	                .walletId(fundWalletId)
	                .fundData(getInitialFundDataModel())
	                .build());

	        logger.info("AccountId "+openAccountCommand.getAccountId()+ " all Wallets been created...");
	    }
	
	    /**
	     * Create Initial CryptoDataModelMap
	     * @return
	     */
	    private Map<String,CryptoDataModel> getInitialCryptoDataModel() {
	        Map<String,CryptoDataModel> cryptoDataModelap = new HashMap<>();
	        cryptoDataModelap.put(BTC.getSymbol(),CryptoDataModel.builder()
	                .cryptoType(BTC)
	                .balance(BTC.getInitialBalance())
	                .build());
	        cryptoDataModelap.put(ETH.getSymbol(),CryptoDataModel.builder()
	                .cryptoType(ETH)
	                .balance(ETH.getInitialBalance())
	                .build());
	        cryptoDataModelap.put(SHIB.getSymbol(),CryptoDataModel.builder()
	                .cryptoType(SHIB)
	                .balance(SHIB.getInitialBalance())
	                .build());
	        return cryptoDataModelap;
	    }

	    /**
	     * getInitialStockDataModel initialize the StockDataModel
	     * @return
	     */
	    private Map<String,StockDataModel> getInitialStockDataModel() {
	        Map<String,StockDataModel> stockDataModelMap = new HashMap<>();
	        stockDataModelMap.put(CAPGEMINI.getSymbol(),StockDataModel.builder()
	                .stockSymbol(CAPGEMINI)
	                .balanceQty(CAPGEMINI.getInitialBalance())
	                .build());
	        stockDataModelMap.put(HSBC.getSymbol(),StockDataModel.builder()
	                .stockSymbol(HSBC)
	                .balanceQty(HSBC.getInitialBalance())
	                .build());
	        stockDataModelMap.put(CITI.getSymbol(),StockDataModel.builder()
	                .stockSymbol(CITI)
	                .balanceQty(CITI.getInitialBalance())
	                .build());
	        return stockDataModelMap;
	    }

	    /**
	     * getInitialFundDataModel provide initial FundDataModel
	     * @return
	     */
	    private Map<String,FundDataModel> getInitialFundDataModel() {
	        Map<String,FundDataModel> fundTypeFundDataModelMap = new HashMap<>();
	        fundTypeFundDataModelMap.put(HSBC_SMALL_CAP.getSymbol(), FundDataModel.builder()
	                .fundType(HSBC_SMALL_CAP)
	                .balance(HSBC_SMALL_CAP.getInitialBalance())
	                .build());
	        fundTypeFundDataModelMap.put(AIA_MID_CAP.getSymbol(), FundDataModel.builder()
	                .fundType(AIA_MID_CAP)
	                .balance(AIA_MID_CAP.getInitialBalance())
	                .build());
	        fundTypeFundDataModelMap.put(CITI_LARGE_CAP.getSymbol(), FundDataModel.builder()
	                .fundType(CITI_LARGE_CAP)
	                .balance(CITI_LARGE_CAP.getInitialBalance())
	                .build());
	        return fundTypeFundDataModelMap;
	    }
}
