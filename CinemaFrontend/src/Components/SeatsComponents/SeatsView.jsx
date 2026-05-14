import React, {useEffect, useState} from "react";
import {useParams} from 'react-router-dom'
import "../../styles/seats.css";
import { PiArmchairBold } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {getShowTimeAction} from "../../Redux/ShowTimeService/Action.js";
import {getSeatsAction} from "../../Redux/SeatService/Action.js";
import {makeReservationAction} from "../../Redux/ReservationService/Action.js";
import RomanNumerals from 'roman-numerals';
import {convertShowtime} from "../../utils/formatDate.js";
import toast from "react-hot-toast";
import {Client} from "@stomp/stompjs";
import {BASE_WS_URL} from "../../Redux/api.js";

function SeatsView() {

    const {showTimeId} = useParams();
    const dispatch = useDispatch();
    const showTime = useSelector(store => store.showTime);
    const seat = useSelector(store => store.seat);
    const reservation = useSelector(store => store.reservation);
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

    const makeReservationHandler = async (e) => {
        e.preventDefault();
        if (selectedSeat) {
            try {
                await dispatch(makeReservationAction({seatId:selectedSeat.id}))
                setSelectedSeat(null);
            } catch (error) {
                toast.error(error.message || "Nie udało się zarezerwować miejsca.");
            }
        }
    }

    useEffect(() => {
        if (!showTimeId) {
            return;
        }

        const client = new Client({
            brokerURL: BASE_WS_URL,
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        client.onConnect = () => {
            client.subscribe(`/topic/showtimes/${showTimeId}/seats`, (message) => {
                try {
                    const update = JSON.parse(message.body);

                    dispatch(getSeatsAction(showTimeId));

                    const seatLabel = `${handleConvertNumber(update.rowNumber)}-${update.seatNumber}`;
                    if (update.action === 'RESERVED') {
                        toast(`Miejsce ${seatLabel} zostało właśnie zajęte.`);
                    } else if (update.action === 'RELEASED') {
                        toast(`Miejsce ${seatLabel} jest ponownie dostępne.`);
                    } else {
                        toast(`Zaktualizowano dostępność miejsca ${seatLabel}.`);
                    }
                } catch (error) {
                    console.error("Invalid websocket message", error);
                }
            });
        };

        client.onStompError = (frame) => {
            console.error("WebSocket STOMP error", frame.headers["message"], frame.body);
        };

        client.activate();

        return () => {
            client.deactivate();
        };
    }, [dispatch, showTimeId]);

    useEffect(() => {
        dispatch(getShowTimeAction(showTimeId))
        dispatch(getSeatsAction(showTimeId))
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
