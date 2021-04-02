import config from "../config";

export async function getAllTrips() {
  let data = [];
  const res = await fetch(config.mdvURL + "/trip");
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getTrips({ skip, limit }) {
  let data = [];
  const res = await fetch(
    config.mdvURL + `/trip/pages?skip=${skip}&limit=${limit}`
  );
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getTripCount() {
  const res = await fetch(config.mdvURL + "/trip/count");
  let num = 0;
  if (res.ok) {
    num = await res.json();
  }
  return num;
}

export async function getTripByCode(code) {
  const res = await fetch(config.mdvURL + `/trip/${code}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getTripByLineCode(code) {
  const res = await fetch(config.mdvURL + `/trip/line/${code}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function submitSimpleTrip(json) {
  const res = await fetch(config.mdvURL + "/trip", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });

  return res.status === 201;
}

export async function submitComplexTrip(json) {
  const res = await fetch(config.mdvURL + "/trip/complex", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });
  return res.status === 200;
}
