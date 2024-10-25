import {
    DELETE_RESERVATION_REQUEST,
    GET_USER_RESERVATIONS_REQUEST,
    MAKE_RESERVATION_REQUEST,
} from "./ActionType.js";

const initialValue= {
    makeReservation: null,
    reservations: [],
    deletedReservation: null,
}

export const reservationReducer=(store=initialValue, {type,payload})=>{
    if (type === MAKE_RESERVATION_REQUEST) {
        return {...store, makeReservation: payload}
    }
    else if (type === GET_USER_RESERVATIONS_REQUEST) {
        return {...store, reservations: payload}
    }
    else if (type === DELETE_RESERVATION_REQUEST) {
        return {...store, deletedReservation: payload}
    }
    return store
}