package com.isep.hpah;

import com.isep.hpah.views.console.WelcomeOutput;
import com.isep.hpah.views.console.SafeScanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SafeScanner sc = new SafeScanner(System.in);
        WelcomeOutput welcome = new WelcomeOutput();
        ConsoleParser console = new ConsoleParser();
        GUIParser gui = new GUIParser();
        int choice = welcome.choiceGame(sc);
        if (choice == 1){
            console.consoleParser();
        } else if (choice == 2){
            gui.launchInterface();
        }
    }
}
