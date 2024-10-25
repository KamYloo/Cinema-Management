import { applyMiddleware, combineReducers, legacy_createStore } from "redux"
import { thunk } from "redux-thunk"
import {authReducer} from "./Auth/Reducer.js";
import {movieReducer} from "./Movie/Reducer.js";
import {showTimeReducer} from "./ShowTime/Reducer.js";
import {seatReducer} from "./Seat/Reducer.js";
import {reservationReducer} from "./Reservation/Reducer.js";



const rootReducer = combineReducers({
    auth: authReducer,
    movie: movieReducer,
    showTime: showTimeReducer,
    seat: seatReducer,
    reservation: reservationReducer,
})


export const store = legacy_createStore(rootReducer, applyMiddleware(thunk))