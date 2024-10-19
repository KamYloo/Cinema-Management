import { BASE_API_URL } from "../../config/api"
import {
    REGISTER,
    LOGIN,
    REQUEST_USER,
    UPDATE_USER,
    LOGOUT,
} from "./ActionType"

export const register = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })

        const resData = await res.json()
        if (resData.jwt)localStorage.setItem("token", resData.jwt)
        console.log("register ", resData)
        dispatch({ type: REGISTER, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}


export const login = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })

        const resData = await res.json()
        console.log("login ", resData)
        if (resData.jwt)localStorage.setItem("token", resData.jwt)
        dispatch({ type: LOGIN, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}

export const currentUser = () => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/api/users/profile`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        })

        const resData = await res.json()
        console.log("usersProfile ", resData)
        dispatch({ type: REQUEST_USER, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}

export const updateUser = (data) => async (dispatch) => {
    try {
        const formData = new FormData();
        formData.append("fullName", data.data.fullName);
        if (data.data.profilePicture) {
            formData.append("profilePicture", data.data.profilePicture);
        }else {

            formData.append("profilePicture", null);
        }
        formData.append("description", data.data.description);

        console.log(data.data.description)

        const res = await fetch(`${BASE_API_URL}/api/users/update`, {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            body: formData
        })

        const resData = await res.json()
        console.log("updateUser ", resData)
        dispatch({ type: UPDATE_USER, payload: resData })
    } catch (error) {
        console.log("catch error ", error)
    }
}

export const logoutAction = () => async (dispatch) => {
    localStorage.removeItem("token")
    dispatch({ type: LOGOUT, payload: null})
    dispatch({ type: REQUEST_USER, payload: null})
}