package com.sourceorigins.controller;

import com.sourceorigins.model.AnalysisResult;
import com.sourceorigins.service.ImageSearchService;
import com.sourceorigins.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    @Autowired
    private ImageSearchService imageSearchService;

    @Autowired
    private ScoreService scoreService;

    // Endpoint upload image directe
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResult> analyzeImage(
            @RequestParam("image") MultipartFile imageFile) {

        AnalysisResult result = imageSearchService.analyzeFile(imageFile);
        double score = scoreService.calculate(result.getOccurrences());
        result.setConfidenceScore(score);
        return ResponseEntity.ok(result);
    }

    // Endpoint vérification serveur
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Source Origin API is running!");
    }
}
