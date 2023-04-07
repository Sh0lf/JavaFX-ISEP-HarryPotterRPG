package com.isep.hpah.views.console;

import com.isep.hpah.model.constructors.character.*;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.spells.AbstractSpell;

public class SpellOutput {
    SimpleOutput out = new SimpleOutput();

    public void slytherinBoost(Wizard player){
        out.print("Since " + player.getName() +" is a Slytherin, you're more efficient with your spells !");
    }

    public void dmgSpell(Wizard player, Character enemy, AbstractSpell spell, int damage, int remainingHealth){
        out.print(player.getName() + " hit " + enemy.getName() + " with " + spell.getName() + " for " + damage + " damage !");
        out.print(enemy.getName() + " has " + remainingHealth + " health points remaining.");
    }

    public void defSpell(Wizard player, int val){
        out.print(player.getName() + " gained " + val + " defense for this turn and have now a total of " + player.getDef() + " defense !");
    }

    public void cooldownSpecial1(){
        out.print("The opponent has restored his stability and has recovered his defense !");
    }

    public void cooldownSpecial2(){
        out.print("The opponent has recovered his wand and can now attack ! Watch out !");
    }

    public void wingardiumSpellCase(Character target){
        out.print("You saw a barrel, you levitated it and you threw it towards the" + target.getName());
        out.print("You dealt 50 damage !");
    }

    public void accioSpellCase(Character target){
        if (target.getName().equals("Basilisk")){
            out.print("You successfully removed one of his fangs ! You just removed 1/3rd of his health !");
        } else {
            out.print("You summoned a rock, destabilizing " + target.getName() + " and showing his weak points !");
            out.print("You reduced his defense by 10 !");
        }
    }

    public void expelliarmusSpellCase(Character target){
        out.print("You successfully threw away " + target.getName() + "'s wand and will miss his next move !");
        out.print("You reduced his dexterity by 20 !");
    }

    public void accioException(){
        out.print("You successfully teleported out to safety !");
    }

    public void exceptionDementor(){
        out.print("You tried to throw something at them, but they are not physical entities ! There's no effect !");
    }
}
