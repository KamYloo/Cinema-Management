import {
    ADD_MOVIE_ERROR,
    ADD_MOVIE_REQUEST, DELETE_MOVIE_ERROR, DELETE_MOVIE_REQUEST,
    GET_ALL_MOVIES_ERROR,
    GET_ALL_MOVIES_REQUEST, GET_MOVIE_ERROR,
    GET_MOVIE_REQUEST, UPLOAD_PHOTO_ERROR, UPLOAD_PHOTO_REQUEST
} from "./ActionType.js";
import {apiRequest, BASE_API_URL} from "../api.js";

export const createMovieAction = (data) => async (dispatch) => {

    await apiRequest({
        url: '/api/movies/create',
        method: 'POST',
        data,
        dispatch,
        successType: ADD_MOVIE_REQUEST,
        errorType: ADD_MOVIE_ERROR,
    });
}


export const getAllMoviesAction = () => async (dispatch) => {
    await apiRequest({
        url: '/api/movies/getAll',
        method: 'GET',
        dispatch,
        successType: GET_ALL_MOVIES_REQUEST,
        errorType: GET_ALL_MOVIES_ERROR,
    });
};

export const getMovieAction = (movieId) => async (dispatch) => {
    await apiRequest({
        url: `/api/movies/${movieId}`,
        method: 'GET',
        dispatch,
        successType: GET_MOVIE_REQUEST,
        errorType: GET_MOVIE_ERROR,
    });
}

export const uploadMoviePhotoAction = (formData) => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/movies/uploadImage`,  {
            method: 'POST',
            headers : {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            body: formData
        })
        const album = await response.text()
        dispatch({type: UPLOAD_PHOTO_REQUEST, payload: album})
        return album
    }catch(err) {
        console.log("catch error " + err)
        dispatch({ type: UPLOAD_PHOTO_ERROR, payload: err.message });
    }
}

export const deleteMovieAction = (movieId) => async (dispatch) => {
    await apiRequest({
        url: `/api/movies/delete/${movieId}`,
        method: 'DELETE',
        dispatch,
        successType: DELETE_MOVIE_REQUEST,
        errorType: DELETE_MOVIE_ERROR,
    });
}