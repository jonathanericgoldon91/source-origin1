package com.sourceorigins.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResult {
    private List<Occurrence> occurrences;
    private double confidenceScore;
    private String firstOccurrenceUrl;
    private String firstOccurrenceSource;
    private int totalFound;
}
