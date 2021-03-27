package com.boost.voucherpool.util;

import com.boost.voucherpool.model.ValidVouchersWithName;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CustomMessage {
    private HttpStatus httpStatus;
    private String status;
    private String message;
    private Boolean error;
    private List<ValidVouchersWithName> validVouchers;

    // Return general message
    public CustomMessage(String message, Boolean error) {
        this.httpStatus = HttpStatus.OK;
        this.error = error;
        if (error)
            this.status = "Unsuccessful";
        else
            this.status = "Success";
        this.message = message;
    }

    // To return Voucher code & Special Offer name in Arrays
    public CustomMessage(List<ValidVouchersWithName> validVouchers) {
        this.httpStatus = HttpStatus.OK;
        this.error = false;
        this.status = "Success";
        this.validVouchers = validVouchers;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<ValidVouchersWithName> getValidVouchers() {
        return validVouchers;
    }

    public void setValidVouchers(List<ValidVouchersWithName> validVouchers) {
        this.validVouchers = validVouchers;
    }
}
