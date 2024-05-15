package com.cg.account.ledger.exception;

import com.cg.account.ledger.constants.AccountStatus;

public class InoperativeAccountException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InoperativeAccountException(String accountId, AccountStatus accountStatus) {
        super("The account:"+accountId+" is in "+accountStatus.name()+" thus no posting allowed");
    }
}
