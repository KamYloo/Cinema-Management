import {
    GET_SHOW_TIME_ERROR,
    GET_SHOW_TIME_REQUEST,
    GET_SHOW_TIMES_BY_MOVIE_ID_ERROR,
    GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST
} from "./ActionType.js";
import {BASE_API_URL} from "../../config/api.js";

export const getShowTimes = (movieId) => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/showTimes/movie/${movieId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });

        const showTimes = await response.json();
        console.log("getShowTimes", showTimes);
        dispatch({ type: GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST, payload: showTimes });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: GET_SHOW_TIMES_BY_MOVIE_ID_ERROR, payload: error.message });
    }
};


export const getShowTime = (showTimeId) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/api/showTimes/${showTimeId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
        })

        const resData = await res.json()
        console.log("Get ShowTime ", resData)
        dispatch({ type: GET_SHOW_TIME_REQUEST, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
        dispatch({ type: GET_SHOW_TIME_ERROR, payload: error.message });
    }
}