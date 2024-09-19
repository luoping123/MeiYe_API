package com.sct.meiye.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author sct
 * @version 1.0
 * @createTime 2022/04/19 20:41
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private Integer status;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Integer status) {
        this.status = status;
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
