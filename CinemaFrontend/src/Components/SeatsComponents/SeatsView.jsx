import React, {useEffect, useState} from "react";
import {useParams} from 'react-router-dom'
import "../../styles/seats.css";
import { PiArmchairBold } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {getShowTime} from "../../Redux/ShowTime/Action.js";
import {getSeats} from "../../Redux/Seat/Action.js";
import {makeReservation} from "../../Redux/Reservation/Action.js";
import RomanNumerals from 'roman-numerals';
import {convertShowtime} from "../../utils/formatDate.js";

function SeatsView() {

    const {showTimeId} = useParams();
    const dispatch = useDispatch();
    const {showTime, seat, reservation} = useSelector(store => store);
    const [selectedSeat, setSelectedSeat] = useState(null);

    const rows = seat.seats.reduce((acc, seat) => {
        acc[seat.rowNumber] = acc[seat.rowNumber] || [];
        acc[seat.rowNumber].push(seat);
        return acc;
    }, {});

    const handleSeatClick = (seat) => {
        if (!seat.reserved) {
            setSelectedSeat(selectedSeat === seat ? null : seat);
        }
    };

    const handleConvertNumber = (num) => {
        if (!isNaN(num) && num > 0) {
            return (RomanNumerals.toRoman(num));
        }
    };

    const makeReservationHandler = (e) => {
        e.preventDefault();
        if (selectedSeat) {
            dispatch(makeReservation({seatId:selectedSeat.id}))
            setSelectedSeat(null);
        }
    }

    useEffect(() => {
        dispatch(getShowTime(showTimeId))
        dispatch(getSeats(showTimeId))
    }, [dispatch, showTimeId, reservation.makeReservation]);


    return (
        <div className="seatsView">
            <div className="center">
                <div className="up">
                    <div className="top">
                        <p>Watch and Enjoy</p>
                        <p>{convertShowtime(showTime.getShowTime?.time).date} · {convertShowtime(showTime.getShowTime?.time).time}</p>
                    </div>
                    <hr/>
                    <div className="bottom">
                        <h4>{showTime.getShowTime?.movie.title} /</h4>
                        <span>{showTime.getShowTime?.movie.genre}</span>
                    </div>
                </div>
                <div className="down">
                    <div className="seatsBox">
                        <div className="seats">
                            <hr className="screen"/>
                            <p className="screenP">Screen</p>

                            {Object.keys(rows).map((rowKey) => (
                                <div className="row" key={`row-${rowKey}`}>
                                    <p>{handleConvertNumber(rowKey)}</p>
                                    {rows[rowKey].map((seat) => (
                                        <div
                                            key={`seat-${seat?.seatNumber}`}
                                            className={`seat ${
                                                seat?.reserved ? 'occupied' :
                                                    selectedSeat === seat ? 'selected' : ''
                                            }`}
                                            onClick={() => handleSeatClick(seat)}
                                        >
                                            <i><PiArmchairBold/><span>{seat.seatNumber}</span></i>

                                        </div>
                                    ))}
                                    <p>{handleConvertNumber(rowKey)}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                    <hr/>
                    <div className="legend">
                        <h6>Legend</h6>
                        <div className="signs">
                            <div className="colors">
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Free</span>
                                </div>
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Busy</span>
                                </div>
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Pick</span>
                                </div>
                            </div>
                            <div className="icon">
                                <i><PiArmchairBold/></i>
                                <span>Armchair</span>
                            </div>
                        </div>
                    </div>
                </div>
                <button className="reservationBtn" onClick={makeReservationHandler}>Reserve</button>
            </div>
        </div>
    );
}

export {SeatsView};
