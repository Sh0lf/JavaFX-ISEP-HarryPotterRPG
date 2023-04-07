package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.*;
import com.isep.hpah.model.constructors.character.Boss;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Enemy;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.model.constructors.spells.ForbiddenSpell;
import com.isep.hpah.model.constructors.spells.Spell;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@Setter @Getter
public class Setup {
    //Generate random core from enum
    public Core generateRandomCore() {
        Core[] values = Core.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    //Generate random cpet from enum
    public Pet generateRandomPet() {
        Pet[] values = Pet.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    //Spells creation
    private Spell fireball = Spell.builder()
            .name("Fireball")
            .num(40)
            .desc("A powerful ball of pure fire and heat")
            .level(0)
            .cooldown(2)
            .mana(20)
            .type("DMG")
            .build();

    private Spell lightningBolt = Spell.builder()
            .name("Lightning Bolt")
            .num(50)
            .desc("Strike your enemies with lightning")
            .level(0)
            .cooldown(3)
            .mana(30)
            .type("DMG")
            .build();

    private Spell wingardiumLeviosa = Spell.builder()
            .name("Wingardium Leviosa")
            .num(50)
            .desc("Levitate objects with this spell")
            .level(0)
            .cooldown(2)
            .mana(20)
            .type("UTL")
            .build();

    private Spell accio = Spell.builder()
            .name("Accio")
            .num(10)
            .desc("Bring an object towards you")
            .level(2)
            .cooldown(3)
            .mana(30)
            .type("UTL")
            .build();
    private Spell expectoPatronum = Spell.builder()
            .name("Expecto Patronum")
            .num(1000)
            .desc("Protect yourself with the sacred Guardian of the forest")
            .level(3)
            .cooldown(3)
            .mana(40)
            .type("DEF")
            .build();

    private Spell sectumsempra = Spell.builder()
            .name("Sectumsempra")
            .num(100)
            .desc("Deal insane pain to someone")
            .level(8)
            .cooldown(5)
            .mana(80)
            .type("ATT")
            .build();

    private Spell expelliarmus = Spell.builder()
            .name("Expelliarmus")
            .num(20)
            .desc("Protect yourself to any attacks without any problem by literally throwing away your opponent's wand")
            .level(8)
            .cooldown(5)
            .mana(80)
            .type("UTL")
            .build();

    private ForbiddenSpell avadaKedavra = ForbiddenSpell.builder()
            .name("Avada Kedavra")
            .num(10000)
            .desc("You are guaranteed to die, unless you protect yourself with something")
            .level(10)
            .corruption(99)
            .cooldown(6)
            .mana(200)
            .type("DMG")
            .build();

    public ForbiddenSpell deathEaterGroup = ForbiddenSpell.builder()
            .name("Group Attack: Death Eaters")
            .num(250)
            .desc("Group up with the death eaters to do a joint attack on the enemy !")
            .level(0)
            .corruption(50)
            .cooldown(5)
            .mana(100)
            .type("DMG")
            .build();

    //TODO: Could do more spells for more diversity and maybe even more transition dungeons (fillerDungeons)

    //list of spells known when starting game
    protected List<AbstractSpell> startingSpellList(){
        //creating arrayList for new wizard
        List<AbstractSpell> knownSpells = new ArrayList<>();
        //Known Spells that you start with
        knownSpells.add(fireball);
        knownSpells.add(lightningBolt);
        knownSpells.add(wingardiumLeviosa);

        return knownSpells;
    }

    //List of all obtainable spells
    public List<AbstractSpell> allObtainableSpells(){
        List<AbstractSpell> obtainableSpells = new ArrayList<>();

        obtainableSpells.add(accio);
        obtainableSpells.add(expectoPatronum);
        obtainableSpells.add(sectumsempra);
        obtainableSpells.add(expelliarmus);
        obtainableSpells.add(avadaKedavra);

        return obtainableSpells;
    }

    protected List<AbstractSpell> voldemortSpells(){
        List<AbstractSpell> voldemortSpells = new ArrayList<>();
        //list of spells that Voldemort knows
        voldemortSpells.add(avadaKedavra);
        voldemortSpells.add(fireball);
        voldemortSpells.add(lightningBolt);
        return voldemortSpells;
    }

    protected List<AbstractSpell> pettigrowSpells(){
        List<AbstractSpell> pettigrowSpells = new ArrayList<>();
        //list of spells that Pettigrow knows
        pettigrowSpells.add(fireball);
        pettigrowSpells.add(lightningBolt);
        return pettigrowSpells;
    }

    protected List<AbstractSpell> ombrageSpells(){
        List<AbstractSpell> ombrageSpells = new ArrayList<>();
        //list of spells that Ombrage knows
        ombrageSpells.add(fireball);
        ombrageSpells.add(lightningBolt);

        return ombrageSpells;
    }

    private Enemy dementor = Enemy.builder()
            .name("Dementor")
            .desc("One of the foulest Dark creatures")
            .health(100)
            .exp(25)
            .att(25)
            .def(5)
            .dex(5)
            .dangerLevel(3)
            .build();
    private Enemy deatheater = Enemy.builder()
            .name("Death Eater")
            .desc("The most ardent followers of Voldemort")
            .health(150)
            .exp(25)
            .att(25)
            .def(5)
            .dex(5)
            .dangerLevel(3)
            .build();

    private Boss troll = Boss.builder()
            .name("Troll")
            .desc("Huge brutal but stupid creature")
            .health(100)
            .exp(50)
            .att(20)
            .def(5)
            .dex(2)
            .build();
    private Boss basilisk = Boss.builder()
            .name("Basilisk")
            .desc("Huge poisoned snake, that can kill you instantly !")
            .health(200)
            .exp(50)
            .att(40)
            .def(10)
            .dex(5)
            .build();

    private Wizard voldemort = Wizard.builder()
            .name("Lord Voldemort")
            .desc("\"You - Know - Who\", \"He Who Must " +
                    "Not Be Named\", or \"the Dark Lord\", one of the brightest Hogwarts have ever seen, but he's obsessed with " +
                    "blood purity and he won't stop at anything to achieve it !")
            .health(1000)
            .exp(50)
            .att(50)
            .def(10)
            .dex(-50)
            .level(100)
            .wand(new Wand("Tom Jedusor's wand", 33.75, Core.PHOENIX_FEATHER))
            .pet(generateRandomPet())
            .house(House.SLYTHERIN)
            .knownSpells(voldemortSpells())
            .potionsOwned(enemyPots())
            .corruptionGauge(0)
            .maxMana(300)
            .mana(300)
            .build();

    private Wizard pettigrow = Wizard.builder()
            .name("Peter Pettigrew")
            .desc("Voldemort's best spy. An extremely smart individual, so beware !")
            .health(1000)
            .exp(50)
            .att(50)
            .def(10)
            .dex(-50)
            .level(50)
            .wand(new Wand("Chestnut", 23, generateRandomCore()))
            .pet(generateRandomPet())
            .house(House.GRYFFINDOR)
            .knownSpells(pettigrowSpells())
            .potionsOwned(enemyPots())
            .corruptionGauge(0)
            .maxMana(200)
            .mana(200)
            .build();

    private Wizard umbridge = Wizard.builder()
            .name("Dolores Umbridge")
            .desc("An extremely cruel professor that's even capable of punishing violently and physically students !")
            .health(500)
            .exp(50)
            .att(25)
            .def(20)
            .dex(0)
            .level(50)
            .wand(new Wand("Umber", 34, generateRandomCore()))
            .pet(generateRandomPet())
            .house(House.SLYTHERIN)
            .knownSpells(ombrageSpells())
            .potionsOwned(enemyPots())
            .corruptionGauge(0)
            .maxMana(200)
            .mana(200)
            .build();

    private Wizard bellatrix = Wizard.builder()
            .name("Bellatrix Lestrange")
            .desc("Voldemort's right hand servant, stronger than you think")
            .health(1000)
            .exp(50)
            .att(50)
            .def(10)
            .dex(-50)
            .level(50)
            .wand(new Wand("Walnut", 32, generateRandomCore()))
            .pet(generateRandomPet())
            .house(House.SLYTHERIN)
            .knownSpells(voldemortSpells())
            .potionsOwned(enemyPots())
            .corruptionGauge(0)
            .maxMana(200)
            .mana(200)
            .build();

    public Wizard playerCreation (String name, Wand wand, Pet pet, House house) {
        int defaultDef = 10;
        int defaultDex = 10;

        if (house == House.GRYFFINDOR){
            defaultDef = 20;
        }

        if (house == House.RAVENCLAW){
            defaultDex = 15;
        }

        return Wizard.builder()
            .name(name)
            .desc("The player")
            .health(200)
            .exp(0)
            .att(20)
            .def(defaultDef)
            .dex(defaultDex)
            .level(1)
            .wand(wand)
            .pet(pet)
            .house(house)
            .knownSpells(startingSpellList())
            .potionsOwned(empty())
            .corruptionGauge(0)
            .maxMana(100)
            .mana(100)
            .build();
    }

    protected List<Character> dungeon1(){
        //creating arrayList
        List<Character> dungeon1 = new ArrayList<>();
        //mob creation per dungeon
        dungeon1.add(troll);
        return dungeon1;
    }

    protected List<Character> dungeon2(){
        //creating arrayList
        List<Character> dungeon2 = new ArrayList<>();
        //mob creation per dungeon
        dungeon2.add(basilisk);
        return dungeon2;
    }

    protected List<Character> dungeon3(){
        //creating arrayList
        List<Character> dungeon3 = new ArrayList<>();
        //mob creation per dungeon
        dungeon3.add(dementor);
        dungeon3.add(dementor);
        dungeon3.add(dementor);
        dungeon3.add(dementor);

        return dungeon3;
    }

    protected List<Character> dungeon4(){
        //creating arrayList
        List<Character> dungeon4 = new ArrayList<>();
        //mob creation per dungeon
        dungeon4.add(voldemort);
        dungeon4.add(pettigrow);
        return dungeon4;
    }

    protected List<Character> dungeon5(){
        //creating arrayList
        List<Character> dungeon5 = new ArrayList<>();
        //mob creation per dungeon
        dungeon5.add(umbridge);
        return dungeon5;
    }

    protected List<Character> dungeon6(){
        //creating arrayList
        List<Character> dungeon6 = new ArrayList<>();
        //mob creation per dungeon
        dungeon6.add(deatheater);
        dungeon6.add(deatheater);
        dungeon6.add(deatheater);
        dungeon6.add(deatheater);

        return dungeon6;
    }

    protected List<Character> dungeon7(){
        //creating arrayList
        List<Character> dungeon7 = new ArrayList<>();
        //mob creation per dungeon
        dungeon7.add(voldemort);
        dungeon7.add(bellatrix);
        return dungeon7;
    }

    private Dungeon philosopherStone = Dungeon.builder()
            .name("The Philosopher's Stone")
            .desc("There's a troll right next to the toilets of the dungeon ! Beast him in a way or another !")
            .enemies(dungeon1())
            .build();
    private Dungeon chamberofSecrets = Dungeon.builder()
            .name("The Chamber of Secrets")
            .desc("You find yourself right in front of the mighty terrifying Basilisk ! Pull out one of these fangs to destroy Tom Riddle's journal ! Or maybe... there's another way...")
            .enemies(dungeon2())
            .build();
    private Dungeon prisonnerofAzkaban = Dungeon.builder()
            .name("The Prisonner of Azkaban")
            .desc("The dementors are on the loose! To defeat them, learn a spell by leveling up and use it against the dementors!")
            .enemies(dungeon3())
            .build();
    private Dungeon gobletofFire = Dungeon.builder()
            .name("The Goblet of Fire")
            .desc("You have won the Triwizard Tournament... and the right to die. You find Voldemort and Peter Pettigrew! Run away anyway!")
            .enemies(dungeon4())
            .build();
    private Dungeon orderofPhoenix = Dungeon.builder()
            .name("The Order of Phoenix")
            .desc("It's time for the OWL (Universal Certificate of Elementary Witchcraft)! Dolores Umbridge is watching over you. Your goal is to distract her until the fireworks are ready to go.")
            .enemies(dungeon5())
            .build();
    private Dungeon halfBloodedPrince = Dungeon.builder()
            .name("The Half-Blood Prince")
            .desc("The Death Eaters attack Hogwarts. Do you want to attack them from the front or do you plan to make another decision?")
            .enemies(dungeon6())
            .build();
    private Dungeon deathlyHallows = Dungeon.builder()
            .name("The Deathly Hallows")
            .desc("You have to start attacking the problem at the source. You are facing Voldemort and Bellatrix Lestrange! Pay attention to Avada Kedavra!")
            .enemies(dungeon7())
            .build();

    public List<Dungeon> allDungeon(){
        //ArrayList creation for better organisation
        List<Dungeon> allDungeon = new ArrayList<>();
        //Dungeons adding to list
        allDungeon.add(philosopherStone);
        allDungeon.add(chamberofSecrets);
        allDungeon.add(prisonnerofAzkaban);
        allDungeon.add(gobletofFire);
        allDungeon.add(orderofPhoenix);
        allDungeon.add(halfBloodedPrince);
        allDungeon.add(deathlyHallows);

        return allDungeon;
    }

    private Potion smallHealthPotion = Potion.builder()
            .name("Small Health Potion")
            .desc("A small health potion, used to heal up scratches and bad wounds")
            .boost(50)
            .type("HP")
            .build();

    private Potion mediumHealthPotion = Potion.builder()
            .name("Medium Health Potion")
            .desc("A medium health potion, big enough to fix broken bones and huge wounds")
            .boost(100)
            .type("HP")
            .build();

    private Potion largeHealthPotion = Potion.builder()
            .name("Large Health Potion")
            .desc("A large health potion, gives enough health to resuscitate someone ! (Even though you cannot)")
            .boost(200)
            .type("HP")
            .build();

    private Potion smallDefPotion = Potion.builder()
            .name("Small Defense Potion")
            .desc("A small defense potion, gives enough defense to not feel anything from small mobs")
            .boost(20)
            .type("DEF")
            .build();

    private Potion mediumDefPotion = Potion.builder()
            .name("Medium Defense Potion")
            .desc("A medium defense potion, big enough to tank through attacks from bosses")
            .boost(40)
            .type("DEF")
            .build();

    private Potion largeDefPotion = Potion.builder()
            .name("Large Defense Potion")
            .desc("A large defense potion, gives enough defense to tank through the most powerful spells")
            .boost(80)
            .type("DEF")
            .build();
    private Potion smallDexPotion = Potion.builder()
            .name("Small Dexterity Potion")
            .desc("A small dexterity potion, enough to make sure to hit someone (With a fairly big uncertainty)")
            .boost(5)
            .type("DEX")
            .build();

    private Potion mediumDexPotion = Potion.builder()
            .name("Medium Dexterity Potion")
            .desc("A medium dexterity potion, big enough to be sure to hit someone (small uncertainty still)")
            .boost(10)
            .type("DEX")
            .build();

    private Potion largeDexPotion = Potion.builder()
            .name("Large Dexterity Potion")
            .desc("A large dexterity potion, gives enough to be sure to hit someone (for real) !")
            .boost(20)
            .type("DEX")
            .build();

    //create list of potions that is empty in case
    protected List<Potion> empty(){
        return new ArrayList<>();
    }

    protected List<Potion> enemyPots(){
        List<Potion> enemypots = new ArrayList<>();

        enemypots.add(mediumHealthPotion);
        enemypots.add(mediumDefPotion);
        enemypots.add(mediumDexPotion);

        return enemypots;
    }

    public List<Potion> allPotions(){
       List<Potion> allPotions = new ArrayList<>();
       allPotions.add(smallHealthPotion);
       allPotions.add(mediumHealthPotion);
       allPotions.add(largeHealthPotion);
       allPotions.add(smallDefPotion);
       allPotions.add(mediumDefPotion);
       allPotions.add(largeDefPotion);
       allPotions.add(smallDexPotion);
       allPotions.add(mediumDexPotion);
       allPotions.add(largeDexPotion);

       return allPotions;
    }

    // List of choices possible during combat, used for ease afterwards
    public List<String> poss() {
        List<String> choice = new ArrayList<>();
        choice.add("Attack");
        choice.add("Defend");
        choice.add("Use spells");
        choice.add("Use potions");

        return choice;
    }
}
