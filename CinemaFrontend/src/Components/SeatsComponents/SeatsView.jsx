import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/seats.css";
import { PiArmchairBold } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {createMovie, getMovie} from "../../Redux/Movie/Action.js";
import {getShowTime} from "../../Redux/ShowTime/Action.js";
import {getSeats} from "../../Redux/Seat/Action.js";
import {makeReservation} from "../../Redux/Reservation/Action.js";

function SeatsView() {

    const {showTimeId} = useParams();
    const dispatch = useDispatch();
    const {showTime, seat, reservation} = useSelector(store => store);
    const [selectedSeat, setSelectedSeat] = useState(null);
    const navigate = useNavigate();

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

    const convertShowtime = (dateTimeString) => {
        const dateTime = new Date(dateTimeString);
        const options = { month: 'short', day: '2-digit', year: 'numeric' };
        const formattedDate = dateTime.toLocaleDateString('en-US', options);
        const formattedTime = dateTime.toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        });

        return {
            date: formattedDate,
            time: formattedTime
        };
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
                                    <p>{rowKey}</p>
                                    {rows[rowKey].map((seat) => (
                                        <div
                                            key={`seat-${seat?.seatNumber}`}
                                            className={`seat ${
                                                seat?.reserved ? 'occupied' :
                                                    selectedSeat === seat ? 'selected' : ''
                                            }`}
                                            onClick={() => handleSeatClick(seat)}
                                        >
                                            <i><PiArmchairBold/></i>
                                            {/*<span>{seat.seatNumber}</span>*/}
                                        </div>
                                    ))}
                                    <p>{rowKey}</p>
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
