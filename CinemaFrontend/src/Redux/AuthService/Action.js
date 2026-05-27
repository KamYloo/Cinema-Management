import { BASE_API_URL } from "../api.js"
import {
    REGISTER,
    LOGIN,
    REQUEST_USER,
    LOGOUT,
} from "./ActionType"

const parseJsonSafe = async (res) => {
    try {
        return await res.json()
    } catch {
        return null
    }
}

export const registerAction = (data) => async (dispatch) => {
    const res = await fetch(`${BASE_API_URL}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data)
    })

    const resData = await parseJsonSafe(res)
    if (!res.ok) {
        throw new Error(resData?.message || "Registration failed")
    }

    dispatch({ type: REGISTER, payload: resData })
    return resData
}


export const loginAction = (data) => async (dispatch) => {
    const res = await fetch(`${BASE_API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data)
    })

    const resData = await parseJsonSafe(res)
    if (!res.ok || !resData?.jwt) {
        localStorage.removeItem("token")
        throw new Error(resData?.message || "Invalid email or password")
    }

    localStorage.setItem("token", resData.jwt)
    dispatch({ type: LOGIN, payload: resData })
    return resData
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

        const resData = await parseJsonSafe(res)
        if (!res.ok) {
            localStorage.removeItem("token")
            dispatch({ type: REQUEST_USER, payload: null })
            return null
        }

        dispatch({ type: REQUEST_USER, payload: resData })
        return resData
    } catch (error) {
        console.log("catch error ", error)
        localStorage.removeItem("token")
        dispatch({ type: REQUEST_USER, payload: null })
        return null
    }
}

export const logoutAction = () => async (dispatch) => {
    localStorage.removeItem("token")
    dispatch({ type: LOGOUT, payload: null})
    dispatch({ type: REQUEST_USER, payload: null})
}