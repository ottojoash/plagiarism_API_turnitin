package com.example.palagrism.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "turnitin_submissions")
public class TurnitinSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String filePath;
    private Double plagiarismScore;
    private String originalityReportUrl;

    @ElementCollection
    @CollectionTable(name = "matched_sources", joinColumns = @JoinColumn(name = "submission_id"))
    @Column(name = "source_url")
    private List<String> matchedSources;

    @ElementCollection
    @CollectionTable(name = "highlighted_text", joinColumns = @JoinColumn(name = "submission_id"))
    @Column(name = "highlighted_text")
    private List<String> highlightedText;

    public TurnitinSubmission() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Double getPlagiarismScore() {
        return plagiarismScore;
    }

    public void setPlagiarismScore(Double plagiarismScore) {
        this.plagiarismScore = plagiarismScore;
    }

    public String getOriginalityReportUrl() {
        return originalityReportUrl;
    }

    public void setOriginalityReportUrl(String originalityReportUrl) {
        this.originalityReportUrl = originalityReportUrl;
    }

    public List<String> getMatchedSources() {
        return matchedSources;
    }

    public void setMatchedSources(List<String> matchedSources) {
        this.matchedSources = matchedSources;
    }

    public List<String> getHighlightedText() {
        return highlightedText;
    }

    public void setHighlightedText(List<String> highlightedText) {
        this.highlightedText = highlightedText;
    }
}
