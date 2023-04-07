package com.isep.hpah.model.constructors.character;

import com.isep.hpah.views.console.SimpleOutput;
import lombok.*;


public abstract class Character {
    @Setter @Getter
    private String name;
    @Setter @Getter
    private String type;
    @Setter @Getter
    private String desc;
    @Setter @Getter
    private int maxHealth;
    @Setter @Getter
    private int health;
    @Setter @Getter
    private double exp;
    @Setter @Getter
    private int att;
    @Setter
    private int def;
    @Setter @Getter
    private int dex;
    @Setter @Getter
    private boolean isDefending = false;

    public Character(String name, String type, String desc, int maxHealth, int health, double exp, int att, int def, int dex) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.maxHealth = maxHealth;
        this.health = health;
        this.exp = exp;
        this.att = att;
        this.def = def;
        this.dex = dex;
    }

    public int getDef() {
        if (isDefending) {
            return def * 2;
        } else {
            return def;
        }
    }

    //Only useful method in a constructor. need to keep it
    public void normalAttack(Character enemy) {
        SimpleOutput out = new SimpleOutput();
        // implementation of normalAttack on enemy
        double hitChance = 0.7 + this.dex;
        double rand = Math.random();

        if (rand > hitChance) {
            out.print(this.name + " missed his attack !");
            return;
        }

        double damage = this.att - (enemy.getDef());
        if (damage <= 0) {
            out.print(this.name + " did no damage to " + enemy.getName() + "!");
            return;
        }

        // Reduce enemy's health by damage
        int remainingHealth = enemy.getHealth() - (int) damage;
        enemy.setHealth(remainingHealth);

        // Print out attack details
        out.print(this.name + " hit " + enemy.getName() + " for " + (int) damage + " damage !");
        out.print(enemy.getName() + " has " + remainingHealth + " health left.");
    }

    /* Not used for the moment but can be interesting to look at
    public void defendedAttack(Character enemy){
        // implementation of defended attack (means the one receiving the attack has defended)
        // Calculate chance of attacker hitting based on their dexterity
        double hitChance = 0.7 + this.dex;
        double rand = Math.random();
        if (rand > hitChance) {
            out.print(enemy.getName() + " dodged the attack");
            return;
        }

        // Calculate damage based on attacker's attack and defender's defense
        double damage = enemy.getAtt() - this.def;
        if (damage <= 0) {
            out.print(this.name + " blocked the attack without a single problem !");
            return;
        }

        // Reduce defender's health by damage
        int remainingHealth = this.health - (int) damage;
        this.health = remainingHealth;

        // Print out defense details
        out.print(this.name + " has defended, but still took " + (int) damage + " damage !");
        out.print(this.name + " has " + remainingHealth + " health remaining.");
    } */
}
