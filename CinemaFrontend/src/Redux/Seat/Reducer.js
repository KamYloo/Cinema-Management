import {
    GET_SEATS_LIST_REQUEST,
} from "./ActionType.js";

const initialValue= {
    seats: [],
}

export const seatReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_SEATS_LIST_REQUEST) {
        return {...store, seats: payload}
    }

    return store
}