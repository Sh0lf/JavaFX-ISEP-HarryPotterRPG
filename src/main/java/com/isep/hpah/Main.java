package com.isep.hpah;

import com.isep.hpah.views.console.WelcomeOutput;
import com.isep.hpah.views.console.SafeScanner;

public class Main {
    public static void main(String[] args) {
        SafeScanner sc = new SafeScanner(System.in);
        WelcomeOutput welcome = new WelcomeOutput();
        ConsoleMain console = new ConsoleMain();
        GUImain gui = new GUImain();
        int choice = welcome.choiceGame(sc);

        if (choice == 1){
            console.consoleMain();
        } else if (choice == 2){
            gui.guiMain();
        }
    }
}
