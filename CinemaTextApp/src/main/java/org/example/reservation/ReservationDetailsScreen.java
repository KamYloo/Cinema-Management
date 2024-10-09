package org.example.reservation;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.example.dto.ReservationDto;

public class ReservationDetailsScreen {
    private final MultiWindowTextGUI gui;
    private final ReservationDto reservation;

    public ReservationDetailsScreen(MultiWindowTextGUI gui, ReservationDto reservation) {
        this.gui = gui;
        this.reservation = reservation;
    }

    public void showReservationDetails() {
        BasicWindow detailsWindow = new BasicWindow("Reservation Details");
        Panel detailsPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        // Wyświetlenie szczegółów rezerwacji
        Label titleLabel = new Label("Reservation Details").addStyle(SGR.BOLD);
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        detailsPanel.addComponent(titleLabel);

        detailsPanel.addComponent(new EmptySpace());

        detailsPanel.addComponent(new Label("Reservation ID: " + reservation.getId())
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Movie: " + reservation.getShowtime().getMovie().getTitle())
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Showtime: " + reservation.getShowtime().getTime())
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Seat: Row " + reservation.getSeat().getRowNumber() + ", Seat " + reservation.getSeat().getSeatNumber())
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        detailsPanel.addComponent(new EmptySpace());

        // Przycisk usuwania rezerwacji
        Button deleteButton = new Button("Delete Reservation", () -> {
            try {
                ReservationService.deleteReservation(reservation.getId());
                MessageDialog.showMessageDialog(gui, "Reservation", "Reservation deleted successfully!");
                detailsWindow.close(); // Zamykamy okno szczegółów po usunięciu rezerwacji
            } catch (Exception e) {
                MessageDialog.showMessageDialog(gui, "Error", "Failed to delete reservation: " + e.getMessage());
            }
        });
        deleteButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        detailsPanel.addComponent(deleteButton);

        // Przycisk zamykający okno
        Button closeButton = new Button("Close", detailsWindow::close);
        closeButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        detailsPanel.addComponent(closeButton);

        // Ustawienie komponentów i pełnego ekranu
        detailsWindow.setComponent(detailsPanel);
        detailsWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));  // Zmieniamy rozmiar na pełny ekran
        gui.addWindowAndWait(detailsWindow);
    }
}
