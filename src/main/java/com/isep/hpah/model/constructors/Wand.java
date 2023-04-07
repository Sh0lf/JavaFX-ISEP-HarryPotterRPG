package com.isep.hpah.model.constructors;

import lombok.*;

@Getter @Setter
public class Wand {
    private String name;
    private Core core;
    private double size;

    public Wand(String name, double size, Core core) {
        this.name = name;
        this.size = size;
        this.core = core;
    }
}
