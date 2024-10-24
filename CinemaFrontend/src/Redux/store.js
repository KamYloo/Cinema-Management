import { applyMiddleware, combineReducers, legacy_createStore } from "redux"
import { thunk } from "redux-thunk"
import {authReducer} from "./Auth/Reducer.js";
import {movieReducer} from "./Movie/Reducer.js";
import {showTimeReducer} from "./ShowTime/Reducer.js";


const rootReducer = combineReducers({
    auth: authReducer,
    movie: movieReducer,
    showTime: showTimeReducer,
})


export const store = legacy_createStore(rootReducer, applyMiddleware(thunk))