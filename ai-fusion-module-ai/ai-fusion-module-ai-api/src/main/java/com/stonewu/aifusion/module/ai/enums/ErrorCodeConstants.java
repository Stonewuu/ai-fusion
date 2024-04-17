package com.stonewu.aifusion.module.ai.enums;

import com.stonewu.aifusion.framework.common.exception.ErrorCode;

/**
 *
 */
public interface ErrorCodeConstants {

    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(2_001_000_000, "AI模型不存在");
    ErrorCode ASSISTANT_NOT_EXISTS = new ErrorCode(2_001_001_000, "AI助手不存在");

}
