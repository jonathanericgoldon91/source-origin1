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
    private String url;
    private String title;
    private String source;
    private String date;
    private String thumbnailUrl;
}
