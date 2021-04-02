import config from "../config";

export async function getAllNodes() {
  let data = [];
  const res = await fetch(config.apiURL + `/node`);
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getNonReliefDepotNodes() {
  let data = await getAllNodes();
  let nonReliefDepotPoints = [];

  data.forEach((node) => {
    if (!node.isReliefPoint && !node.isDepot) {
      nonReliefDepotPoints.push(node);
    }
  });

  return nonReliefDepotPoints;
}

export async function getReliefDepotNodes() {
    let data = await getAllNodes();
    let reliefDepotPoints = [];
  
    data.forEach((node) => {
      if (node.isReliefPoint || node.isDepot) {
        reliefDepotPoints.push(node);
      }
    });
  
    return reliefDepotPoints;
  }

export async function getNodes({ skip, limit }) {
  let data = [];
  const res = await fetch(config.apiURL + `/node?skip=${skip}&limit=${limit}`);
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getNodeCount() {
  const res = await fetch(config.apiURL + "/node/count");
  let num = 0;
  if (res.ok) {
    const json = await res.json();
    num = json.numberOfElems;
  }
  return num;
}

export async function submitNode(json) {
  const res = await fetch(config.apiURL + "/node", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });
  return res.status === 200;
}

export async function getNodeByCode(code) {
  const res = await fetch(config.apiURL + `/node/${code}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}
