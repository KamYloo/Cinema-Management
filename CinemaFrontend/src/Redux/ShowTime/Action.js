import {GET_SHOW_TIMES_BY_MOVIE_ID_ERROR, GET_SHOW_TIMES_BY_MOVIE_ID_REQUEST} from "./ActionType.js";
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