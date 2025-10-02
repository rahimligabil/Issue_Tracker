package com.gabil.tracker.common;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Instant timestamp;  
    private String path;       
    private String code;        
    private String message;     
    private List<FieldErrorItem> errors; 

    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldErrorItem {
        private String field;  
        private String message; 
}
}
