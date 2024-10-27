package org.example.user;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Window.Hint;
import org.example.auth.LoginScreen;
import org.example.auth.RegisterScreen;

import java.util.Arrays;

public class MainMenuScreen {
    private final MultiWindowTextGUI gui;

    public MainMenuScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow("Main Menu");

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Label titleLabel = new Label("Welcome to the Main Menu");
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1))); // górny odstęp
        mainPanel.addComponent(titleLabel);
        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1))); // odstęp za tytułem

        Panel buttonPanel = new Panel(new GridLayout(1)); // Siatka z jedną kolumną
        buttonPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Button loginButton = new Button("Login", () -> {
            window.close();
            new LoginScreen(gui).start();
        });

        Button registerButton = new Button("Register", () -> {
            window.close();
            new RegisterScreen(gui).start();
        });

        buttonPanel.addComponent(loginButton);
        buttonPanel.addComponent(new EmptySpace(new TerminalSize(1, 1))); // Odstęp między przyciskami
        buttonPanel.addComponent(registerButton);

        mainPanel.addComponent(buttonPanel);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1))); // odstęp na dole

        window.setHints(Arrays.asList(Hint.FULL_SCREEN));
        window.setComponent(mainPanel);

        gui.addWindowAndWait(window);
    }


}
