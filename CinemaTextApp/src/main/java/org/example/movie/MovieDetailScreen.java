package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.SimpleTheme;
import org.example.dto.MovieDto;
import org.example.dto.ShowTimeDto;
import org.example.seat.SeatsScreen;
import org.example.showTime.ShowTimeService;
import org.example.user.UserService;
import org.example.utils.ColorThemes;

import java.util.List;

public class MovieDetailScreen {

    private final Screen screen;

    public MovieDetailScreen(Screen screen) {
        this.screen = screen;
    }

    public void showMovieDetails(Integer movieId) {
        try {
            MovieDto movie = MovieService.getMovie(movieId);
            BasicWindow detailsWindow = new BasicWindow("Movie Details: " + movie.getTitle());

            Panel mainPanel = new Panel();
            mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            Panel infoPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            infoPanel.addComponent(new Label("Title:")
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.CYAN));
            infoPanel.addComponent(new Label(movie.getTitle())
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.YELLOW));

            infoPanel.addComponent(new Label("Description:")
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.CYAN));
            Label descriptionLabel = new Label(movie.getDescription())
                    .addStyle(SGR.UNDERLINE)
                    .setForegroundColor(TextColor.ANSI.WHITE);
            descriptionLabel.setPreferredSize(new TerminalSize(50, 5));
            descriptionLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            infoPanel.addComponent(descriptionLabel);

            infoPanel.addComponent(new Label("Genre:").addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.CYAN));
            infoPanel.addComponent(new Label(movie.getGenre())
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.MAGENTA));

            infoPanel.addComponent(new Label("Duration:").addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.CYAN));
            infoPanel.addComponent(new Label(movie.getDuration() + " min")
                    .setForegroundColor(TextColor.ANSI.GREEN));

            mainPanel.addComponent(infoPanel);
            mainPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

            Panel showTimesPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            showTimesPanel.addComponent(new Label("Available Showtimes:")
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.CYAN));

            ActionListBox showTimeListBox = new ActionListBox();
            showTimeListBox.setTheme(ColorThemes.getButtonTheme());

            List<ShowTimeDto> showTimes = ShowTimeService.getShowTimesByMovie(movieId);
            for (ShowTimeDto showTime : showTimes) {
                showTimeListBox.addItem("Showtime: " + showTime.getTime().toString(), () -> {
                    new SeatsScreen(screen).showSeatSelection(showTime.getId());
                });
            }
            showTimesPanel.addComponent(showTimeListBox);
            mainPanel.addComponent(showTimesPanel);

            mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            Panel buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(5));

            Button backButton = new Button("Back", detailsWindow::close);
            backButton.setTheme(ColorThemes.getButtonTheme());
            buttonPanel.addComponent(backButton);

            if (UserService.isAdmin()) {
                Button deleteButton = new Button("Delete This Movie", () -> {
                    try {
                        MovieService.deleteMovie(movieId);
                        detailsWindow.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mainPanel.addComponent(new Label("Failed to delete movie.")
                                .setForegroundColor(TextColor.ANSI.RED)
                                .addStyle(SGR.BOLD));
                    }
                });
                deleteButton.setTheme(ColorThemes.getButtonTheme());
                buttonPanel.addComponent(deleteButton);
            }

            mainPanel.addComponent(buttonPanel);
            mainPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

            detailsWindow.setComponent(mainPanel);
            detailsWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
            detailsWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.addWindowAndWait(detailsWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
