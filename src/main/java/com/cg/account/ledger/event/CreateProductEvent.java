package com.cg.account.ledger.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductEvent {
	private String productId;
	private String name;
	private String price;
	private String quantity;
}
