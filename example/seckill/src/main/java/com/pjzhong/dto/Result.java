package com.pjzhong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    private boolean success;
    private String error;
    private T data;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
