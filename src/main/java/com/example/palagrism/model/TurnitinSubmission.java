package com.example.palagrism.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turnitin_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnitinSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String filePath; // Store the path where the file is saved
    private String turnitinResponse;
    private Double plagiarismScore;

    @Column(columnDefinition = "TEXT")
    private String originalityReportUrl;
}
