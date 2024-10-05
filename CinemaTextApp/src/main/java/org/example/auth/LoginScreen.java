package org.example.auth;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.utils.JwtUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class LoginScreen {
    private final MultiWindowTextGUI gui;

    public LoginScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow("Login");

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel contentPanel = new Panel(new GridLayout(2)); // Siatka 2 kolumn
        contentPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Label titleLabel = new Label("User Login");
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Label emailLabel = new Label("Email:");
        TextBox emailBox = new TextBox().setPreferredSize(new TerminalSize(30, 1));  // Szerokość pola tekstowego

        Label passwordLabel = new Label("Password:");
        TextBox passwordBox = new TextBox().setMask('*').setPreferredSize(new TerminalSize(30, 1)); // Hasło ukryte

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

        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        contentPanel.addComponent(loginButton);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        mainPanel.addComponent(titleLabel);
        mainPanel.addComponent(contentPanel);

        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setComponent(mainPanel);

        gui.addWindowAndWait(window);
    }
}
