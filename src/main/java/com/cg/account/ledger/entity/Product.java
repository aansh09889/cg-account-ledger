package com.cg.account.ledger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
	
	@Id
	private String productId;
	private String name;
	private String price;
	private String quantity;
	
}
