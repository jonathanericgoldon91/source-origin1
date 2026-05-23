package com.sourceorigins.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Représente le résultat complet d'une analyse d'image.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResult {

    /** Liste de toutes les occurrences trouvées sur le web */
    private List<Occurrence> occurrences;

    /** Score de confiance entre 0 et 100 */
    private double confidenceScore;

    /** URL de la première occurrence trouvée */
    private String firstOccurrenceUrl;

    /** Source de la première occurrence trouvée */
    private String firstOccurrenceSource;

    /** Nombre total d'occurrences trouvées */
    private int totalFound;

    /**
     * Retourne la meilleure occurrence (la première de la liste)
     */
    public Occurrence getBestOccurrence() {
        if (occurrences == null || occurrences.isEmpty()) {
            return null;
        }
        return occurrences.get(0);
    }

    /**
     * Vérifie si l'analyse a trouvé des résultats
     */
    public boolean hasResults() {
        return totalFound > 0;
    }
}
