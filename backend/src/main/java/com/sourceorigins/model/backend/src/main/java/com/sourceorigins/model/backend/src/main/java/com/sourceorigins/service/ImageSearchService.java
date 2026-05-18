package com.sourceorigins.service;

import com.sourceorigins.model.AnalysisResult;
import com.sourceorigins.model.Occurrence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class ImageSearchService {

    @Value("${serpapi.key}")
    private String apiKey;

    public AnalysisResult analyze(String imageUrl) {
        String apiUrl = "https://serpapi.com/search.json"
                + "?engine=google_reverse_image"
                + "&image_url=" + imageUrl
                + "&api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map response = restTemplate.getForObject(apiUrl, Map.class);
            List<Occurrence> occurrences = extractOccurrences(response);
            String firstUrl = occurrences.isEmpty() ? "" : occurrences.get(0).getUrl();
            String firstSource = occurrences.isEmpty() ? "" : occurrences.get(0).getSource();
            return new AnalysisResult(occurrences, 0.0, firstUrl, firstSource, occurrences.size());
        } catch (Exception e) {
            return new AnalysisResult(new ArrayList<>(), 0.0, "", "", 0);
        }
    }

    private List<Occurrence> extractOccurrences(Map response) {
        List<Occurrence> list = new ArrayList<>();
        if (response == null) return list;
        List<Map> results = (List<Map>) response.get("image_results");
        if (results == null) return list;
        for (Map r : results) {
            Occurrence o = new Occurrence();
            o.setUrl(String.valueOf(r.getOrDefault("link", "")));
            o.setTitle(String.valueOf(r.getOrDefault("title", "Sans titre")));
            o.setSource(String.valueOf(r.getOrDefault("source", "Inconnu")));
            o.setDate(String.valueOf(r.getOrDefault("date", "Date inconnue")));
            o.setThumbnailUrl(String.valueOf(r.getOrDefault("thumbnail", "")));
            list.add(o);
        }
        return list;
    }
}
