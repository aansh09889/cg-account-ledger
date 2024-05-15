package com.cg.account.ledger.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.cg.account.ledger.command.CreateProductCommand;
import com.cg.account.ledger.event.CreateProductEvent;

@Aggregate
public class CreateProductAggregate {

	@AggregateIdentifier
	private String productId;
	private String name;
	private String price;
	private String quantity;

	public CreateProductAggregate() {
	}

	@CommandHandler
	public CreateProductAggregate(CreateProductCommand createProductCommand) {
		CreateProductEvent event = new CreateProductEvent();
		BeanUtils.copyProperties(createProductCommand, event);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(CreateProductEvent createProductEvent) {
		this.productId = createProductEvent.getProductId();
		this.name = createProductEvent.getName();
		this.price = createProductEvent.getPrice();
		this.quantity = createProductEvent.getQuantity();
	}

}
