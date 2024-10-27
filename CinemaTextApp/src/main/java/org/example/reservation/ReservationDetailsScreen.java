package org.example.reservation;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
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

        Label titleLabel = new Label("Reservation Details")
                .addStyle(SGR.BOLD)
                .setForegroundColor(TextColor.ANSI.CYAN);
        titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        detailsPanel.addComponent(titleLabel);

        detailsPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        detailsPanel.addComponent(new Label("Reservation ID: " + reservation.getId())
                .addStyle(SGR.BOLD).setForegroundColor(TextColor.ANSI.YELLOW)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Movie: " + reservation.getShowtime().getMovie().getTitle())
                .addStyle(SGR.BOLD).setForegroundColor(TextColor.ANSI.MAGENTA)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Showtime: " + reservation.getShowtime().getTime())
                .setForegroundColor(TextColor.ANSI.GREEN)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        detailsPanel.addComponent(new Label("Seat: Row " + reservation.getSeat().getRowNumber() + ", Seat " + reservation.getSeat().getSeatNumber())
                .setForegroundColor(TextColor.ANSI.WHITE)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        detailsPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Panel buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(3));
        buttonPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Button closeButton = new Button("Close", detailsWindow::close);
        closeButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        closeButton.setTheme(getButtonTheme());
        buttonPanel.addComponent(closeButton);

        Button deleteButton = new Button("Delete Reservation", () -> {
            try {
                ReservationService.deleteReservation(reservation.getId());
                MessageDialog.showMessageDialog(gui, "Reservation", "Reservation deleted successfully!");
                detailsWindow.close();
            } catch (Exception e) {
                MessageDialog.showMessageDialog(gui, "Error", "Failed to delete reservation: " + e.getMessage());
            }
        });
        deleteButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        deleteButton.setTheme(getButtonTheme());
        buttonPanel.addComponent(deleteButton);

        detailsPanel.addComponent(buttonPanel);
        detailsPanel.addComponent(new EmptySpace().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        detailsWindow.setComponent(detailsPanel);
        detailsWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));
        detailsWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        gui.addWindowAndWait(detailsWindow);
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
