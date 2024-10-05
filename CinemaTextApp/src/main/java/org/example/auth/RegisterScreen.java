package org.example.auth;
import com.googlecode.lanterna.gui2.*;
import org.example.utils.JwtUtils;

public class RegisterScreen {
    private final MultiWindowTextGUI gui;

    public RegisterScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow("Register");
        Panel contentPanel = new Panel(new GridLayout(2));

        TextBox fullNameBox = new TextBox();
        TextBox emailBox = new TextBox();
        TextBox passwordBox = new TextBox().setMask('*');
        TextBox roleBox = new TextBox();

        contentPanel.addComponent(new Label("Full Name"));
        contentPanel.addComponent(fullNameBox);

        contentPanel.addComponent(new Label("Email"));
        contentPanel.addComponent(emailBox);

        contentPanel.addComponent(new Label("Password"));
        contentPanel.addComponent(passwordBox);

        contentPanel.addComponent(new Label("Role (Admin/User)"));
        contentPanel.addComponent(roleBox);

        contentPanel.addComponent(new EmptySpace());
        contentPanel.addComponent(new Button("Register", () -> {
            String fullName = fullNameBox.getText();
            String email = emailBox.getText();
            String password = passwordBox.getText();
            String role = roleBox.getText();

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
        }));

        window.setComponent(contentPanel);
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(window);
    }
}
