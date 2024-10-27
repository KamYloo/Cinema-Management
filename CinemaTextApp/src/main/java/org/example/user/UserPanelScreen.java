package org.example.user;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;

import org.example.movie.AddMovieScreen;
import org.example.movie.MovieListScreen;
import org.example.reservation.UserReservationsScreen;
import org.example.utils.ColorThemes;

public class UserPanelScreen {
    private final MultiWindowTextGUI gui;

    public UserPanelScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() throws Exception {
        BasicWindow window = new BasicWindow();

        Panel contentPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        boolean isAdmin = UserService.isAdmin();

        Label welcomeLabel = new Label("Welcome to the User Panel!")
                .setForegroundColor(TextColor.ANSI.CYAN)
                .addStyle(SGR.BOLD)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(welcomeLabel);

        contentPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        if (isAdmin) {
            Button addMovieButton = new Button("Add Movie", () -> {
                window.close();
                try {
                    new AddMovieScreen(gui, this).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            addMovieButton.setTheme(ColorThemes.getButtonTheme());
            addMovieButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(addMovieButton);
        }

        Button moviesButton = new Button("Movies", () -> {
            window.close();
            try {
                new MovieListScreen(gui.getScreen()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        moviesButton.setTheme(ColorThemes.getButtonTheme());
        moviesButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(moviesButton);

        Button showReservationsButton = new Button("Show Reservations", () -> {
            window.close();
            try {
                new UserReservationsScreen(gui).showUserReservations();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        showReservationsButton.setTheme(ColorThemes.getButtonTheme());
        showReservationsButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(showReservationsButton);

        Button logoutButton = new Button("Logout", () -> {
            window.close();
            new MainMenuScreen(gui).start();
        });
        logoutButton.setTheme(ColorThemes.getButtonTheme());
        logoutButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(logoutButton);

        contentPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        window.setComponent(contentPanel);
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

        gui.addWindowAndWait(window);
    }
}
