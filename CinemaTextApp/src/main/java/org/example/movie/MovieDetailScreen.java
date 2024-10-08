package org.example.movie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import org.example.dto.MovieDto;
import org.example.dto.ShowTimeDto;
import org.example.seat.SeatsScreen;
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

            Panel mainPanel = new Panel();
            mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL)); // Stack elements vertically

            mainPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

            Panel detailsPanel = new Panel(new GridLayout(2).setHorizontalSpacing(2));

            detailsPanel.addComponent(new Label("Title:").addStyle(SGR.BOLD));
            detailsPanel.addComponent(new Label(movie.getTitle()));

            detailsPanel.addComponent(new Label("Description:").addStyle(SGR.BOLD));
            detailsPanel.addComponent(new Label(movie.getDescription()));

            detailsPanel.addComponent(new Label("Genre:").addStyle(SGR.BOLD));
            detailsPanel.addComponent(new Label(movie.getGenre()));

            detailsPanel.addComponent(new Label("Duration:").addStyle(SGR.BOLD));
            detailsPanel.addComponent(new Label(movie.getDuration() + " min"));

            List<ShowTimeDto> showTimes = ShowTimeService.getShowTimesByMovie(movieId);
            detailsPanel.addComponent(new Label("Available Showtimes:").addStyle(SGR.BOLD));

            ActionListBox showTimeListBox = new ActionListBox();
            for (ShowTimeDto showTime : showTimes) {
                showTimeListBox.addItem("Showtime: " + showTime.getTime().toString(), () -> {
                    new SeatsScreen(screen).showSeatSelection(showTime.getId());
                });
            }
            detailsPanel.addComponent(showTimeListBox);

            mainPanel.addComponent(detailsPanel);

            mainPanel.addComponent(new Button("Back", detailsWindow::close)
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

            mainPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

            detailsWindow.setComponent(mainPanel);

            detailsWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.addWindowAndWait(detailsWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
