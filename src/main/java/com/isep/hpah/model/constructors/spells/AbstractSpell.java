package com.isep.hpah.model.constructors.spells;

import lombok.*;

@Setter @Getter
public abstract class AbstractSpell {
    //defining what a spell have:
    private String name;
    private int num;
    private String desc;
    private int level;
    private int corruption;
    private int cooldown;
    private int cooldownRem;
    private int mana;
    private String type;

    public AbstractSpell(String name, int num, String desc, int level, int corruption, int cooldown, int cooldownRem, int mana, String type) {
        this.name = name;
        this.num = num;
        this.desc = desc;
        this.level = level;
        this.corruption = corruption;
        this.cooldown = cooldown;
        this.cooldownRem = cooldownRem;
        this.mana = mana;
        this.type = type;
    }
}
