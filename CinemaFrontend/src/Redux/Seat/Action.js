import {BASE_API_URL} from "../../config/api.js";
import {GET_SEATS_LIST_ERROR, GET_SEATS_LIST_REQUEST} from "./ActionType.js";

export const getSeats = (showTimeId) => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/seats/showtime/${showTimeId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });

        const seats = await response.json();
        console.log("getSeats", seats);
        dispatch({ type: GET_SEATS_LIST_REQUEST, payload: seats });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: GET_SEATS_LIST_ERROR, payload: error.message });
    }
};