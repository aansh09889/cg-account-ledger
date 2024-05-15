package com.cg.account.ledger.exception;

public class AccountNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String accountId) {
        super("Account with ID " + accountId + " not found.");
    }
}
