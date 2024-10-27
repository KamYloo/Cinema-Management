package org.example.auth;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import org.example.utils.ColorThemes;

public class RegisterScreen {
    private final MultiWindowTextGUI gui;

    public RegisterScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow();
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel contentPanel = new Panel(new GridLayout(2));
        contentPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Label titleLabel = new Label("User Register");
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                .setForegroundColor(TextColor.ANSI.CYAN)
                .addStyle(SGR.BOLD);

        TextBox fullNameBox = new TextBox().setPreferredSize(new TerminalSize(30, 1));
        TextBox emailBox = new TextBox().setPreferredSize(new TerminalSize(30, 1));
        TextBox passwordBox = new TextBox().setMask('*').setPreferredSize(new TerminalSize(30, 1));
        TextBox roleBox = new TextBox().setPreferredSize(new TerminalSize(30, 1));

        contentPanel.addComponent(new Label("Full Name").setForegroundColor(TextColor.ANSI.WHITE));
        contentPanel.addComponent(fullNameBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE)));

        contentPanel.addComponent(new Label("Email").setForegroundColor(TextColor.ANSI.WHITE));
        contentPanel.addComponent(emailBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE)));

        contentPanel.addComponent(new Label("Password").setForegroundColor(TextColor.ANSI.WHITE));
        contentPanel.addComponent(passwordBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE)));

        contentPanel.addComponent(new Label("Role (Admin/User)").setForegroundColor(TextColor.ANSI.WHITE));
        contentPanel.addComponent(roleBox.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE)));

        contentPanel.addComponent(new EmptySpace());
        contentPanel.addComponent((new Button("Register", () -> {
            String fullName = fullNameBox.getText().trim();
            String email = emailBox.getText().trim();
            String password = passwordBox.getText().trim();
            String role = roleBox.getText().trim().toLowerCase();

            try {
                boolean success = AuthService.register(fullName, email, password, role);
                if (success) {
                    window.close();
                    System.out.println("Registration successful! Please login.");
                    new LoginScreen(gui).start();
                } else {
                    System.out.println("Registration failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).setTheme(ColorThemes.getButtonTheme()));

        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        mainPanel.addComponent(titleLabel);
        mainPanel.addComponent(contentPanel);

        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setComponent(mainPanel);
        gui.addWindowAndWait(window);
    }
}
