package org.example.auth;
import com.googlecode.lanterna.gui2.*;
import org.example.movie.AddMovieScreen;
import org.example.movie.MovieListScreen;
import org.example.reservation.UserReservationsScreen;
import org.example.user.UserService;

public class UserPanelScreen {
    private final MultiWindowTextGUI gui;


    public UserPanelScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() throws Exception {
        BasicWindow window = new BasicWindow("User Panel");
        Panel contentPanel = new Panel(new GridLayout(1));
        boolean isAdmin = UserService.isAdmin();
        contentPanel.addComponent(new Label("Welcome to the User Panel!"));
        if (isAdmin) {
            contentPanel.addComponent(new Button("AddMovie", () -> {
                window.close();
                try {
                    new AddMovieScreen(gui, this).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        contentPanel.addComponent(new Button("Movies", () -> {
            window.close();
            try {
                new MovieListScreen(gui.getScreen()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        contentPanel.addComponent(new Button("Show Reservations", () -> {
            window.close();
            try {
                new UserReservationsScreen(gui).showUserReservations();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        contentPanel.addComponent(new Button("Logout", () -> {
            window.close();
            new MainMenuScreen(gui).start();
        }));


        window.setComponent(contentPanel);
        window.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(window);
    }
}
