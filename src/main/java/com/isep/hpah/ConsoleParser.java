package com.isep.hpah;

import com.isep.hpah.model.constructors.*;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.controller.*;
import com.isep.hpah.views.console.SafeScanner;
import com.isep.hpah.views.console.SimpleOutput;
import com.isep.hpah.views.console.WelcomeOutput;

import java.util.List;

public class ConsoleParser {
    public void consoleParser() {
        GameEngine game = new GameEngine();
        Setup stp = new Setup();
        SafeScanner sc = new SafeScanner(System.in);
        SortingHat sortHat = new SortingHat();
        SimpleOutput out = new SimpleOutput();
        WelcomeOutput welcome = new WelcomeOutput();
        House house;
        Pet pet;
        Wand wand;
        int res1;
        int res2;
        int finalRes;

        String name = welcome.name(sc);

        welcome.sortingHatIntro(name);

        res1 = welcome.sortingHatQuestion1(sc);

        res2 = welcome.sortingHatQuestion2(sc);

        finalRes = res1 + res2;

        house = sortHat.getResHouse(finalRes);

        pet = stp.generateRandomPet();

        welcome.sortingHatResponse(pet, house);

        wand = welcome.wand(sc);

        welcome.showWand(wand);

        Wizard player = stp.playerCreation(name, wand, pet, house);

        welcome.showPlayer(player);

        out.pressEnterToContinue();

        List<Dungeon> dungeons = stp.allDungeon();

        int i;
        for (i = 0; i<=dungeons.size(); i++){
            out.printHeader(dungeons.get(i).getName());
            out.pressEnterToContinue();

            out.printHeading(dungeons.get(i).getDesc());
            out.pressEnterToContinue();

            game.dungeonCombat(dungeons.get(i).getEnemies(), player);
            out.pressEnterToContinue();
        }
    }
}

