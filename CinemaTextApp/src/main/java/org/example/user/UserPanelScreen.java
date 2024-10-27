package org.example.user;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;

import org.example.movie.AddMovieScreen;
import org.example.movie.MovieListScreen;
import org.example.reservation.UserReservationsScreen;

public class UserPanelScreen {
    private final MultiWindowTextGUI gui;

    public UserPanelScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() throws Exception {
        BasicWindow window = new BasicWindow();

        // Główny panel z ustawionym układem pionowym (Vertical)
        Panel contentPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        boolean isAdmin = UserService.isAdmin();

        // Etykieta powitalna
        Label welcomeLabel = new Label("Welcome to the User Panel!")
                .setForegroundColor(TextColor.ANSI.CYAN)
                .setBackgroundColor(TextColor.ANSI.BLACK)
                .addStyle(SGR.BOLD)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(welcomeLabel);

        // Dodanie pustej przestrzeni nad przyciskami, aby je lepiej wycentrować
        contentPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Stworzenie motywu dla przycisków
        Theme buttonTheme = SimpleTheme.makeTheme(
                true,                           // Użyj pogrubienia dla aktywnych komponentów
                TextColor.ANSI.WHITE,           // Kolor bazowy tekstu
                TextColor.ANSI.BLUE,            // Kolor tła przycisków
                TextColor.ANSI.WHITE,           // Kolor tekstu w polach edytowalnych
                TextColor.ANSI.BLUE,            // Kolor tła dla pól edytowalnych
                TextColor.ANSI.BLACK,           // Kolor tekstu po najechaniu
                TextColor.ANSI.WHITE,           // Kolor tła po najechaniu
                TextColor.ANSI.BLACK            // Kolor tła GUI
        );

        // Przyciski z wycentrowanym układem
        if (isAdmin) {
            Button addMovieButton = new Button("Add Movie", () -> {
                window.close();
                try {
                    new AddMovieScreen(gui, this).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            addMovieButton.setTheme(buttonTheme);
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
        moviesButton.setTheme(buttonTheme);
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
        showReservationsButton.setTheme(buttonTheme);
        showReservationsButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(showReservationsButton);

        Button logoutButton = new Button("Logout", () -> {
            window.close();
            new MainMenuScreen(gui).start();
        });
        logoutButton.setTheme(buttonTheme);
        logoutButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(logoutButton);

        // Dodanie pustej przestrzeni pod przyciskami, aby je lepiej wycentrować
        contentPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Ustawienie okna z wyśrodkowanym panelem
        window.setComponent(contentPanel);
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

        // Wyświetlenie okna
        gui.addWindowAndWait(window);
    }
}
