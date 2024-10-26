import {
    ADD_MOVIE_ERROR,
    ADD_MOVIE_REQUEST,
    GET_ALL_MOVIES_ERROR,
    GET_ALL_MOVIES_REQUEST, GET_MOVIE_ERROR,
    GET_MOVIE_REQUEST
} from "./ActionType.js";
import {apiRequest} from "../../config/api.js";

export const createMovie = (data) => async (dispatch) => {

    await apiRequest({
        url: '/api/movies/create',
        method: 'POST',
        data,
        dispatch,
        successType: ADD_MOVIE_REQUEST,
        errorType: ADD_MOVIE_ERROR,
    });
}


export const getAllMovies = () => async (dispatch) => {
    await apiRequest({
        url: '/api/movies/getAll',
        method: 'GET',
        dispatch,
        successType: GET_ALL_MOVIES_REQUEST,
        errorType: GET_ALL_MOVIES_ERROR,
    });
};

export const getMovie = (movieId) => async (dispatch) => {
    await apiRequest({
        url: `/api/movies/${movieId}`,
        method: 'GET',
        dispatch,
        successType: GET_MOVIE_REQUEST,
        errorType: GET_MOVIE_ERROR,
    });
}