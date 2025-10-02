package com.gabil.tracker.common.error;

import org.springframework.http.HttpStatus;
public enum ErrorCode {

 
    BAD_REQUEST            (HttpStatus.BAD_REQUEST,            1000, "Bad request.",                 "/bad-request"),
    VALIDATION_FAILED      (HttpStatus.UNPROCESSABLE_ENTITY,   1001, "Validation failed.",           "/validation-failed"),
    METHOD_NOT_ALLOWED     (HttpStatus.METHOD_NOT_ALLOWED,     1002, "HTTP method not allowed.",     "/method-not-allowed"),
    UNSUPPORTED_MEDIA_TYPE (HttpStatus.UNSUPPORTED_MEDIA_TYPE, 1003, "Unsupported media type.",      "/unsupported-media-type"),
    INTERNAL_ERROR         (HttpStatus.INTERNAL_SERVER_ERROR,  1500, "Unexpected server error.",     "/internal-error"),
    UNAUTHORIZED           (HttpStatus.UNAUTHORIZED,           2000, "Authentication required.",     "/unauthorized"),
    FORBIDDEN              (HttpStatus.FORBIDDEN,              2001, "Access is forbidden.",         "/forbidden"),
    CONFLICT               (HttpStatus.CONFLICT,               3000, "Resource conflict.",           "/conflict"),
    DUPLICATE_KEY          (HttpStatus.CONFLICT,               3001, "Duplicate key.",               "/duplicate-key"),
    ISSUE_NOT_FOUND        (HttpStatus.NOT_FOUND,              4000, "Issue not found.",             "/issue-not-found"),
    ISSUE_ALREADY_CLOSED   (HttpStatus.CONFLICT,               4001, "Issue is already closed.",     "/issue-already-closed"),
    PROJECT_NOT_FOUND      (HttpStatus.NOT_FOUND,              4100, "Project not found.",           "/project-not-found"),
    PROJECT_ARCHIVED       (HttpStatus.CONFLICT,               4101, "Project is archived.",         "/project-archived"),
    USER_NOT_FOUND         (HttpStatus.NOT_FOUND,              4200, "User not found.",              "/user-not-found"),
    USER_INACTIVE          (HttpStatus.CONFLICT,               4201, "User is inactive.",            "/user-inactive"),
    COMMENT_NOT_FOUND      (HttpStatus.NOT_FOUND,              4300, "Comment not found.",           "/comment-not-found");
	
	
    private final HttpStatus httpStatus;     
    private final int        appCode;         
    private final String     defaultMessage;  
    private final String     typePath;      

    ErrorCode(HttpStatus httpStatus, int appCode, String defaultMessage, String typePath) {
        this.httpStatus = httpStatus;
        this.appCode = appCode;
        this.defaultMessage = defaultMessage;
        this.typePath = typePath;
    }
    
    private static final String BASE_TYPE_URI = "https://errors.gabil.com";

    public String getKey() { return name(); }               // e.g. ISSUE_NOT_FOUND
    public String getTypeUri() { return BASE_TYPE_URI + typePath; }  // full URI
    public HttpStatus getStatus() { return httpStatus; }
    public int getAppCode() { return appCode; }
    public String getDefaultMessage() { return defaultMessage; }
    public int httpStatusCode() { return httpStatus.value(); }
}