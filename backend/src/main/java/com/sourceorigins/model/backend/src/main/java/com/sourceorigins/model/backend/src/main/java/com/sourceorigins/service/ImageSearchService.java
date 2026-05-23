package com.sourceorigins.service;

import com.sourceorigins.model.AnalysisResult;
import com.sourceorigins.model.Occurrence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * Service principal de recherche d'image.
 * Appelle l'API SerpAPI pour retrouver l'origine d'une image sur le web.
 */
@Service
public class ImageSearchService {

    /** Clé d'accès à l'API SerpAPI */
    @Value("${serpapi.key}")
    private String apiKey;

    /** Nombre maximum de résultats à retourner */
    private static final int MAX_RESULTS = 10;

    /**
     * Analyse une image à partir de son URL.
     * @param imageUrl l'URL de l'image à analyser
     * @return un AnalysisResult contenant toutes les occurrences trouvées
     */
    public AnalysisResult analyze(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new AnalysisResult(new ArrayList<>(), 0.0, "", "", 0);
        }
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

    /**
     * Extrait les occurrences depuis la réponse brute de l'API.
     * Limite les résultats à MAX_RESULTS occurrences.
     * @param response la réponse JSON de SerpAPI
     * @return la liste des occurrences trouvées
     */
    private List<Occurrence> extractOccurrences(Map response) {
        List<Occurrence> list = new ArrayList<>();
        if (response == null) return list;
        List<Map> results = (List<Map>) response.get("image_results");
        if (results == null) return list;
        for (Map r : results) {
            if (list.size() >= MAX_RESULTS) break;
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
