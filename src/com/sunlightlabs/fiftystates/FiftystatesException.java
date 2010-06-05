package com.sunlightlabs.fiftystates;

public class FiftystatesException extends Exception {
	private String msg;
	private static final long serialVersionUID = 1L;
	
	public FiftystatesException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public FiftystatesException(Exception e, String msg) {
		super(e);
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	public static class NotFound extends FiftystatesException {
		private static final long serialVersionUID = 2L;
		
		public NotFound(String msg) {
			super(msg);
		}
	}
}
