package com.gabil.tracker.common.error;

import java.net.URI;
import java.time.OffsetDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private ProblemDetail pd(ErrorCode ec,String detail,HttpServletRequest req) {
		ProblemDetail p = ProblemDetail.forStatusAndDetail(
                ec.getStatus(),
                (detail == null || detail.isBlank()) ? ec.getDefaultMessage() : detail
        );
        p.setTitle(ec.getKey());                          // "ISSUE_NOT_FOUND"
        p.setType(URI.create(ec.getTypeUri()));           // https://errors.gabil.com/issue-not-found
        p.setInstance(URI.create(req.getRequestURI()));   // "/api/...”
        p.setProperty("appCode", ec.getAppCode());        // 4000, 3001 vs.
        p.setProperty("key", ec.getKey());                // enum adı
        p.setProperty("timestamp", OffsetDateTime.now().toString());
        p.setProperty("method", req.getMethod());
        return p;
	}
	
	@ExceptionHandler(BaseException.class)
    public ProblemDetail handleDomain(BaseException ex, HttpServletRequest req) {
        ErrorCode ec = ex.getErrorCode();
        ProblemDetail p = pd(ec, ex.getMessage(), req);
        if (ex.getExtras() != null) ex.getExtras().forEach(p::setProperty);
        return p; 
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ProblemDetail handleNotReadableException(HttpMessageNotReadableException ec,HttpServletRequest req) {
		
		var code = ErrorCode.BAD_REQUEST;
		
		ProblemDetail p = pd(code,"The message is not readable",req);
		return p;
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ProblemDetail mismatchException(MethodArgumentTypeMismatchException ex,HttpServletRequest req)
	{
		var code = ErrorCode.BAD_REQUEST;
		
		ProblemDetail p = pd(code,"Type mismatch for parametr:" + ex.getName(), req);
		
		return p;
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ProblemDetail methodNotSupportedException(HttpRequestMethodNotSupportedException ex,HttpServletRequest req) {
		var code = ErrorCode.METHOD_NOT_ALLOWED;
		
		ProblemDetail p = pd(code,"Http method not supported:" + ex.getMethod(), req);
		
		return p;
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ProblemDetail typeNotSupportedException(HttpMediaTypeNotSupportedException ex,HttpServletRequest req) {
		var code = ErrorCode.UNSUPPORTED_MEDIA_TYPE;
		
		ProblemDetail p = pd(code,"Unsupported Content Type" + ex.getContentType(), req);
		
		return p;
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail dataViolationException(DataIntegrityViolationException ex,HttpServletRequest req) {
		var code = ErrorCode.CONFLICT;
		
		ProblemDetail p = pd(code,code.getDefaultMessage(), req);
		
		return p;
	}
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail dataViolationException(Exception ex,HttpServletRequest req) {
		var code = ErrorCode.INTERNAL_ERROR;
		
		ProblemDetail p = pd(code, code.getDefaultMessage(), req);
		
		return p;
	}
	
	
}
