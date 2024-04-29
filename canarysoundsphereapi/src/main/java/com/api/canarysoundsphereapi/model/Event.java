package com.api.canarysoundsphereapi.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "events")
public class Event {
    private String _id;
    private String logo;
    private String image;
    private String name;
    private String date;
    private String time;
    private int capacity;
    private String description;
    private String direction;
    private String marker;
    private String ticket_store;
}