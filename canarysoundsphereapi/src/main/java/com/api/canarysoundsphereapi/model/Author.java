package com.api.canarysoundsphereapi.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "authors")
public class Author {
    private String _id;
    private String name;
    private String image;
    private int foundation_year;
    private String music_type;
    private String description;
    private String music_list;
}