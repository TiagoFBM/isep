import config from "../config";

export async function login(json) {
    const res = await fetch(config.authApiURL + "/authentication/login", {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            "Content-Type": "application/json",
        } });
    let data = {};
    let token = null;
    if (res.status === 200) {
        const data = await res.json();
        token = data.token;
        //data = await res.json().token;
    }
    return token;
}

export async function register(json) {
    const res = await fetch(config.authApiURL + "/authentication/register", {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            "Content-Type": "application/json",
        } });
    let data = {};
    if (res.status === 200) {
        return true;
    }
    return false;
}

export async function getUserFromToken(token) {
    const res = await fetch(config.authApiURL + "/authentication/user", {
        headers: {
            "Content-Type": "application/json",
            "Authorization": token
        }
    });
    let data = {};
    if (res.status === 200) {
        data = await res.json();
    }
    return data;
}