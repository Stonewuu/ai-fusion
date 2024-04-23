package com.stonewu.aifusion.module.ai.enums;

import com.stonewu.aifusion.framework.common.exception.ErrorCode;

/**
 *
 */
public interface ErrorCodeConstants {

    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(1_030_000_000, "模型不存在");
    ErrorCode MODEL_NO_KEYS = new ErrorCode(1_030_000_001, "模型密钥不存在");
    ErrorCode MODEL_NO_SUCH_TYPE = new ErrorCode(1_030_000_001, "模型类型不存在");
    ErrorCode ASSISTANT_NOT_EXISTS = new ErrorCode(1_030_001_000, "AI助手不存在");

}
