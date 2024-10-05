package org.example;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.example.auth.LoginScreen;
import org.example.auth.MainMenuScreen;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);


            new MainMenuScreen(gui).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}