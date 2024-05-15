package com.cg.account.ledger.util;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.entity.CryptoWallet;
import com.cg.account.ledger.entity.FundWallet;
import com.cg.account.ledger.entity.HKDWallet;
import com.cg.account.ledger.entity.Posting;
import com.cg.account.ledger.entity.StockWallet;
import com.cg.account.ledger.entity.INRWallet;
import com.cg.account.ledger.entity.Wallet;
import com.cg.account.ledger.model.WalletModel;
import com.cg.account.ledger.repository.CryptoRepository;
import com.cg.account.ledger.repository.FiatHKDRepository;
import com.cg.account.ledger.repository.FiatUSDRepository;
import com.cg.account.ledger.repository.FundRepository;
import com.cg.account.ledger.repository.StockRepository;
import com.cg.account.ledger.repository.WalletRepository;

@Component
public class AccountLedgerUtil {

	@Autowired
	CryptoRepository cryptoRepository;

	@Autowired
	FiatHKDRepository fiatHKDRepository;

	@Autowired
	FiatUSDRepository fiatUSDRepository;

	@Autowired
	FundRepository fundRepository;

	@Autowired
	StockRepository stockRepository;

	@Autowired
	WalletRepository walletRepository;

	private static final Logger logger = LoggerFactory.getLogger(AccountLedgerUtil.class);

	public BigDecimal getAmountFromAsset(Wallet wallet) {
		logger.info("Amount check validaiton Start : " + wallet.getWalletId().toString() + " id : " + wallet.getId());
		BigDecimal amount = new BigDecimal(0);
		switch (wallet.getAssetType().toString().toUpperCase()) {
		case "FIAT_HKD":
			Optional<HKDWallet> hkdWallet = fiatHKDRepository.findById(wallet.getId());
			amount = hkdWallet.get().getBalance();
			break;
		case "FIAT_INR":
			Optional<INRWallet> usdWallet = fiatUSDRepository.findById(wallet.getId());
			amount = usdWallet.get().getBalance();
			break;
		case "STOCK":
			Optional<StockWallet> stockWallet = stockRepository.findById(wallet.getId());
			amount = stockWallet.get().getBalanceQty();
			break;
		case "CRYPTO":
			Optional<CryptoWallet> cryptoWallet = cryptoRepository.findById(wallet.getId());
			amount = cryptoWallet.get().getBalanceQty();
			break;
		case "FUND":
			Optional<FundWallet> fundWallet = fundRepository.findById(wallet.getId());
			amount = fundWallet.get().getBalance();
			break;
		default:
			throw new IllegalArgumentException(
					"Assest is not available : Asset Type  " + wallet.getAssetType().toString().toUpperCase());
		}
		logger.info("Amount available" + amount + " in wallet ID " + wallet.getWalletId());
		return amount;
	}

	public void updateAsset(Posting posting) {
		logger.info("Asset Table Updating ");
		Wallet fromWallet = walletRepository.findByWalletId(posting.getFromWalletId());
		logger.info("From Amout starts updating for wallet id " + posting.getFromWalletId() + " for asset Type : "
				+ fromWallet.getAssetType().toString());
		switch (fromWallet.getAssetType().toString().toUpperCase()) {
		case "FIAT_HKD":
			Optional<HKDWallet> hkdWallet = fiatHKDRepository.findById(fromWallet.getId());
			hkdWallet.get().setBalance(hkdWallet.get().getBalance().subtract(posting.getTxnAmount()));
			fiatHKDRepository.save(hkdWallet.get());
			break;
		case "FIAT_INR":
			Optional<INRWallet> usdWallet = fiatUSDRepository.findById(fromWallet.getId());
			usdWallet.get().setBalance(usdWallet.get().getBalance().subtract(posting.getTxnAmount()));
			fiatUSDRepository.save(usdWallet.get());
			break;
		case "STOCK":
			Optional<StockWallet> stockWallet = stockRepository.findById(fromWallet.getId());
			stockWallet.get().setBalanceQty(stockWallet.get().getBalanceQty().subtract(posting.getTxnAmount()));
			stockRepository.save(stockWallet.get());
			break;
		case "CRYPTO":
			Optional<CryptoWallet> cryptoWallet = cryptoRepository.findById(fromWallet.getId());
			cryptoWallet.get().setBalanceQty(cryptoWallet.get().getBalanceQty().subtract(posting.getTxnAmount()));
			cryptoRepository.save(cryptoWallet.get());
			break;
		case "FUND":
			Optional<FundWallet> fundWallet = fundRepository.findById(fromWallet.getId());
			fundWallet.get().setBalance(fundWallet.get().getBalance().subtract(posting.getTxnAmount()));
			fundRepository.save(fundWallet.get());
			break;
		default:
			throw new IllegalArgumentException(
					"Assest is not available : Asset Type  " + fromWallet.getAssetType().toString().toUpperCase());
		}

		Wallet toWallet = walletRepository.findByWalletId(posting.getToWalletId());
		logger.info("To Amout starts updating for wallet id " + posting.getFromWalletId() + " for asset Type : "
				+ toWallet.getAssetType().toString());
		switch (toWallet.getAssetType().toString().toUpperCase()) {
		case "FIAT_HKD":
			Optional<HKDWallet> hkdWallet = fiatHKDRepository.findById(toWallet.getId());
			hkdWallet.get().setBalance(hkdWallet.get().getBalance().add(posting.getTxnAmount()));
			fiatHKDRepository.save(hkdWallet.get());
			break;
		case "FIAT_INR":
			Optional<INRWallet> usdWallet = fiatUSDRepository.findById(toWallet.getId());
			usdWallet.get().setBalance(usdWallet.get().getBalance().add(posting.getTxnAmount()));
			fiatUSDRepository.save(usdWallet.get());
			break;
		case "STOCK":
			Optional<StockWallet> stockWallet = stockRepository.findById(toWallet.getId());
			stockWallet.get().setBalanceQty(stockWallet.get().getBalanceQty().add(posting.getTxnAmount()));
			stockRepository.save(stockWallet.get());
			break;
		case "CRYPTO":
			Optional<CryptoWallet> cryptoWallet = cryptoRepository.findById(toWallet.getId());
			cryptoWallet.get().setBalanceQty(cryptoWallet.get().getBalanceQty().add(posting.getTxnAmount()));
			cryptoRepository.save(cryptoWallet.get());
			break;
		case "FUND":
			Optional<FundWallet> fundWallet = fundRepository.findById(toWallet.getId());
			fundWallet.get().setBalance(fundWallet.get().getBalance().add(posting.getTxnAmount()));
			fundRepository.save(fundWallet.get());
			break;
		default:
			throw new IllegalArgumentException(
					"Assest is not available : Asset Type  " + toWallet.getAssetType().toString().toUpperCase());
		}

		logger.info("Asset Table Updating Complete ");

	}

}
