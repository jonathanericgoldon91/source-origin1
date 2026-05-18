package com.sourceorigins.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
