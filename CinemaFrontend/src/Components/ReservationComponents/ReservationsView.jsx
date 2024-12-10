import React, {useEffect} from "react";
import {useParams} from 'react-router-dom'
import "../../styles/reservations.css";
import {useDispatch, useSelector} from "react-redux";
import {deleteReservationAction, getReservationsByUserAction} from "../../Redux/ReservationService/Action.js";
import {convertShowtime} from "../../utils/formatDate.js";

function ReservationsView() {

    const dispatch = useDispatch();
    const {userId} = useParams();

    const {reservation} = useSelector(store => store);

    const deleteReservationHandler = (reservationId) => {
        const confirmDelete = window.confirm('Are you sure you want to delete this ReservationService?');
        if (confirmDelete) {
            dispatch(deleteReservationAction(reservationId));
        }
    };

    useEffect(() => {
        dispatch(getReservationsByUserAction(userId))
    }, [dispatch, userId, reservation.makeReservation, reservation.deletedReservation]);

    return (
        <div className="reservationsView">
            <h2>My Reservations</h2>
            <table>
                <thead>
                <tr>
                    <th>Movie Title</th>
                    <th>Date & Time</th>
                    <th>Row</th>
                    <th>Seat</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {reservation.reservations.map((reservation) => (
                    <tr key={reservation.id}>
                        <td>{reservation?.showtime.movie.title}</td>
                        <td>{convertShowtime(reservation?.showtime.time).date} · {convertShowtime(reservation?.showtime.time).time}</td>
                        <td>{reservation?.seat.rowNumber}</td>
                        <td>{reservation?.seat.seatNumber}</td>
                        <td>
                            <button
                                onClick={() => deleteReservationHandler(reservation.id)}
                                className="cancel-button"
                            >
                                Cancel
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
