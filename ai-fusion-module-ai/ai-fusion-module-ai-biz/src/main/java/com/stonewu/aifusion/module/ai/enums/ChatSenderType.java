package com.stonewu.aifusion.module.ai.enums;

public enum ChatSenderType {

    USER(1, "用户"),
    MODEL(1, "模型");

    private final Integer code;
    private final String sender;

    public Integer getCode() {
        return code;
    }

    public String getSender() {
        return sender;
    }

    ChatSenderType(Integer code, String sender) {
        this.code = code;
        this.sender = sender;
    }
}
