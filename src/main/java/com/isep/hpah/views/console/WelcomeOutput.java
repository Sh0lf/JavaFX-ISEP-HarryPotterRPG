package com.isep.hpah.views.console;

import com.isep.hpah.controller.Setup;
import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Pet;
import com.isep.hpah.model.constructors.Wand;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;

public class WelcomeOutput {

    SimpleOutput out = new SimpleOutput();
    Setup stp = new Setup();

    public String name(SafeScanner sc) {
        boolean verifInput = false;
        out.printHeading("Harry Potter RPG game, made by Yap Vincent");

        String name = null;
        while (!verifInput) {
            try {
                out.print("WELCOME APPRENTICE ! What's your name ?");
                name = sc.getString();
                verifInput = true;
            } catch (Exception e) {
                out.print("Please enter a valid string, not an integer !");
                verifInput = false;
            }
        }
        return name;
    }

    public void sortingHatIntro(String name) {
        out.printHeader("Welcome to Hogwarts, dear " + name + " ! Are you ready for a new adventure ?!");
        out.pressEnterToContinue();

        out.printHeading("Great ! Now let me put the mighty legendary Sorting Hat on your head, and let him do his " +
                "magic and allocate you to a house !");
        out.pressEnterToContinue();
    }

    public int sortingHatQuestion1(SafeScanner sc) {
        int res1 = 0;
        boolean verifInput = false;
        out.printHeading("""
                Sorting Hat : Student, before I rashly allocate you to a house, let me ask you some questions :\s
                You come here with preferences and preconceptions - certain expectations.\s
                [1] I can't wait to start classes.\s
                [2] I can't wait to explore.""");

        while (!verifInput) {
            try {
                int tempRes1 = sc.getInt();
                res1 = tempRes1 * 10;
                verifInput = true;
            } catch (Exception e) {
                out.print("Please select an answer by writing 1 or 2 !");
                verifInput = false;
            }
        }
        return res1;
    }

    public int sortingHatQuestion2(SafeScanner sc) {
        int res2 = 0;
        boolean verifInput = false;
        out.printHeading("""
                Sorting Hat : I see... Hmm, I detect something in you. A certain sens of - hmm - what is it ?
                [1] Daring.\s
                [2] Ambition.""");

        while (!verifInput) {
            try {
                res2 = sc.getInt();
                verifInput = true;
            } catch (Exception e) {
                out.print("Please select an answer by writing 1 or 2 !");
                verifInput = false;
            }
        }
        return res2;
    }

    public void sortingHatResponse(Pet pet, House house) {
        out.printHeading("Sorting Hat : You belong to " + house + " ! \nHere's your pet ! It's a small but " +
                "fierce " + pet + " !\nNow, get me out of him, I despise people of this house !");
        out.pressEnterToContinue();

        out.printHeader("""
                Thank you Sorting Hat ! Don't mind him much, he can be mean but he gets his job well done\s
                and ultimately he does care about the students !\s
                Now let's get your wand !\s
                Of course you need one !""");
        out.pressEnterToContinue();
    }

    public Wand wand(SafeScanner sc) {
        String wandName = "";
        boolean verifInput = false;
        while (!verifInput) {
            try {
                out.print("First of all, how would you call your wand?");
                wandName = sc.getString();
                verifInput = true;
            } catch (Exception e) {
                out.print("Please write valid content");
                verifInput = false;
            }
        }

        int wandSize = 0;
        verifInput = false;
        while (!verifInput) {
            try {
                out.print("And what size in cm would be ideal for you ?");
                wandSize = sc.getInt();
                verifInput = true;
            } catch (Exception e) {
                out.print("Please write valid content");
                verifInput = false;
            }
        }
        return new Wand(wandName, wandSize, stp.generateRandomCore());
    }

    public void showWand(Wand wand){
        out.printHeading("Great ! I found you a wand that looks perfect to you ! It has a " + wand.getCore() +
                " under the name of " + wand.getName() + " and the size is exactly what you wanted : " + wand.getSize());
        out.pressEnterToContinue();
    }

    public void showPlayer(Wizard player){
        out.clearConsole();
        out.printHeading("Now wizard, are you ready ?! You will start this story knowing a few spells:");

        for (AbstractSpell spell : player.getKnownSpells()) {
            // print out the name and description of the spell
            out.print(spell.getName() + " : " + spell.getDesc());
        }

        out.printHeading("Now, watch out during your journey, there's forbidden spells. If you ever use them, " +
                "you would raise your corruption gauge. Do not go beyond 100 !\n\n" +
                "Your stats are:\nHealth: " + player.getHealth() + "\nAtt: " + player.getAtt() +
                "\nDef: " + player.getDef() + "\nDex: " + player.getDex() + "\nMana: " + player.getMana());

        if (player.getHouse().equals(House.GRYFFINDOR)){
            out.print("Since you're in Gryffindor, you have a defense bonus ! You start with 20 Def instead of 10 !");
        } else if (player.getHouse().equals(House.RAVENCLAW)){
            out.print("Since you're in Ravenclaw, you have a dexterity bonus ! You start with 15 Dex instead of 10 !");
        } else if (player.getHouse().equals(House.SLYTHERIN)){
            out.print("Since you're in Slytherin, all your spells are more efficient !");
        } else if (player.getHouse().equals(House.HUFFLEPUFF)){
            out.print("Since you're in Hufflepuff, all your potions are more efficient !");
        }
    }
}