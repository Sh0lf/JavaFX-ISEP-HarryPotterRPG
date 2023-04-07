package com.isep.hpah.model.constructors.spells;

import lombok.*;

public class ForbiddenSpell extends AbstractSpell{
    @Builder
    public ForbiddenSpell(String name, int num, String desc, int level, int corruption, int cooldown, int mana, String type) {
        super(name, num, desc, level, corruption, cooldown, 0, mana, type);
    }
}
