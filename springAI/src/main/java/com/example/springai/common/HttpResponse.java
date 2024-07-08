package com.example.springai.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class HttpResponse<T> implements Serializable {

    private String code;

    private T data;

    private String message;

    @JsonIgnore
    private int httpStatus;

    public HttpResponse(String code, T data, String message, int httpStatus) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpResponse(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }



}