package com.example.Titan.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.Titan.constant.ErrorMessages;
import com.example.Titan.expections.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponseDto {
    private String message;
    private String timeZone;
    private Integer statusCode;
    private String subStatus;
    private Object data;
    private Object errorData;

    public static ApiResponseDto failed(Exception e) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorMessages.SOMETHING_WENT_WRONG)
                .build();
    }

    public static ApiResponseDto failed(CustomException ce) {
        return ApiResponseDto.builder()
                .statusCode(ce.getStatusCode().intValue())
                .message(ce.getMessage())
                .build();
    }
    public static ApiResponseDto failed(String message, Object data) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponseDto failed(String message, Object data, boolean error) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .data(error ? null : data)
                .errorData(error ? data : null)
                .build();
    }

    public static ApiResponseDto failedExpectation(String message, Object data, boolean error) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                .message(message)
                .data(error ? null : data)
                .errorData(error ? data : null)
                .build();
    }


    public static ApiResponseDto error(String message, Object data) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .errorData(data)
                .build();
    }

    public static ApiResponseDto success(Object data) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Processed.")
                .data(data)
                .build();
    }

    public static ApiResponseDto success(Object data, String timezone) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Processed.")
                .timeZone(timezone)
                .data(data)
                .build();
    }

    public static ApiResponseDto unAuthorised(CustomException ce) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ce.getMessage())
                .build();
    }

    public static ApiResponseDto failed(int statusCode, String message) {
        return ApiResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public static ApiResponseDto subStatusSuccess(Object data, String timezone,String subStatus) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .subStatus(subStatus)
                .message("Successfully Processed.")
                .timeZone(timezone)
                .data(data)
                .build();
    }

    public static ApiResponseDto subStatusSuccess(Object data,String subStatus) {
        return ApiResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .subStatus(subStatus)
                .message("Successfully Processed.")
                .data(data)
                .build();
    }
}
