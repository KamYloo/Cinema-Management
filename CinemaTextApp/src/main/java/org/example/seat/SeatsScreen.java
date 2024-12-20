package org.example.seat;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import org.example.dto.ReservationDto;
import org.example.dto.SeatDto;
import org.example.reservation.ReservationService;
import org.example.utils.ColorThemes;

import java.util.List;

public class SeatsScreen {

    private final Screen screen;
    private Panel seatPanel;
    private ActionListBox seatListBox;
    private Integer currentShowTimeId;

    public SeatsScreen(Screen screen) {
        this.screen = screen;
    }

    public void showSeatSelection(Integer showTimeId) {
        this.currentShowTimeId = showTimeId;
        try {
            BasicWindow seatWindow = new BasicWindow();

            Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            mainPanel.addComponent(new EmptySpace());

            Label titleLabel = new Label("Available Seats")
                    .setForegroundColor(TextColor.ANSI.CYAN)
                    .addStyle(SGR.BOLD)
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            mainPanel.addComponent(titleLabel);

            seatPanel = new Panel(new GridLayout(2).setHorizontalSpacing(3).setVerticalSpacing(1));
            seatPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            seatListBox = new ActionListBox();
            seatListBox.setTheme(ColorThemes.getButtonTheme());

            // Populate the seat list initially
            updateSeatList();

            seatPanel.addComponent(seatListBox);
            mainPanel.addComponent(seatPanel);

            Button backButton = new Button("Back", seatWindow::close);
            backButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            backButton.setTheme(ColorThemes.getButtonTheme());
            mainPanel.addComponent(backButton);

            mainPanel.addComponent(new EmptySpace());

            seatWindow.setComponent(mainPanel);
            seatWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            seatWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.addWindowAndWait(seatWindow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSeatList() {
        try {
            seatListBox.clearItems();  // Clear existing items

            List<SeatDto> seats = SeatService.getSeatsByShowTime(currentShowTimeId);

            for (SeatDto seat : seats) {
                String seatLabel = "Row: " + seat.getRowNumber() + ", Seat: " + seat.getSeatNumber() +
                        (seat.isReserved() ? " [Reserved]" : " [Available]");

                if (seat.isReserved()) {
                    seatListBox.addItem(seatLabel, () -> {
                        MessageDialog.showMessageDialog(new MultiWindowTextGUI(screen), "Reservation Error",
                                "This seat is already reserved. Please select another seat.");
                    });
                } else {
                    seatListBox.addItem(seatLabel, () -> reserveSeat(seat.getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reserveSeat(Integer seatId) {
        try {
            ReservationDto reservation = ReservationService.createReservation(seatId);

            MessageDialog.showMessageDialog(new MultiWindowTextGUI(screen), "Reservation", "Seat reserved successfully!");

            // Refresh the seat list to reflect the updated status
            updateSeatList();

        } catch (Exception e) {
            MessageDialog.showMessageDialog(new MultiWindowTextGUI(screen), "Reservation Failed", "This seat is already reserved or an error occurred.");
            e.printStackTrace();
        }
    }
}
