package com.sm360.advertising.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResponse implements Serializable {

    private Object data;
    private String message;
    private RestStatus status=RestStatus.Success;

    public enum RestStatus {
        Success, Failed
    }

}
