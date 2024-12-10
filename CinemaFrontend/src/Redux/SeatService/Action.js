import {apiRequest} from "../api.js";
import {GET_SEATS_LIST_ERROR, GET_SEATS_LIST_REQUEST} from "./ActionType.js";


export const getSeatsAction = (showTimeId) => async (dispatch) => {
    await apiRequest({
        url: `/api/seats/showtime/${showTimeId}`,
        method: 'GET',
        dispatch,
        successType: GET_SEATS_LIST_REQUEST,
        errorType: GET_SEATS_LIST_ERROR,
    });
};