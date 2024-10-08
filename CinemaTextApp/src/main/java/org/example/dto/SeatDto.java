package org.example.dto;

public class SeatDto {
    private Integer id;
    private int rowNumber;
    private int seatNumber;
    private boolean reserved;
    private ShowTimeDto showTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public ShowTimeDto getShowTime() {
        return showTime;
    }

    public void setShowTime(ShowTimeDto showTime) {
        this.showTime = showTime;
    }
}
