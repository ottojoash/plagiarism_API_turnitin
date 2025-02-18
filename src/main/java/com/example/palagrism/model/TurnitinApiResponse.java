package com.example.palagrism.model;

import java.util.List;

public class TurnitinApiResponse {
    private double similarityScore;
    private List<String> matchedSources;
    private String originalityReportUrl;
    private List<String> highlightedText;

    public double getSimilarityScore() {
        return similarityScore;
    }

    public List<String> getMatchedSources() {
        return matchedSources;
    }

    public String getOriginalityReportUrl() {
        return originalityReportUrl;
    }

    public List<String> getHighlightedText() {
        return highlightedText;
    }
}
