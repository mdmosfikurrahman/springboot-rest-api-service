package com.spring.restapi.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    @JsonFormat(pattern = "dd MMMM, yyyy hh:mm a")
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private T error;

    public RestResponse(int status, String message, T data, T error) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
    public static <T> RestResponse<T> success(int status, String message, T data) {
        return new RestResponse<>(status, message, data, null);
    }

    public static <T> RestResponse<T> error(int status, String message, T error) {
        return new RestResponse<>(status, message, null, error);
    }
}
