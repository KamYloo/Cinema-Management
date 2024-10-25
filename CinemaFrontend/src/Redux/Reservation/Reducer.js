import {
    MAKE_RESERVATION_REQUEST,
} from "./ActionType.js";

const initialValue= {
    makeReservation: null,
}

export const reservationReducer=(store=initialValue, {type,payload})=>{
    if (type === MAKE_RESERVATION_REQUEST) {
        return {...store, makeReservation: payload}
    }
    return store
}