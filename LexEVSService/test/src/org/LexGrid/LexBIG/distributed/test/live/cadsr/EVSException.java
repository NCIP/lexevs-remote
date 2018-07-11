package org.LexGrid.LexBIG.distributed.test.live.cadsr;

public class EVSException extends Exception {

	public EVSException() {
		super();
	}
	
	public EVSException(Exception e) {
		super(e);
	}
	
	public EVSException(String message, Exception e) {
		super(message, e);
	}
}
