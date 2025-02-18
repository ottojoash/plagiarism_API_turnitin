package com.example.palagrism.controller;


import com.example.palagrism.model.TurnitinSubmission;
import com.example.palagrism.service.TurnitinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/turnitin")
public class TurnitinController {

    private final TurnitinService turnitinService;

    public TurnitinController(TurnitinService turnitinService) {
        this.turnitinService = turnitinService;
    }

    @PostMapping("/upload")
    public ResponseEntity<TurnitinSubmission> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            TurnitinSubmission submission = turnitinService.uploadFileToTurnitin(file);
            return ResponseEntity.ok(submission);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/submission/{id}")
    public ResponseEntity<TurnitinSubmission> getSubmission(@PathVariable Long id) {
        Optional<TurnitinSubmission> submission = turnitinService.getSubmission(id);
        return submission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
