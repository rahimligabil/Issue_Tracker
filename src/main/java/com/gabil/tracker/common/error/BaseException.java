package com.gabil.tracker.common.error;

import java.util.Map;

public class BaseException extends RuntimeException{
	
	private final ErrorCode errorCode;
	
	private final Map<String, Object> extra;
	
	
	public BaseException(ErrorCode code) {
		this(code,code.getDefaultMessage(),null);
	}
	
	public BaseException(ErrorCode code, String message) {
	       this(code, message, null);
	}

	public BaseException(ErrorCode code, String defaultMessage, Map<String, Object> extra) {
		super(defaultMessage);
		this.errorCode = code;
		this.extra = extra;
		
	}
	

    public ErrorCode getErrorCode() { return errorCode; }
    public Map<String, Object> getExtras() { return extra; }
	

}
