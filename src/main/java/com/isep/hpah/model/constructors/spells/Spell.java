package com.isep.hpah.model.constructors.spells;

import lombok.Builder;

public class Spell extends AbstractSpell{
    @Builder
    public Spell(String name, int num, String desc, int level, int cooldown, int mana, String type) {
        super(name, num, desc, level, 0, cooldown, 0, mana, type);
    }
}
