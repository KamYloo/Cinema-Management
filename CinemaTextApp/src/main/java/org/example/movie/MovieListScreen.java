package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.SimpleTheme;
import org.example.user.UserPanelScreen;
import org.example.dto.MovieDto;
import org.example.utils.ColorThemes;

import java.util.Set;

public class MovieListScreen {

    private Screen screen;
    private Panel mainPanel;
    private TextBox searchTextBox;
    private ActionListBox movieListBox;

    public MovieListScreen(Screen screen) {
        this.screen = screen;
        this.mainPanel = new Panel(new GridLayout(1));
    }

    public void show() {
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        BasicWindow window = new BasicWindow();

        window.setHints(java.util.Collections.singletonList(Window.Hint.FULL_SCREEN));

        Panel containerPanel = new Panel(new BorderLayout());

        containerPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)), BorderLayout.Location.TOP);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)), BorderLayout.Location.BOTTOM);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(1, 0)), BorderLayout.Location.LEFT);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(1, 0)), BorderLayout.Location.RIGHT);

        Label titleLabel = new Label("List of Movies:")
                .setForegroundColor(TextColor.ANSI.CYAN)
                .addStyle(SGR.BOLD)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        mainPanel.addComponent(titleLabel);

        Panel searchPanel = new Panel(new GridLayout(2));
        searchTextBox = new TextBox().setPreferredSize(new TerminalSize(20, 1));
        searchPanel.addComponent(searchTextBox);

        Button searchButton = new Button("Search", this::searchMovies);
        searchButton.setTheme(ColorThemes.getButtonTheme());
        searchPanel.addComponent(searchButton);

        mainPanel.addComponent(searchPanel.withBorder(Borders.singleLine("Search")).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

        movieListBox = new ActionListBox(new TerminalSize(30, 10));
        refreshMovieList(movieListBox);
        movieListBox.setTheme(ColorThemes.getButtonTheme());
        mainPanel.addComponent(movieListBox.withBorder(Borders.singleLine("Movies")).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

        Button backButton = new Button("Back", () -> {
            window.close();
            try {
                new UserPanelScreen(new MultiWindowTextGUI(screen)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        backButton.setTheme(ColorThemes.getButtonTheme());
        mainPanel.addComponent(backButton.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

        containerPanel.addComponent(mainPanel.withBorder(Borders.doubleLine()), BorderLayout.Location.CENTER);

        window.setComponent(containerPanel);
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        textGUI.addWindowAndWait(window);
    }

    public void refreshMovieList(ActionListBox movieListBox) {
        refreshMovieList(movieListBox, null);
    }

    public void refreshMovieList(ActionListBox movieListBox, String searchQuery) {
        movieListBox.clearItems();
        try {
            Set<MovieDto> movies;
            if (searchQuery == null || searchQuery.isEmpty()) {
                movies = MovieService.getAllMovies();
            } else {
                movies = MovieService.searchMovie(searchQuery);
            }

            for (MovieDto movie : movies) {
                movieListBox.addItem(movie.getTitle() + " (" + movie.getGenre() + ")", () -> {
                    try {
                        MovieDetailScreen movieDetailsScreen = new MovieDetailScreen(screen);
                        movieDetailsScreen.showMovieDetails(movie.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            mainPanel.addComponent(new Label("Failed to load movies."));
        }
    }

    private void searchMovies() {
        String searchQuery = searchTextBox.getText();
        refreshMovieList(movieListBox, searchQuery);
    }
}
