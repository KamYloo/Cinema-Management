import { applyMiddleware, combineReducers, legacy_createStore } from "redux"
import { thunk } from "redux-thunk"
import {authReducer} from "./AuthService/Reducer.js";
import {movieReducer} from "./MovieService/Reducer.js";
import {showTimeReducer} from "./ShowTimeService/Reducer.js";
import {seatReducer} from "./SeatService/Reducer.js";
import {reservationReducer} from "./ReservationService/Reducer.js";



const rootReducer = combineReducers({
    auth: authReducer,
    movie: movieReducer,
    showTime: showTimeReducer,
    seat: seatReducer,
    reservation: reservationReducer,
})


export const store = legacy_createStore(rootReducer, applyMiddleware(thunk))