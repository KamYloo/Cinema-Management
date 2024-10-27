package org.example.reservation;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import org.example.user.UserPanelScreen;
import org.example.dto.ReservationDto;
import org.example.user.UserService;

import java.util.List;

public class UserReservationsScreen {
    private final MultiWindowTextGUI gui;

    public UserReservationsScreen(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void showUserReservations() throws Exception {
        Integer userId = UserService.getLoggedInUserId();
        List<ReservationDto> reservations = ReservationService.getReservationsByUser(userId);

        BasicWindow reservationWindow = new BasicWindow("Your Reservations");
        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        Label titleLabel = new Label("Your Reservations")
                .addStyle(SGR.BOLD)
                .setForegroundColor(TextColor.ANSI.CYAN);
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        mainPanel.addComponent(titleLabel);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        ActionListBox reservationListBox = new ActionListBox();
        reservationListBox.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        for (ReservationDto reservation : reservations) {
            String reservationDetails = String.format("ID: %d, Movie: %s, Seat: %s-%s",
                    reservation.getId(),
                    reservation.getShowtime().getMovie().getTitle(),
                    reservation.getSeat().getRowNumber(),
                    reservation.getSeat().getSeatNumber()
            );

            reservationListBox.addItem(reservationDetails, () -> {
                new ReservationDetailsScreen(gui, reservation).showReservationDetails();
            });
        }
        reservationListBox.setTheme(getButtonTheme());
        mainPanel.addComponent(reservationListBox);

        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Button backButton = new Button("Back", () -> {
            reservationWindow.close();
            try {
                new UserPanelScreen(gui).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        backButton.setTheme(getButtonTheme());
        mainPanel.addComponent(backButton);

        reservationWindow.setComponent(mainPanel);
        reservationWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        reservationWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        gui.addWindowAndWait(reservationWindow);
    }

    private Theme getButtonTheme() {
        return SimpleTheme.makeTheme(
                true,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLUE,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLUE,
                TextColor.ANSI.BLACK,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLACK
        );
    }
}
