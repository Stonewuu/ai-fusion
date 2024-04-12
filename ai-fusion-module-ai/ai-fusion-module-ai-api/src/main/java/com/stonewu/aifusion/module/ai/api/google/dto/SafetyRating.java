package com.stonewu.aifusion.module.ai.api.google.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafetyRating {

    private String category;

    private String probability;

}
