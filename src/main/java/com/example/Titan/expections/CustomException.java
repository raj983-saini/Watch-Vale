package com.example.Titan.expections;


import com.example.Titan.constant.ErrorConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends Exception {

    Integer statusCode;
    String message;
    String debugMessage;
    ErrorConstant.ERROR_CODE errorCode;

    public CustomException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode.value();
        this.message = message;
        this.debugMessage = message;
    }

    public CustomException(ErrorConstant.ERROR_CODE errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
        this.debugMessage = errorCode.getDescription();
        this.errorCode = errorCode;
    }

    public CustomException(ErrorConstant.ERROR_CODE errorCode, String debugMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.statusCode = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
        this.debugMessage = debugMessage;
    }

    public CustomException(String message) {
        super(message);
        this.statusCode = HttpStatus.UNPROCESSABLE_ENTITY.value();
        this.message = message;
    }

    public CustomException(HttpStatus statusCode, String message, String debugMessage) {
        super(message);
        this.statusCode = statusCode.value();
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public CustomException(int statusCode, String message, String debugMessage) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.debugMessage = debugMessage;
    }
}