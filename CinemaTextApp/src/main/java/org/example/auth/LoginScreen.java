package org.example.auth;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.user.UserPanelScreen;
import org.example.utils.ColorThemes;

import java.util.Arrays;

public class LoginScreen {
    private final MultiWindowTextGUI gui;

    public LoginScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow();

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel contentPanel = new Panel(new GridLayout(2));
        contentPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Label titleLabel = new Label("User Login");
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                .setForegroundColor(TextColor.ANSI.CYAN)
                .addStyle(SGR.BOLD);


        Label emailLabel = new Label("Email:");
        emailLabel.setForegroundColor(TextColor.ANSI.WHITE);
        TextBox emailBox = new TextBox().setPreferredSize(new TerminalSize(30, 1));
        emailBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE));

        Label passwordLabel = new Label("Password:");
        passwordLabel.setForegroundColor(TextColor.ANSI.WHITE);
        TextBox passwordBox = new TextBox().setMask('*').setPreferredSize(new TerminalSize(30, 1));
        passwordBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE));

        contentPanel.addComponent(emailLabel);
        contentPanel.addComponent(emailBox);

        contentPanel.addComponent(passwordLabel);
        contentPanel.addComponent(passwordBox);

        Button loginButton = new Button("Login", () -> {
            String email = emailBox.getText();
            String password = passwordBox.getText();

            try {
                boolean success = AuthService.login(email, password);
                if (success) {
                    window.close();
                    new UserPanelScreen(gui).start();
                } else {
                    new MessageDialogBuilder()
                            .setTitle("Login Failed")
                            .setText("Incorrect email or password.")
                            .addButton(MessageDialogButton.OK)
                            .build()
                            .showDialog(gui);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loginButton.setTheme(ColorThemes.getButtonTheme());
        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        contentPanel.addComponent(loginButton);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        mainPanel.addComponent(titleLabel);
        mainPanel.addComponent(contentPanel);

        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setComponent(mainPanel);

        gui.addWindowAndWait(window);
    }
}
