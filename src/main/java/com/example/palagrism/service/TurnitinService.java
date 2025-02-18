package com.example.palagrism.service;

import com.example.palagrism.model.TurnitinApiResponse;
import com.example.palagrism.model.TurnitinSubmission;
import com.example.palagrism.repository.TurnitinSubmissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TurnitinService {

    @Value("${turnitin.api.url}")
    private String turnitinApiUrl;

    @Value("${turnitin.api.key}")
    private String turnitinApiKey;

    private final TurnitinSubmissionRepository submissionRepository;
    private final WebClient webClient;

    public TurnitinService(TurnitinSubmissionRepository submissionRepository, WebClient.Builder webClientBuilder) {
        this.submissionRepository = submissionRepository;
        this.webClient = webClientBuilder.baseUrl(turnitinApiUrl).build();
    }

    public TurnitinSubmission uploadFileToTurnitin(MultipartFile file) throws IOException {
        // Step 1: Save File Locally
        File savedFile = saveFileLocally(file);

        // Step 2: Send file to Turnitin
        TurnitinApiResponse apiResponse = sendFileToTurnitin(savedFile);

        // Step 3: Save submission in MySQL
        TurnitinSubmission submission = new TurnitinSubmission();
        submission.setFileName(file.getOriginalFilename());
        submission.setFileType(file.getContentType());
        submission.setFilePath(savedFile.getAbsolutePath());
        submission.setPlagiarismScore(apiResponse.getSimilarityScore());
        submission.setMatchedSources(apiResponse.getMatchedSources());
        submission.setHighlightedText(apiResponse.getHighlightedText());
        submission.setOriginalityReportUrl(apiResponse.getOriginalityReportUrl());

        return submissionRepository.save(submission);
    }

    private File saveFileLocally(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        File savedFile = new File(uploadDir + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(savedFile)) {
            fos.write(file.getBytes());
        }
        return savedFile;
    }

    private TurnitinApiResponse sendFileToTurnitin(File file) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/upload").queryParam("api_key", turnitinApiKey).build())
                .bodyValue(new FileSystemResource(file))
                .retrieve()
                .bodyToMono(TurnitinApiResponse.class)
                .block(); // Blocking call (simpler, but can be made async)
    }

    public Optional<TurnitinSubmission> getSubmission(Long id) {
        return submissionRepository.findById(id);
    }
}
