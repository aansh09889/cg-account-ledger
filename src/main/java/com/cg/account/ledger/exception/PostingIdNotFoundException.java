package com.cg.account.ledger.exception;

public class PostingIdNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostingIdNotFoundException(String postingId) {
        super("Post ID " + postingId + " not found.");
    }
}
