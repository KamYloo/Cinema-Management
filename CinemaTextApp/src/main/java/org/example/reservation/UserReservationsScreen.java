package org.example.reservation;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import org.example.auth.UserPanelScreen;
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

        Label titleLabel = new Label("Your Reservations").addStyle(SGR.BOLD);
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        mainPanel.addComponent(titleLabel);

        mainPanel.addComponent(new EmptySpace());

        ActionListBox reservationListBox = new ActionListBox();
        reservationListBox.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        // Tworzymy listę rezerwacji
        for (ReservationDto reservation : reservations) {
            String reservationDetails = "Reservation ID: " + reservation.getId() +
                    ", Movie: " + reservation.getShowtime().getMovie().getTitle() +
                    ", Seat: " + reservation.getSeat().getRowNumber() + "-" + reservation.getSeat().getSeatNumber();

            // Dodajemy szczegóły rezerwacji i możliwość jej usunięcia
            reservationListBox.addItem(reservationDetails, () -> {
                new ReservationDetailsScreen(gui, reservation).showReservationDetails();
            });
        }

        mainPanel.addComponent(reservationListBox);

        mainPanel.addComponent(new EmptySpace());

        Button backButton = new Button("Back", () -> {
            reservationWindow.close();
            try {
                // Powrót do UserPanelScreen
                new UserPanelScreen(gui).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        mainPanel.addComponent(backButton);

        reservationWindow.setComponent(mainPanel);
        reservationWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        gui.addWindowAndWait(reservationWindow);
    }
}
