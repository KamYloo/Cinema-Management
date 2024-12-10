import { BASE_API_URL } from "../api.js"
import {
    REGISTER,
    LOGIN,
    REQUEST_USER,
    LOGOUT,
} from "./ActionType"

export const registerAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })

        const resData = await res.json()
        dispatch({ type: REGISTER, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}


export const loginAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })

        const resData = await res.json()
        if (resData.jwt)
            localStorage.setItem("token", resData.jwt)
        dispatch({ type: LOGIN, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}

export const currentUserAction = () => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/api/users/profile`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        })

        const resData = await res.json()
        dispatch({ type: REQUEST_USER, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}

export const logoutAction = () => async (dispatch) => {
    localStorage.removeItem("token")
    dispatch({ type: LOGOUT, payload: null})
    dispatch({ type: REQUEST_USER, payload: null})
}