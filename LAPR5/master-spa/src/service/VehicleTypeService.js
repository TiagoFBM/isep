import config from "../config";


export async function getVehicleTypes({ skip, limit }) {

    let data = [];
    const res = await fetch(config.apiURL + `/vehicleType?skip=${skip}&limit=${limit}`);
    if (res.ok) {
        data = await res.json();
    }
    return data;
}

export async function getVehicleTypeCount() {
    const res = await fetch(config.apiURL + "/vehicleType/count");
    let num = 0;
    if (res.ok) {
        const json = await res.json();
        num = json.numberOfElems;
    }
    return num;
}

export async function submitVehicleType(json) {
    const res = await fetch(config.apiURL + "/vehicleType", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json"
        }
    });
    return res.status === 200;
}

export async function getAllVehicleTypes() {
    let data = [];
    const res = await fetch(config.apiURL + "/vehicleType");
    if (res.ok) {
        data = await res.json();
    }
    return data;
}