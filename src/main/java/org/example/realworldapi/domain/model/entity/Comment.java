package org.example.realworldapi.domain.model.entity;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

    private String id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String body;
    private User author;
}
