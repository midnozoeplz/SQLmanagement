package com.jianz.SQLmanagement.handler;

//自定义基础接口类
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
