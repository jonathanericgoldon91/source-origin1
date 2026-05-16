package com.sourceorigins.controller;

import com.sourceorigins.model.AnalysisResult;
import com.sourceorigins.service.ImageSearchService;
import com.sourceorigins.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    @Autowired
    private ImageSearchService imageSearchService;

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResult> analyzeImage(
            @RequestParam("url") String imageUrl) {
        AnalysisResult result = imageSearchService.analyze(imageUrl);
        double score = scoreService.calculate(result.getOccurrences());
        result.setConfidenceScore(score);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Source Origin API is running!");
    }
}
