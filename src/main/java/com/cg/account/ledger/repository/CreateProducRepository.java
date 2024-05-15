package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.Product;


public interface CreateProducRepository extends JpaRepository<Product, String>{

}
