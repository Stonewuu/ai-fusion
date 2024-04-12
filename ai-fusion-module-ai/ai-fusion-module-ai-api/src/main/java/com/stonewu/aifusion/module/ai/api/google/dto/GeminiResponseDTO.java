package com.stonewu.aifusion.module.ai.api.google.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponseDTO {

    private List<Candidate> candidates;

}
