package com.isep.hpah.model.constructors.character;
import lombok.*;

@Setter @Getter
public abstract class AbstractEnemy extends Character {
    private int dangerLevel;

    public AbstractEnemy(String name, String type, String desc, int maxHealth, int health, double exp, int att, int def, int dex, int dangerLevel) {
        super(name, type, desc, maxHealth, health, exp, att, def, dex);
        this.dangerLevel = dangerLevel;
    }
}
