import config from "../config";

export async function submitDriverDuty(json) {
    const res = await fetch(config.mdvURL + "/driverDuty", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json",
        },
    });

    return res.status === 201;
}

export async function getDriverDuties({ skip, limit }) {
    let data = [];
    const res = await fetch(
        config.mdvURL + `/driverDuty/pages?skip=${skip}&limit=${limit}`
    );
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getDriverDutyById(id) {
    let data = [];
    const res = await fetch(
        config.mdvURL + `/driverDuty/${id}`
    );
    if (res.ok) {
        data = await res.json();
    }

    return data;
}


export async function generateDriverDuties() {
    let data = [];

    const res = await fetch(
        'http://localhost:9800/generateDriverDuties'
    );
    console.log(res);
    if (res.ok) {
        data = await res.json();
    }
    console.log(data);
    return data;
}

export async function deleteDriverDuties() {
    let data = [];
    const res = await fetch(
        config.mdvURL + '/driverDuty/all', {
        method: 'DELETE'
    });
    if (res.ok) {
        data = await res.json();
    }
}