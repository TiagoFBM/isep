import config from "../config";

export async function getDriverTypeByCode(code) {
    const res = await fetch(config.apiURL + `/driverType/${code}`);
    let data = {};
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getDriverTypes({ skip, limit }) {
    let data = [];
    const res = await fetch(config.apiURL + `/driverType?skip=${skip}&limit=${limit}`);
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getDriverTypeCount() {
    const res = await fetch(config.apiURL + "/driverType/count");
    let num = 0;
    if (res.ok) {
        const json = await res.json();
        num = json.numberOfElems;
    }
    return num;
}

export async function submitDriverType(json) {
    const res = await fetch(config.apiURL + "/driverType", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json"
        }
    });
    return res.status === 200;
}

export async function getAllDriverTypes() {
    let data = [];
    const res = await fetch(config.apiURL + "/driverType");
    if (res.ok) {
        data = await res.json();
    }
    return data;
}