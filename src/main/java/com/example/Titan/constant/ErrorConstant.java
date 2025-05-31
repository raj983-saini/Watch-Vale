package com.example.Titan.constant;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ErrorConstant {
    private static final String INVALID_REQUEST = "Invalid Request";
    private static final String EXPIRED = "Expired";
    private static final String ACCESS_BLOCKED = "Access blocked";
    private static final String UNEXPECTED_ERROR = "Unexpected Error";
    public static final String UNAUTHORIZED = "401";

    @Getter
    public enum ERROR_CODE {
        STATE_NOT_FOUND(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: validation failed"),
        FAILED_TO_READ_REQUEST(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Failed to read the request payload"),
        INVALID_CLIENT_ID(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: The client ID is invalid"),
        INVALID_APP_ID(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: The app ID is invalid"),
        ORIGIN_MISMATCHED(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: The merchant validation failed"),
        MERCHANT_FRONT_END_RESTRICTION(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Something went wrong. Please check your OTPLESS app configuration to allow app on this url."),
        MERCHANT_BACKEND_END_RESTRICTION(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: IP validation failed"),
        MERCHANT_CREDS_EMPTY(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: Merchant credentials are empty"),
        EMPTY_PARAMS(HttpStatus.UNAUTHORIZED, INVALID_REQUEST, "Some required parameters are missing"),
        LINK_EXPIRED(HttpStatus.UNAUTHORIZED, "Link Expired", "The OTP link you clicked has expired. Please request a new sign-in link."),
        FLOATER_CLOSED(HttpStatus.UNAUTHORIZED, "Floater Closed", "Opening a floater in the app is not allowed."),
        ACCOUNT_LOCKED(HttpStatus.LOCKED, "Account Locked", "Your account has been temporarily locked due to multiple failed sign-in attempts. Please contact support for assistance."),
        OIDC_REDIRECT_URI_MISMATCHED(HttpStatus.LOCKED, ACCESS_BLOCKED, "Authorization error: Request has been tampered with"),
        CHANNEL_TXN_ALREADY_SUCCESS(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Authorization error: The transaction auth failed."),
        ONETAP_ALL_IDENTITY_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, INVALID_REQUEST, "Authorization error: Failed to complete transaction"),
        ONETAP_TXN_NOT_FOUND(HttpStatus.EXPECTATION_FAILED, INVALID_REQUEST, "Authorization error: Failed to auth transaction"),
        TXN_NOT_FOUND(HttpStatus.EXPECTATION_FAILED, INVALID_REQUEST, "Authorization error: Failed to auth transaction"),
        ONETAP_TXN_NOT_PENDING(HttpStatus.EXPECTATION_FAILED, INVALID_REQUEST, "Authorization error: Failed to validate transaction"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR, "An unexpected error occurred. Please try again later"),
        CLIENT_INVALID_TOKEN(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid token/request Id"),
        CLIENT_INVALID_CODE(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid code"),
        CLIENT_CODE_EXPIRED(HttpStatus.UNAUTHORIZED, EXPIRED, "Request error: Code expired"),
        CLIENT_INVALID_MOBILE(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid phone number"),
        CLIENT_INVALID_EMAIL(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid email"),
        CLIENT_INVALID_ORDER_ID(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid order id"),
        CLIENT_INVALID_REDIRECT_URI(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid redirect URI"),
        CLIENT_INVALID_EXPIRY_TIME(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid expiry"),
        CLIENT_INVALID_TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED, EXPIRED, "Request error: Token is expired"),
        CLIENT_INVALID_MOBILE_CHANNEL(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid phone number's channel"),
        CLIENT_INVALID_EMAIL_CHANNEL(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid email's channel"),
        CLIENT_INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: Invalid credentials"),
        CLIENT_INVALID_CHANNEL(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid channel"),
        CLIENT_INVALID_URL(HttpStatus.BAD_REQUEST, ACCESS_BLOCKED, "Authorization error: Invalid url"),
        CLIENT_INVALID_METHOD(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid method"),
        CLIENT_INVALID_APP_ID(HttpStatus.UNAUTHORIZED, ACCESS_BLOCKED, "Authorization error: The app id is invalid"),
        CLIENT_EMPTY_OTP(HttpStatus.UNAUTHORIZED, INVALID_REQUEST, "Request error: Empty OTP"),
        INVALID_MOBILE_OR_EMAIL(HttpStatus.BAD_REQUEST, INVALID_REQUEST, "Request error: Invalid phone number or email."),
        CLIENT_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR, "An unexpected error occurred. Please try again later"),
        SESSION_NOT_PRESENT(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR, "Request error: session not present"),
        SESSION_NOT_ACTIVE(HttpStatus.UNAUTHORIZED, UNEXPECTED_ERROR, "Authorization error: session is not in active state"),
        USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, UNEXPECTED_ERROR, "Request error: user not found");


        private final HttpStatus httpStatus;
        private final String message;
        private final String description;

        ERROR_CODE(HttpStatus httpStatus, String message, String description) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.description = description;
        }
    }
}
