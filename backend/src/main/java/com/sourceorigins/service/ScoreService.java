package com.sourceorigins.service;

import com.sourceorigins.model.Occurrence;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    public double calculate(List<Occurrence> occurrences) {
        if (occurrences == null || occurrences.isEmpty()) return 0.0;

        // Critère 1 : nombre de sources (30%)
        double sourceScore = Math.min(occurrences.size() * 5.0, 30.0);

        // Critère 2 : sources identifiées (25%)
        long knownSources = occurrences.stream()
            .filter(o -> o.getSource() != null && !o.getSource().equals("Inconnu"))
            .count();
        double knownScore = Math.min(knownSources * 5.0, 25.0);

        // Critère 3 : présence de dates (25%)
        long withDates = occurrences.stream()
            .filter(o -> o.getDate() != null && !o.getDate().equals("Date inconnue"))
            .count();
        double dateScore = Math.min(withDates * 5.0, 25.0);

        // Critère 4 : diversité des domaines (20%)
        long uniqueDomains = occurrences.stream()
            .map(o -> extractDomain(o.getUrl()))
            .distinct().count();
        double domainScore = Math.min(uniqueDomains * 4.0, 20.0);
        // Critère 5 : bonus sources de presse reconnues (bonus 10%)
        List<String> trustedSources = Arrays.asList(
            "bbc.com", "cnn.com", "reuters.com", 
            "lemonde.fr", "rfi.fr", "apnews.com"
        );
        long trustedCount = occurrences.stream()
            .map(o -> extractDomain(o.getUrl()))
            .filter(trustedSources::contains)
            .count();
        double bonusScore = Math.min(trustedCount * 5.0, 10.0);

        return Math.min(sourceScore + knownScore + dateScore + domainScore, 100.0);
    }

    private String extractDomain(String url) {
        if (url == null || url.isEmpty()) return "";
        try {
            return url.replace("https://", "").replace("http://", "").split("/")[0];
        } catch (Exception e) { return ""; }
    }
}
