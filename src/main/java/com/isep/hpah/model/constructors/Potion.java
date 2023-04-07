package com.isep.hpah.model.constructors;

import lombok.*;

@Getter @Setter
public class Potion {
    private String name;
    private String desc;
    private int boost;
    private String type;

    @Builder
    public Potion(String name, String desc, int boost, String type) {
        this.name = name;
        this.desc = desc;
        this.boost = boost;
        this.type = type;
    }
}
