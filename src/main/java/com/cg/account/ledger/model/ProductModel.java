package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductModel {

	private String name;
	private String price;
	private String quantity;
}
