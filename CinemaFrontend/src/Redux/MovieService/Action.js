import {
    ADD_MOVIE_ERROR,
    ADD_MOVIE_REQUEST,
    DELETE_MOVIE_ERROR,
    DELETE_MOVIE_REQUEST,
    GET_ALL_MOVIES_ERROR,
    GET_ALL_MOVIES_REQUEST,
    GET_MOVIE_ERROR,
    GET_MOVIE_REQUEST,
    UPLOAD_PHOTO_ERROR,
    UPLOAD_PHOTO_REQUEST
} from "./ActionType.js";
import {dispatchAction} from "../api.js";

export const createMovieAction = (data) => async (dispatch) => {
    await dispatchAction(dispatch, ADD_MOVIE_REQUEST, ADD_MOVIE_ERROR, '/api/movies/create',
        {
            method: 'POST',
            body: JSON.stringify(data),
        }
    );
}

export const getAllMoviesAction = () => async (dispatch) => {
    await dispatchAction(dispatch, GET_ALL_MOVIES_REQUEST, GET_ALL_MOVIES_ERROR, '/api/movies/getAll',
        {
            method: 'GET',
        });
};

export const getMovieAction = (movieId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_MOVIE_REQUEST, GET_MOVIE_ERROR, `/api/movies/${movieId}`,
        {
            method: 'GET',
        });
}

export const uploadMoviePhotoAction = (formData) => async (dispatch) => {
    return await dispatchAction(dispatch, UPLOAD_PHOTO_REQUEST, UPLOAD_PHOTO_ERROR, '/api/movies/uploadImage',
        {
            method: 'POST',
            body: formData,
        });
}

export const deleteMovieAction = (movieId) => async (dispatch) => {
    await dispatchAction(dispatch, DELETE_MOVIE_REQUEST, DELETE_MOVIE_ERROR, `/api/movies/delete/${movieId}`,
        {
            method: 'DELETE',
        });
}