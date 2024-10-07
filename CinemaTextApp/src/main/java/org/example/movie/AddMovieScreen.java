package org.example.movie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.auth.UserPanelScreen;
import org.example.dto.MovieDto;
import org.example.utils.JwtUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddMovieScreen {
    private final MultiWindowTextGUI gui;
    private final List<LocalDateTime> showTimes = new ArrayList<>();
    private final UserPanelScreen userPanelScreen; // Add reference to UserPanelScreen

    public AddMovieScreen(MultiWindowTextGUI gui, UserPanelScreen userPanelScreen) {
        this.gui = gui;
        this.userPanelScreen = userPanelScreen; // Initialize UserPanelScreen reference
    }

    public void start() throws Exception {
        BasicWindow window = new BasicWindow("Add Movie");
        Panel contentPanel = new Panel(new GridLayout(2)); // Layout with 2 columns

        contentPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));

        contentPanel.addComponent(new Label("Title:"));
        TextBox titleBox = new TextBox();
        contentPanel.addComponent(titleBox);

        contentPanel.addComponent(new Label("Description:"));
        TextBox descriptionBox = new TextBox();
        contentPanel.addComponent(descriptionBox);

        contentPanel.addComponent(new Label("Image URL:"));
        TextBox imageBox = new TextBox();
        contentPanel.addComponent(imageBox);

        contentPanel.addComponent(new Label("Genre:"));
        TextBox genreBox = new TextBox();
        contentPanel.addComponent(genreBox);

        contentPanel.addComponent(new Label("Duration (minutes):"));
        TextBox durationBox = new TextBox();
        contentPanel.addComponent(durationBox);

        contentPanel.addComponent(new EmptySpace());

        contentPanel.addComponent(new Label("Add ShowTime (yyyy-MM-dd HH:mm):"));
        TextBox showTimeBox = new TextBox();
        contentPanel.addComponent(showTimeBox);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Button addShowTimeButton = new Button("Add ShowTime", () -> {
            try {
                LocalDateTime showTime = LocalDateTime.parse(showTimeBox.getText(), formatter);
                showTimes.add(showTime);
                showMessage("ShowTime added: " + showTime.toString(), TextColor.ANSI.GREEN);
            } catch (Exception e) {
                showMessage("Invalid ShowTime format. Please use 'yyyy-MM-dd HH:mm'", TextColor.ANSI.RED);
            }
        });
        contentPanel.addComponent(addShowTimeButton);

        Button submitButton = new Button("Submit", () -> {
            try {
                StringBuilder showTimesJson = new StringBuilder("[");
                for (int i = 0; i < showTimes.size(); i++) {
                    showTimesJson.append(String.format("{\"time\": \"%s\"}", showTimes.get(i).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                    if (i < showTimes.size() - 1) {
                        showTimesJson.append(",");
                    }
                }
                showTimesJson.append("]");

                int durationInMinutes = Integer.parseInt(durationBox.getText());
                String jsonBody = String.format(
                        "{\"title\": \"%s\", \"description\": \"%s\", \"image\": \"%s\", \"genre\": \"%s\", \"duration\": %d, \"showTimes\": %s}",
                        titleBox.getText(),
                        descriptionBox.getText(),
                        imageBox.getText(),
                        genreBox.getText(),
                        durationInMinutes,
                        showTimesJson.toString()
                );

                String token = JwtUtils.getJwtToken();

                MovieDto createdMovie = MovieService.createMovie(jsonBody, token);

                if (createdMovie != null) {
                    showMessage("Movie created successfully!", TextColor.ANSI.GREEN);
                    userPanelScreen.start();
                } else {
                    showMessage("Error creating movie: Response is null", TextColor.ANSI.RED);
                }
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage(), TextColor.ANSI.RED);
            }
        });

        contentPanel.addComponent(submitButton);

        Button backButton = new Button("Back", () -> {
            try {
                userPanelScreen.start();
            } catch (Exception e) {
                showMessage("Error navigating back: " + e.getMessage(), TextColor.ANSI.RED);
            }
        });
        contentPanel.addComponent(backButton);

        window.setComponent(contentPanel);
        window.setHints(List.of(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(window);
    }

    private void showMessage(String message, TextColor color) {
        MessageDialog.showMessageDialog(gui, "Info", message, MessageDialogButton.OK);
    }
}
