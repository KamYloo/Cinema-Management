import {
    GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST
} from "./ActionType.js";

const initialValue= {
    showTimes: [],
}

export const showTimeReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST) {
        return {...store, showTimes: payload}
    }
    return store
}