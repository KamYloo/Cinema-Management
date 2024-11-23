import {
    GET_SHOW_TIME_ERROR,
    GET_SHOW_TIME_REQUEST,
    GET_SHOW_TIMES_BY_MOVIE_ID_ERROR,
    GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST
} from "./ActionType.js";
import {apiRequest} from "../api.js";

export const getShowTimes = (movieId) => async (dispatch) => {
    await apiRequest({
        url: `/api/showTimes/movie/${movieId}`,
        method: 'GET',
        dispatch,
        successType: GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST,
        errorType: GET_SHOW_TIMES_BY_MOVIE_ID_ERROR,
    });
};


export const getShowTime = (showTimeId) => async (dispatch) => {
    await apiRequest({
        url: `/api/showTimes/${showTimeId}`,
        method: 'GET',
        dispatch,
        successType: GET_SHOW_TIME_REQUEST,
        errorType: GET_SHOW_TIME_ERROR,
    });
}