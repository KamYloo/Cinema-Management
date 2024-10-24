import {ADD_MOVIE_REQUEST, DELETE_MOVIE_REQUEST, GET_ALL_MOVIES_REQUEST, GET_MOVIE_REQUEST} from "./ActionType.js";

const initialValue= {
    createdMovie:null,
    movies: [],
    deletedMovie : null,
    getMovie:null,
}

export const movieReducer=(store=initialValue, {type,payload})=>{
    if (type === ADD_MOVIE_REQUEST) {
        return {...store, createdMovie: payload}
    }
    else if (type === GET_ALL_MOVIES_REQUEST) {
        return {...store, movies: payload}
    }
    else if (type === GET_MOVIE_REQUEST) {
        return {...store, getMovie: payload}
    }
    else if(type === DELETE_MOVIE_REQUEST) {
        return {...store, deletedMovie: payload}
    }

    return store
}