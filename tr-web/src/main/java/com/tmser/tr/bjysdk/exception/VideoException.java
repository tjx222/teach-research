package com.tmser.tr.bjysdk.exception;

import com.tmser.tr.bjysdk.exception.ServiceException;

/**
 * 当访问对象存储服务失败时抛出该异常类实例。
 */
public class VideoException extends ServiceException {

    private static final long serialVersionUID = -1979779664334663173L;
    
    private String resourceType;
    private String header;
    private String method;
    
    public VideoException() {
        super();
    }

    
    public VideoException(String errcode) {
        super(errcode);
    }

    public VideoException(String errcode, Throwable cause) {
        super(errcode, cause);
    }

    public VideoException(String message, String errorCode,
            String hostId, String header, String resourceType, String method) {
        this(message, errorCode, hostId, header, resourceType, method, null, null);
    }
    
    public VideoException(String errcode, String errorCode, 
    	String hostId, String header, String resourceType, String method, Throwable cause) {
        this(errcode, errorCode, hostId, header, resourceType, method, null, cause);
    }
    
    public VideoException(String errorMessage, String errorCode, 
            String hostId, String header, String resourceType, String method, String rawResponseError) {
        this(errorMessage, errorCode, hostId, header, resourceType, method, rawResponseError, null);
    }
    
    public VideoException(String errorMessage, String errorCode,
            String hostId, String header, String resourceType, String method, String rawResponseError, Throwable cause) {
        super(errorMessage, errorCode, hostId, rawResponseError, cause);
        this.resourceType = resourceType;
        this.header = header;
        this.method = method;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getHeader() {
        return header;
    }

    public String getMethod() {
        return method;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() 
                + (resourceType == null ? "" : "\n[ResourceType]: " + resourceType)
                + (header == null ? "" : "\n[Header]: " + header)
                + (method == null ? "" : "\n[Method]: " + method);
    }
}