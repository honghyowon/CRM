package com.study.crmProject.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Getter
public class BaseResponse<T> {

    private final DataHeader dataHeader;
    private final T data;
    private final T error;

    public BaseResponse(DataHeader dataHeader, T data, T error) {
        this.dataHeader = dataHeader;
        this.data = data;
        this.error = error;
    }

    public static <T> BaseResponse<?> ok(T data) {
        return new BaseResponse<>(DataHeader.ok(), data, null);
    }

    public static BaseResponse<?> error(HttpStatus status, Map<String, String> errors) {
        return new BaseResponse<>(DataHeader.error(status), null, errors);
    }
}