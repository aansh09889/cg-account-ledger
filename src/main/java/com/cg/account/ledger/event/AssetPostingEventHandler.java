package com.cg.account.ledger.event;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.entity.Posting;
import com.cg.account.ledger.repository.AssetPostingRepository;
import com.cg.account.ledger.util.AccountLedgerUtil;

@Component
@ProcessingGroup("account-ledger")
public class AssetPostingEventHandler {

	@Autowired
	AssetPostingRepository assetPostingRepository;
	
	@Autowired
	AccountLedgerUtil accountLedgerUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(AssetPostingEventHandler.class);
	
	@EventHandler
	public void on(AssetPostingEvent assetPostingEvent) {
		logger.info("Asset Posting request came to Event handler");
		Posting posting= new Posting();
		BeanUtils.copyProperties(assetPostingEvent, posting);
		accountLedgerUtil.updateAsset(posting);
		assetPostingRepository.save(posting);	
		logger.info("Asset Posting request Request Completed ");
	}
	
	@EventHandler
	public void on(AssetPostingStatusChangeEvent assetPostingStatusChangeEvent) {
		logger.info("Asset Update Post request came to Event handler");
		Optional<Posting> posting= assetPostingRepository.findById(assetPostingStatusChangeEvent.getPostingId());
		posting.get().setPostingStatus(assetPostingStatusChangeEvent.getPostingStatus());	
		assetPostingRepository.save(posting.get());	
		logger.info("Asset Update Post request Request Completed ");
	}
	
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception e) throws Exception{
        throw e;
    }
}
