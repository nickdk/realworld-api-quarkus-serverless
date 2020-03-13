package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tag {

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
