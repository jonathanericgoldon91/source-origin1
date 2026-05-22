package com.sourceorigins.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une occurrence (apparition) d'une image sur le web.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Occurrence {

    /** URL de la page où l'image a été trouvée */
    private String url;

    /** Titre de la page web */
    private String title;

    /** Nom du site source (ex: Wikipedia, Facebook...) */
    private String source;

    /** Date de publication de l'image */
    private String date;

    /** URL de la miniature de l'image */
    private String thumbnailUrl;
}
