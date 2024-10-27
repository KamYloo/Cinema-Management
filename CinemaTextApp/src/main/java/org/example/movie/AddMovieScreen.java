package org.example.movie;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.user.UserPanelScreen;
import org.example.dto.MovieDto;
import org.example.utils.ColorThemes;
import org.example.utils.JwtUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddMovieScreen {
    private final MultiWindowTextGUI gui;
    private final List<LocalDateTime> showTimes = new ArrayList<>();
    private final UserPanelScreen userPanelScreen;

    public AddMovieScreen(MultiWindowTextGUI gui, UserPanelScreen userPanelScreen) {
        this.gui = gui;
        this.userPanelScreen = userPanelScreen;
    }

    public void start() {
        BasicWindow window = new BasicWindow("Add Movie");
        Panel contentPanel = new Panel(new GridLayout(2));

        Theme textBoxTheme = SimpleTheme.makeTheme(
                true,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLUE,
                TextColor.ANSI.BLACK,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.WHITE
        );

        contentPanel.addComponent(new Label("Title:"));
        TextBox titleBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING));
        titleBox.setTheme(textBoxTheme);
        contentPanel.addComponent(titleBox);

        contentPanel.addComponent(new Label("Description:"));
        TextBox descriptionBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING));
        descriptionBox.setTheme(textBoxTheme);
        contentPanel.addComponent(descriptionBox);

        contentPanel.addComponent(new Label("Image URL:"));
        TextBox imageBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING));
        imageBox.setTheme(textBoxTheme);
        contentPanel.addComponent(imageBox);

        contentPanel.addComponent(new Label("Genre:"));
        TextBox genreBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING));
        genreBox.setTheme(textBoxTheme);
        contentPanel.addComponent(genreBox);

        contentPanel.addComponent(new Label("Duration (minutes):"));
        TextBox durationBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING));
        durationBox.setTheme(textBoxTheme);
        contentPanel.addComponent(durationBox);

        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        contentPanel.addComponent(new Label("Add ShowTime (yyyy-MM-dd HH:mm):"));
        TextBox showTimeBox = new TextBox();
        showTimeBox.setTheme(textBoxTheme);
        contentPanel.addComponent(showTimeBox);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Button addShowTimeButton = new Button("Add ShowTime", () -> {
            try {
                LocalDateTime showTime = LocalDateTime.parse(showTimeBox.getText(), formatter);
                showTimes.add(showTime);
                showMessage("ShowTime added: " + showTime.toString(), TextColor.ANSI.GREEN);
            } catch (Exception e) {
                showMessage("Invalid ShowTime format. Use 'yyyy-MM-dd HH:mm'", TextColor.ANSI.RED);
            }
        });
        addShowTimeButton.setTheme(ColorThemes.getButtonTheme());
        contentPanel.addComponent(addShowTimeButton);

        Button backButton = new Button("Back", () -> {
            try {
                userPanelScreen.start();
            } catch (Exception e) {
                showMessage("Error navigating back: " + e.getMessage(), TextColor.ANSI.RED);
            }
        });
        backButton.setTheme(ColorThemes.getButtonTheme());
        contentPanel.addComponent(backButton);

        Button submitButton = new Button("Submit", () -> {
            try {
                int durationInMinutes = Integer.parseInt(durationBox.getText());
                String jsonBody = String.format(
                        "{\"title\": \"%s\", \"description\": \"%s\", \"image\": \"%s\", \"genre\": \"%s\", \"duration\": %d, \"showTimes\": %s}",
                        titleBox.getText(),
                        descriptionBox.getText(),
                        imageBox.getText(),
                        genreBox.getText(),
                        durationInMinutes,
                        buildShowTimesJson()
                );

                String token = JwtUtils.getJwtToken();
                MovieDto createdMovie = MovieService.createMovie(jsonBody, token);

                if (createdMovie != null) {
                    showMessage("Movie created successfully!", TextColor.ANSI.GREEN);
                    userPanelScreen.start();
                } else {
                    showMessage("Error: Movie creation failed", TextColor.ANSI.RED);
                }
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage(), TextColor.ANSI.RED);
            }
        });
        submitButton.setTheme(ColorThemes.getButtonTheme());
        contentPanel.addComponent(submitButton);

        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        window.setComponent(contentPanel);
        window.setHints(List.of(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(window);
    }

    private String buildShowTimesJson() {
        StringBuilder showTimesJson = new StringBuilder("[");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        for (int i = 0; i < showTimes.size(); i++) {
            showTimesJson.append(String.format("{\"time\": \"%s\"}", showTimes.get(i).format(isoFormatter)));
            if (i < showTimes.size() - 1) {
                showTimesJson.append(",");
            }
        }
        showTimesJson.append("]");
        return showTimesJson.toString();
    }

    private void showMessage(String message, TextColor color) {
        MessageDialog.showMessageDialog(gui, "Info", message, MessageDialogButton.OK);
    }
}
