package com.isep.hpah.model.constructors.character;

import lombok.Builder;

public class Enemy extends AbstractEnemy {
    @Builder
    Enemy(String name, String desc, int health, double exp, int att, int def, int dex, int dangerLevel) {
        super(name, "Enemy: Mob", desc, health, health, exp, att, def, dex, dangerLevel);
    }
}
