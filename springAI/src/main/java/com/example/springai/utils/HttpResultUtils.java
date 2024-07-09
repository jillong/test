package com.example.springai.utils;

import com.example.springai.common.HttpPagedResponse;
import com.example.springai.common.HttpResponse;
import com.example.springai.common.PagedResult;
import org.springframework.http.HttpStatus;

public class HttpResultUtils {

    public static <T> HttpResponse<T> createHttpResult(T data, String message, HttpStatus httpStatus) {
        return createHttpResult(null, data, message, httpStatus);
    }

    public static <T> HttpResponse<T> createHttpResult(String code, String message, HttpStatus httpStatus) {
        return createHttpResult(code, null, message, httpStatus);
    }

    public static <T> HttpResponse<T> createHttpResult(String message, HttpStatus httpStatus) {
        return createHttpResult(null, null, message, httpStatus);
    }

    public static <T> HttpResponse<T> createHttpResult(String code, T data, String message, HttpStatus httpStatus) {
        return new HttpResponse<>(code, data, message, httpStatus.value());
    }

    public static <T> HttpPagedResponse<T> createHttpPagedResult(String message, HttpStatus httpStatus) {
        return createHttpPagedResult(null, null, message, httpStatus);
    }


    public static <T> HttpPagedResponse<T> createHttpPagedResult(
            PagedResult<T> data, String message, HttpStatus httpStatus) {
        return createHttpPagedResult(null, data, message, httpStatus);
    }

    public static <T> HttpPagedResponse<T> createHttpPagedResult(
            String code, PagedResult<T> data, String message, HttpStatus httpStatus) {
        return new HttpPagedResponse<>(code, data, message, httpStatus.value());
    }

}