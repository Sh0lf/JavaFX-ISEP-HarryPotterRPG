package com.isep.hpah.model.constructors.character;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Pet;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.Wand;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import lombok.*;
import java.util.List;

@Setter @Getter
public class Wizard extends Character {
    private int level;
    private Pet pet;
    private Wand wand;
    private House house;
    private List<AbstractSpell> knownSpells;
    private List<Potion> potionsOwned;
    private int corruptionGauge;
    private int maxMana;
    private int mana;
    private int defSpell;
    private int potionDefBoost;
    private int potionDexBoost;

    @Builder
    public Wizard(String name, String desc, int health, int exp, int att, int def, int dex, int level, Wand wand, Pet pet,
                  House house, List<AbstractSpell> knownSpells, List<Potion> potionsOwned, int corruptionGauge,
                  int maxMana, int mana) {
        super(name, "Wizard", desc, health, health, exp, att, def, dex);
        this.level = level;
        this.wand = wand;
        this.pet = pet;
        this.house = house;
        this.knownSpells = knownSpells;
        this.potionsOwned = potionsOwned;
        this.corruptionGauge = corruptionGauge;
        this.maxMana = maxMana;
        this.mana = mana;
        this.defSpell = 0;
        this.potionDefBoost = 0;
        this.potionDexBoost = 0;
    }
}
