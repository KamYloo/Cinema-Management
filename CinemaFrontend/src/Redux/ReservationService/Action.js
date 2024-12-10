import {dispatchAction} from "../api.js";
import {
    DELETE_RESERVATION_ERROR,
    DELETE_RESERVATION_REQUEST,
    GET_USER_RESERVATIONS_ERROR,
    GET_USER_RESERVATIONS_REQUEST,
    MAKE_RESERVATION_ERROR,
    MAKE_RESERVATION_REQUEST
} from "./ActionType.js";

export const makeReservationAction = (data) => async (dispatch) => {
    await dispatchAction(dispatch, MAKE_RESERVATION_REQUEST, MAKE_RESERVATION_ERROR, '/api/reservations/create', {
        method: 'POST',
        body: JSON.stringify(data),
    });
}

export const getReservationsByUserAction = (userId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_USER_RESERVATIONS_REQUEST, GET_USER_RESERVATIONS_ERROR, `/api/reservations/user/${userId}`, {
        method: 'GET',
    });
};

export const deleteReservationAction = (reservationId) => async (dispatch) => {
    await dispatchAction(dispatch, DELETE_RESERVATION_REQUEST, DELETE_RESERVATION_ERROR, `/api/reservations/delete/${reservationId}`, {
        method: 'DELETE',
    });

};