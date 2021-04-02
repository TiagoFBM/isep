import config from "../config";

export async function getAllVehicles() {
    let data = [];
    const res = await fetch(config.mdvURL + "/vehicle");
    if (res.ok) {
        data = await res.json();
    }
    console.log(data);
    return data;
}

export async function getVehicles({ skip, limit }) {
    let data = [];
    const res = await fetch(
      config.mdvURL + `/vehicle/pages?skip=${skip}&limit=${limit}`
    );
    if (res.ok) {
      data = await res.json();
    }
    return data;
  }

  export async function getVehicleCount() {
    const res = await fetch(config.mdvURL + "/vehicle/count");
    let num = 0;
    if (res.ok) {
      num = await res.json();
    }
    return num;
  }

export async function submitVehicle(json) {
    const res = await fetch(config.mdvURL + "/vehicle", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json",
        },
    });
    return res.status === 201;
}