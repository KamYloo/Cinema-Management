import {BASE_API_URL} from "../../config/api.js";
import {
    DELETE_RESERVATION_ERROR,
    DELETE_RESERVATION_REQUEST,
    GET_USER_RESERVATIONS_ERROR,
    GET_USER_RESERVATIONS_REQUEST,
    MAKE_RESERVATION_ERROR,
    MAKE_RESERVATION_REQUEST
} from "./ActionType.js";

export const makeReservation = (data) => async (dispatch) => {

    try {
        const response = await fetch(`${BASE_API_URL}/api/reservations/create`,  {
            method: 'POST',
            headers : {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(data)
        })

        const reservation = await response.json();
        console.log("created reservation", reservation);
        dispatch({ type: MAKE_RESERVATION_REQUEST, payload: reservation });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: MAKE_RESERVATION_ERROR, payload: error.message });
    }
}

export const getReservationsByUser = (userId) => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/reservations/user/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });

        const reservations = await response.json();
        console.log("getReservations", reservations);
        dispatch({ type: GET_USER_RESERVATIONS_REQUEST, payload: reservations });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: GET_USER_RESERVATIONS_ERROR, payload: error.message });
    }
};

export const deleteReservation = (reservationId) => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/reservations/delete/${reservationId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
        });

        const res = await response.json();
        console.log("Deleted Reservation", res)
        dispatch({ type: DELETE_RESERVATION_REQUEST, payload: res });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: DELETE_RESERVATION_ERROR, payload: error.message });
    }
};