package org.example.auth;
import com.googlecode.lanterna.gui2.*;

public class UserPanelScreen {
    private final MultiWindowTextGUI gui;

    public UserPanelScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() {
        BasicWindow window = new BasicWindow("User Panel");
        Panel contentPanel = new Panel(new GridLayout(1));

        contentPanel.addComponent(new Label("Welcome to the User Panel!"));
        contentPanel.addComponent(new Button("Logout", () -> {
            window.close();
            new MainMenuScreen(gui).start();
        }));

        window.setComponent(contentPanel);
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(window);
    }
}
