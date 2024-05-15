package com.cg.account.ledger.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.entity.Product;
import com.cg.account.ledger.repository.CreateProducRepository;

@Component
public class ProductEventHandler {
	
	@Autowired
	CreateProducRepository createProducRepository;

	@EventHandler
	public void on(CreateProductEvent event) {
		
		Product product = new Product();
		BeanUtils.copyProperties(event, product);
		createProducRepository.save(product);
	}
}
