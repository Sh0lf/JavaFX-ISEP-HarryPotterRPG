package com.isep.hpah.views.console;
import java.util.Scanner;
import java.io.InputStream;

public class SafeScanner {
    private final Scanner sc;
    public SafeScanner(InputStream is){
        this.sc = new Scanner(is);
    }

    public int getInt(){
        System.out.println("-> \n");
        int input = this.sc.nextInt();
        this.sc.nextLine();
        return input;
    }

    public String getString() {
        System.out.println("-> \n");
        return this.sc.nextLine();
    }

    public void closeScanner(){
        this.sc.close();
    }
}
