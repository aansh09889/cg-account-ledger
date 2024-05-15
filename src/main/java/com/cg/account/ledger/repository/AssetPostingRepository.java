package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.Posting;

public interface AssetPostingRepository extends JpaRepository<Posting,String> {
	Posting findByPostingId(String postingId);

}
