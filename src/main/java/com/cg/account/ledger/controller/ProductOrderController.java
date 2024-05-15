package com.cg.account.ledger.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.account.ledger.command.CreateProductCommand;
import com.cg.account.ledger.model.ProductModel;

@RestController
@RequestMapping("/products")
public class ProductOrderController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public String addProduct(@RequestBody ProductModel productModel) {

		String result= commandGateway.sendAndWait(CreateProductCommand.builder().productId(UUID.randomUUID().toString())
				.name(productModel.getName()).price(productModel.getPrice()).quantity(productModel.getQuantity()).build());
		return result;
	}
}
