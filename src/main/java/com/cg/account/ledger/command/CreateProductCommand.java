package com.cg.account.ledger.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductCommand {

	@TargetAggregateIdentifier
	private String productId;
	private String name;
	private String price;
	private String quantity;
}
