package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.TerminalPosition;
import org.example.auth.UserPanelScreen;
import org.example.dto.MovieDto;
import org.example.user.UserService;

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

    public void show() throws Exception {
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        BasicWindow window = new BasicWindow();

        window.setHints(java.util.Collections.singletonList(Hint.FULL_SCREEN));

        Panel containerPanel = new Panel(new BorderLayout());

        containerPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)), BorderLayout.Location.TOP);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)), BorderLayout.Location.BOTTOM);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(1, 0)), BorderLayout.Location.LEFT);
        containerPanel.addComponent(new EmptySpace(new TerminalSize(1, 0)), BorderLayout.Location.RIGHT);

        Label titleLabel = new Label("List of Movies:").addStyle(SGR.BOLD);
        titleLabel.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER));
        mainPanel.addComponent(titleLabel);

        Panel searchPanel = new Panel(new LinearLayout());

        searchTextBox = new TextBox().setPreferredSize(new TerminalSize(20, 1));
        searchPanel.addComponent(searchTextBox);

        Button searchButton = new Button("Search", this::searchMovies);
        searchPanel.addComponent(searchButton);

        mainPanel.addComponent(searchPanel.withBorder(Borders.singleLine()).setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER)));

        movieListBox = new ActionListBox(new TerminalSize(30, 10));  // Rozmiar listy filmów
        refreshMovieList(movieListBox);
        mainPanel.addComponent(movieListBox.withBorder(Borders.singleLine("Movies")));

        Button backButton = new Button("Back", () -> {
            window.close();
            try {
                new UserPanelScreen(new MultiWindowTextGUI(screen)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mainPanel.addComponent(backButton.withBorder(Borders.singleLine()).setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER)));

        containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);

        window.setComponent(containerPanel);
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
