package com.app.security.utilities;

import java.time.LocalDateTime;

public class CustomApiResponse {

    private String msg;
    private LocalDateTime localDateTime;

    public CustomApiResponse(String msg){
        this.msg = msg;
        localDateTime = LocalDateTime.now();
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "CustomApiResponse{" +
                "msg='" + msg + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
