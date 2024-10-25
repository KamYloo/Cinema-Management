import {BASE_API_URL} from "../../config/api.js";
import {MAKE_RESERVATION_ERROR, MAKE_RESERVATION_REQUEST} from "./ActionType.js";

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