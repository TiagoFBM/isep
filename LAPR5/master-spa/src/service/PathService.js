import config from "../config";

export async function getPaths({skip, limit}) {
    let data = [];
    const res = await fetch(config.apiURL + `/path?skip=${skip}&limit=${limit}`);
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getPathCount() {
    const res = await fetch(config.apiURL + "/path/count");
    let num = 0;
    if (res.ok) {
        const json = await res.json();
        num = json.numberOfElems;
    }
    return num;
}

export async function submitPath(json) {
    const res = await fetch(config.apiURL + "/path", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json"
        }
    });
    return res.status === 200;
}

export async function getPathByCode(code) {
    const res = await fetch(config.apiURL + `/path/${code}`);
    let data = {};
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getAllPaths() {
    let data = [];
    const res = await fetch(config.apiURL + "/path");
    if (res.ok) {
        data = await res.json();
    }
    return data;
}