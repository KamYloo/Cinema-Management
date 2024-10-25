import {
    GET_SHOW_TIME_REQUEST,
    GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST
} from "./ActionType.js";

const initialValue= {
    showTimes: [],
    getShowTime: null,
}

export const showTimeReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST) {
        return {...store, showTimes: payload}
    }
    else if (type === GET_SHOW_TIME_REQUEST) {
        return {...store, getShowTime: payload}
    }
    return store
}