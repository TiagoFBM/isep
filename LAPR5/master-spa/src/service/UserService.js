import config from "../config";

export async function getUsers({ skip, limit }) {
    const res = await fetch(config.authApiURL + `/user?skip=${skip}&limit=${limit}`, {
        headers: {
            "Content-Type": "application/json",
        }
    });
    let data = [];
    if (res.status === 200) {
        data = await res.json();
    }
    return data;
}

export async function updateUser(json) {
    const res = await fetch(config.authApiURL + "/user", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }, 
        body: JSON.stringify(json)
    });

    return res.status === 200;
}

export async function getUserCount() {
    const res = await fetch(config.authApiURL + "/user/count");
    let num = 0;
    if (res.ok) {
        const json = await res.json();
        num = json.numberOfElems;
    }
    return num;
}

export async function removeUser(email) {
    const res = await fetch(config.authApiURL + "/user/" + email, {
        method: "DELETE"
    });
    return res.status === 200;
}