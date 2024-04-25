package com.stonewu.aifusion.module.ai.enums;

import com.stonewu.aifusion.framework.common.exception.ErrorCode;

/**
 *
 */
public interface ErrorCodeConstants {

    // AI模型
    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(1_030_000_000, "模型不存在");
    ErrorCode MODEL_NO_KEYS = new ErrorCode(1_030_000_001, "模型密钥不存在");
    ErrorCode MODEL_NO_SUCH_TYPE = new ErrorCode(1_030_000_001, "模型类型不存在");

    // AI助手
    ErrorCode ASSISTANT_NOT_EXISTS = new ErrorCode(1_030_001_000, "AI助手不存在");

    // 对话记录
    ErrorCode CHAT_SESSION_NOT_EXISTS = new ErrorCode(1_030_002_000, "对话记录不存在");
    ErrorCode CHAT_RECORD_NOT_EXISTS = new ErrorCode(1_030_002_001, "对话记录不存在");
}
