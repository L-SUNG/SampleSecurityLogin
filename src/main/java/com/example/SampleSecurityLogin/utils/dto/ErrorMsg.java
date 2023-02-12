package com.example.SampleSecurityLogin.utils.dto;

import lombok.Setter;

@Setter
public class ErrorMsg {

    // 고정키
    private final String key = "errorMsg";
    // 에러 메세지
    private final String errorMsg;

    public ErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}