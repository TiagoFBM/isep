import config from "../config";

export async function getDriverByCode(id) {
    const res = await fetch(config.mdvURL + `/driver/${id} `);
    let data = [];
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getAllDrivers({ skip, limit }) {
    let data = [];
    const res = await fetch(
        config.mdvURL + `/driver/pages?skip=${skip}&limit=${limit}`
    );
    if (res.ok) {
        data = await res.json();
    }
    return data;
}


export async function getAllDriversCount() {
    const res = await fetch(config.mdvURL + "/vehicle/count");
    let num = 0;
    if (res.ok) {
        num = await res.json();
    }
    return num;
}

export async function submitDriver(json) {
    const res = await fetch(config.mdvURL + "/driver", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json",
        },
    });
    return res.status === 201;
}