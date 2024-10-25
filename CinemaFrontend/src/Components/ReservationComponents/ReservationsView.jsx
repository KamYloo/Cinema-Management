import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/reservations.css";
import {useDispatch, useSelector} from "react-redux";
import {deleteReservation, getReservationsByUser} from "../../Redux/Reservation/Action.js";

function ReservationsView() {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {userId} = useParams();

    const {reservation} = useSelector(store => store);

    const deleteReservationHandler = (reservationId) => {
        const confirmDelete = window.confirm('Are you sure you want to delete this Reservation?');
        if (confirmDelete) {
            dispatch(deleteReservation(reservationId));
        }
    };

    useEffect(() => {
        dispatch(getReservationsByUser(userId))
    }, [dispatch, userId, reservation.makeReservation, reservation.deletedReservation]);

    return (
        <div className="reservationsView">
            <h2>Moje Rezerwacje</h2>
            <table>
                <thead>
                <tr>
                    <th>Tytuł Filmu</th>
                    <th>Data i Godzina</th>
                    <th>Rząd</th>
                    <th>Miejsce</th>
                    <th>Akcje</th>
                </tr>
                </thead>
                <tbody>
                {reservation.reservations.map((reservation) => (
                    <tr key={reservation.id}>
                        <td>{reservation?.showtime.movie.title}</td>
                        <td>{reservation?.showtime.time}</td>
                        <td>{reservation?.seat.rowNumber}</td>
                        <td>{reservation?.seat.seatNumber}</td>
                        <td>
                            <button
                                onClick={() => deleteReservationHandler(reservation.id)}
                                className="cancel-button"
                            >
                                Anuluj
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export {ReservationsView};
