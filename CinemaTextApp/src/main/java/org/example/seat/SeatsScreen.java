package org.example.seat;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import org.example.dto.SeatDto;

import java.util.List;

public class SeatsScreen {

    private Screen screen;

    public SeatsScreen(Screen screen) {
        this.screen = screen;
    }

    public void showSeatSelection(Integer showTimeId) {
        try {
            List<SeatDto> seats = SeatService.getSeatsByShowTime(showTimeId);

            BasicWindow seatWindow = new BasicWindow("Seat Selection");

            Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            mainPanel.addComponent(new EmptySpace());

            Label titleLabel = new Label("Available Seats").addStyle(SGR.BOLD);
            titleLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            mainPanel.addComponent(titleLabel);

            Panel seatPanel = new Panel(new GridLayout(2).setHorizontalSpacing(3).setVerticalSpacing(1));
            seatPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            ActionListBox seatListBox = new ActionListBox();

            for (SeatDto seat : seats) {
                String seatLabel = "Row: " + seat.getRowNumber() + ", Seat: " + seat.getSeatNumber() +
                        (seat.isReserved() ? " [Reserved]" : " [Available]");

                if (!seat.isReserved()) {
                    seatListBox.addItem(seatLabel, () -> {
                        /* reserveSeat(seat.getId()); */
                    });
                }
            }

            seatPanel.addComponent(seatListBox);
            mainPanel.addComponent(seatPanel);

            Button backButton = new Button("Back", seatWindow::close);
            backButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            mainPanel.addComponent(backButton);

            mainPanel.addComponent(new EmptySpace());

            seatWindow.setComponent(mainPanel);

            seatWindow.setHints(java.util.Arrays.asList(Window.Hint.FULL_SCREEN));

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.addWindowAndWait(seatWindow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private void reserveSeat(Integer seatId) {
        // Call service to reserve the seat and notify the user
        boolean success = SeatService.reserveSeat(seatId);
        if (success) {
            MessageDialog.showMessageDialog(new MultiWindowTextGUI(screen), "Reservation", "Seat reserved successfully!");
        } else {
            MessageDialog.showMessageDialog(new MultiWindowTextGUI(screen), "Reservation Failed", "This seat is already reserved.");
        }
    }*/
}
