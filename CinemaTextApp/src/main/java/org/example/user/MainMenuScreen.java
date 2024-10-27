package org.example.user;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Window.Hint;
import org.example.auth.LoginScreen;
import org.example.auth.RegisterScreen;
import org.example.utils.ColorThemes;

import java.util.Arrays;

public class MainMenuScreen {
    private final MultiWindowTextGUI gui;

    public MainMenuScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow();

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Label titleLabel = new Label("Welcome to the Main Menu");
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                .setForegroundColor(TextColor.ANSI.CYAN)
                .setBackgroundColor(TextColor.ANSI.BLACK)
                .addStyle(SGR.BOLD);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        mainPanel.addComponent(titleLabel);
        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Panel buttonPanel = new Panel(new GridLayout(1));
        buttonPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Button loginButton = new Button("Login", () -> {
            window.close();
            new LoginScreen(gui).start();
        });
        loginButton.setTheme(ColorThemes.getButtonTheme());

        Button registerButton = new Button("Register", () -> {
            window.close();
            new RegisterScreen(gui).start();
        });

        registerButton.setTheme(ColorThemes.getButtonTheme());
        buttonPanel.addComponent(loginButton);
        buttonPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        buttonPanel.addComponent(registerButton);

        mainPanel.addComponent(buttonPanel);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        window.setHints(Arrays.asList(Hint.FULL_SCREEN));
        window.setComponent(mainPanel);

        gui.addWindowAndWait(window);
    }


}
