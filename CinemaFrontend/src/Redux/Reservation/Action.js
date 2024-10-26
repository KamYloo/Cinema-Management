import {apiRequest} from "../../config/api.js";
import {
    DELETE_RESERVATION_ERROR,
    DELETE_RESERVATION_REQUEST,
    GET_USER_RESERVATIONS_ERROR,
    GET_USER_RESERVATIONS_REQUEST,
    MAKE_RESERVATION_ERROR,
    MAKE_RESERVATION_REQUEST
} from "./ActionType.js";

export const makeReservation = (data) => async (dispatch) => {

    await apiRequest({
        url: `/api/reservations/create`,
        method: 'POST',
        data,
        dispatch,
        successType: MAKE_RESERVATION_REQUEST,
        errorType: MAKE_RESERVATION_ERROR,
    });
}

export const getReservationsByUser = (userId) => async (dispatch) => {
    await apiRequest({
        url: `/api/reservations/user/${userId}`,
        method: 'GET',
        dispatch,
        successType: GET_USER_RESERVATIONS_REQUEST,
        errorType: GET_USER_RESERVATIONS_ERROR,
    });
};

export const deleteReservation = (reservationId) => async (dispatch) => {
    await apiRequest({
        url: `/api/reservations/delete/${reservationId}`,
        method: 'DELETE',
        dispatch,
        successType: DELETE_RESERVATION_REQUEST,
        errorType: DELETE_RESERVATION_ERROR,
    });
};