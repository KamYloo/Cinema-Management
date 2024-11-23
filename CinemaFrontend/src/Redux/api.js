export const BASE_API_URL = "http://localhost:8080"


export const apiRequest = async ({ url, method, data, dispatch, successType, errorType }) => {
    try {
        const response = await fetch(`${BASE_API_URL}${url}`, {
            method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
            body: data ? JSON.stringify(data) : undefined,
        });

        const result = await response.json();
        dispatch({ type: successType, payload: result });
    } catch (error) {
        dispatch({ type: errorType, payload: error.message });
    }
};