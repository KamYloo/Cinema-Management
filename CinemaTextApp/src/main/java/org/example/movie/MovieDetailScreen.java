package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import org.example.dto.MovieDto;
import org.example.dto.ShowTimeDto;
import org.example.showTime.ShowTimeService;

import java.util.List;

public class MovieDetailScreen {

    private Screen screen;

    public MovieDetailScreen(Screen screen) {
        this.screen = screen;
    }

    public void showMovieDetails(Integer movieId) {
        try {
            MovieDto movie = MovieService.getMovie(movieId);

            BasicWindow detailsWindow = new BasicWindow("Movie Details: " + movie.getTitle());
            Panel detailsPanel = new Panel(new GridLayout(1));

            detailsPanel.addComponent(new Label("Title: " + movie.getTitle()).addStyle(SGR.BOLD));
            detailsPanel.addComponent(new Label("Description: " + movie.getDescription()));
            detailsPanel.addComponent(new Label("Genre: " + movie.getGenre()));
            detailsPanel.addComponent(new Label("Duration: " + movie.getDuration() + " min"));

            List<ShowTimeDto> showTimes = ShowTimeService.getShowTimesByMovie(movieId);
            detailsPanel.addComponent(new Label("Available Showtimes:").addStyle(SGR.BOLD));

            Table<String> showTimeTable = new Table<>("Showtime ID", "Time");
            for (ShowTimeDto showTime : showTimes) {
                showTimeTable.getTableModel().addRow(
                        String.valueOf(showTime.getId()),
                        showTime.getTime().toString()
                );
            }

            detailsPanel.addComponent(showTimeTable);

            detailsPanel.addComponent(new Button("Select Showtime", () -> {
                String selectedShowtimeId = showTimeTable.getTableModel().getRow(showTimeTable.getSelectedRow()).get(0);
                detailsPanel.addComponent(new Label("Selected showtime: " + selectedShowtimeId));
            }));

            detailsPanel.addComponent(new Button("Back", () -> {
                detailsWindow.close();
                try {
                    new MovieListScreen(screen).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));

            detailsWindow.setComponent(detailsPanel);

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.addWindowAndWait(detailsWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
