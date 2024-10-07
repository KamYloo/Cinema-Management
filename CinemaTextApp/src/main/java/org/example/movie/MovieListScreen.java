package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import org.example.auth.UserPanelScreen;
import org.example.dto.MovieDto;
import org.example.user.UserService;
import java.util.Set;

public class MovieListScreen {

    private Screen screen;
    private Panel mainPanel;

    public MovieListScreen(Screen screen) {
        this.screen = screen;
        this.mainPanel = new Panel(new GridLayout(1));
    }

    public void show() throws Exception {
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        BasicWindow window = new BasicWindow("Movie List");

        Table<String> movieTable = new Table<>("ID", "Title", "Genre", "Duration (min)");
        mainPanel.addComponent(new Label("List of Movies:").addStyle(SGR.BOLD));

        refreshMovieTable(movieTable);

        mainPanel.addComponent(movieTable);

        mainPanel.addComponent(new Button("Select Movie", () -> {
            String selectedMovieId = movieTable.getTableModel().getRow(movieTable.getSelectedRow()).get(0);
            try {
                MovieDetailScreen movieDetailsScreen = new MovieDetailScreen(screen);
                movieDetailsScreen.showMovieDetails(Integer.parseInt(selectedMovieId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        if (UserService.isAdmin()) {
            mainPanel.addComponent(new Button("Delete Selected Movie", () -> {
                String selectedMovieId = movieTable.getTableModel().getRow(movieTable.getSelectedRow()).get(0);
                try {
                    MovieService.deleteMovie(Integer.parseInt(selectedMovieId));
                    mainPanel.addComponent(new Label("Movie deleted successfully!"));
                } catch (Exception e) {
                    e.printStackTrace();
                    mainPanel.addComponent(new Label("Failed to delete movie."));
                }
            }));
        }

        mainPanel.addComponent(new Button("Back", () -> {
            window.close();
            try {
                new UserPanelScreen(new MultiWindowTextGUI(screen)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        window.setComponent(mainPanel);
        textGUI.addWindowAndWait(window);


    }

    public void refreshMovieTable(Table<String> movieTable) {
        movieTable.getTableModel().clear();
        try {
            Set<MovieDto> movies = MovieService.getAllMovies();
            for (MovieDto movie : movies) {
                movieTable.getTableModel().addRow(
                        String.valueOf(movie.getId()),
                        movie.getTitle(),
                        movie.getGenre(),
                        String.valueOf(movie.getDuration())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            mainPanel.addComponent(new Label("Failed to load movies."));
        }
    }
}
