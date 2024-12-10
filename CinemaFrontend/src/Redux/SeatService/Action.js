import {dispatchAction} from "../api.js";
import {GET_SEATS_LIST_ERROR, GET_SEATS_LIST_REQUEST} from "./ActionType.js";


export const getSeatsAction = (showTimeId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_SEATS_LIST_REQUEST, GET_SEATS_LIST_ERROR, `/api/seats/showtime/${showTimeId}`, {
        method: 'GET',
    });
};