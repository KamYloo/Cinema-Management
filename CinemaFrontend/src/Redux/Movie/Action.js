import {
    ADD_MOVIE_ERROR,
    ADD_MOVIE_REQUEST,
    GET_ALL_MOVIES_ERROR,
    GET_ALL_MOVIES_REQUEST, GET_MOVIE_ERROR,
    GET_MOVIE_REQUEST
} from "./ActionType.js";
import {BASE_API_URL} from "../../config/api.js";

export const createMovie = (data) => async (dispatch) => {

    try {
        const response = await fetch(`${BASE_API_URL}/api/movies/create`,  {
            method: 'POST',
            headers : {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(data)
        })

        const movie = await response.json();
        console.log("created movie", movie);
        dispatch({ type: ADD_MOVIE_REQUEST, payload: movie });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: ADD_MOVIE_ERROR, payload: error.message });
    }
}


export const getAllMovies = () => async (dispatch) => {
    try {
        const response = await fetch(`${BASE_API_URL}/api/movies/getAll`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });

        const movies = await response.json();
        console.log("getAllMovies", movies);
        dispatch({ type: GET_ALL_MOVIES_REQUEST, payload: movies });
    } catch (error) {
        console.log('catch error', error);
        dispatch({ type: GET_ALL_MOVIES_ERROR, payload: error.message });
    }
};

export const getMovie = (movieId) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/api/movies/${movieId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
        })

        const resData = await res.json()
        console.log("Get Movie ", resData)
        dispatch({ type: GET_MOVIE_REQUEST, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
        dispatch({ type: GET_MOVIE_ERROR, payload: error.message });
    }
}