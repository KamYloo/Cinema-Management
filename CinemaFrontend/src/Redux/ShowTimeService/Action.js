import {
    GET_SHOW_TIME_ERROR,
    GET_SHOW_TIME_REQUEST,
    GET_SHOW_TIMES_BY_MOVIE_ID_ERROR,
    GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST
} from "./ActionType.js";
import {dispatchAction} from "../api.js";

export const getShowTimesAction = (movieId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST, GET_SHOW_TIMES_BY_MOVIE_ID_ERROR, `/api/showTimes/movie/${movieId}`, {
        method: 'GET',
    });
};

export const getShowTimeAction = (showTimeId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_SHOW_TIME_REQUEST, GET_SHOW_TIME_ERROR, `/api/showTimes/${showTimeId}`, {
        method: 'GET',
    });
}