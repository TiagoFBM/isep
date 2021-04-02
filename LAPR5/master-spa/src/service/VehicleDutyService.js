import config from "../config";

export async function getAllVehicleDuties() {
  let data = [];
  const res = await fetch(config.mdvURL + "/vehicleDuty");
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getVehicleDuties({ skip, limit }) {
  let data = [];
  const res = await fetch(
    config.mdvURL + `/vehicleDuty/pages?skip=${skip}&limit=${limit}`
  );
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getTripById(id) {
  const res = await fetch(config.mdvURL + `/vehicleDuty/${id}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getWorkBlockByID(id) {
  const res = await fetch(config.mdvURL + `/vehicleDuty/workblock/${id}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function submitVehicleDuty(json) {
  const res = await fetch(config.mdvURL + "/vehicleDuty", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });

  return res.status === 201;
}

export async function addWorkblocksToVehicleDuty(json) {
  const res = await fetch(config.mdvURL + "/vehicleDuty/workblock", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });

  return res.status === 201;
}