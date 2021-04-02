import config from "../config";

export async function getAllLines() {
  let data = [];
  const res = await fetch(config.apiURL + `/line`);
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getLines({ skip, limit }) {
  let data = [];
  const res = await fetch(config.apiURL + `/line?skip=${skip}&limit=${limit}`);
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getLineCount() {
  const res = await fetch(config.apiURL + "/line/count");
  let num = 0;
  if (res.ok) {
    const json = await res.json();
    num = json.numberOfElems;
  }
  return num;
}

export async function getLineByCode(code) {
  const res = await fetch(config.apiURL + `/line/${code}`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function getLinePathsByCode(code) {
  const res = await fetch(config.apiURL + `/line/${code}/paths`);
  let data = {};
  if (res.ok) {
    data = await res.json();
  }
  return data;
}

export async function submitLine(json) {
  const res = await fetch(config.apiURL + "/line", {
    method: "POST",
    body: json,
    headers: {
      "Content-Type": "application/json",
    },
  });
  return res.status === 200;
}

function segmentEqual(segmentA, segmentB) {
  return (
    segmentA.firstNodeID.code === segmentB.firstNodeID.code &&
    segmentA.secondNodeID.code === segmentB.secondNodeID.code
  );
}

function segmentoExiste(segmentosDesenhados, segment) {
  for (let i in segmentosDesenhados) {
    let segm = segmentosDesenhados[i];
    if (segmentEqual(segm, segment)) {
      return true;
    }
  }
  return false;
}


export async function adaptLines() {
  let drawnSegments = [];

  let newLines = [];
  let infoLines = await getAllLines();



  for (let i in infoLines) {
    let line = infoLines[i];
    let newLinePaths = adaptLine(line, drawnSegments);
    let newLine = {
      code: line.code,
      description: line.description,
      linePaths: newLinePaths,
      lineColor: line.lineColor
    }
    newLines.push(newLine);
  }

  return newLines;
}

function adaptLine(line, drawnSegments) {
  let newLinePaths = [];
  let linePaths = line.linePaths;
  for (let k in linePaths) {

    let linePath = linePaths[k];
    let newSegments = [];

    for (let p in linePath.path.segments) {
      let segment = linePath.path.segments[p];


      if (!segmentoExiste(drawnSegments, segment)) {
        newSegments.push(segment);
        drawnSegments.push(segment);
      } else {

        //Long = X
        //Lat = Y
        //A = firstNode
        //B = secondNode

        let firstNode = segment.firstNodeID;
        let secondNode = segment.secondNodeID;

        let pontoMedio = {
          longitude: secondNode.longitude - firstNode.longitude,
          latitude: secondNode.latitude - firstNode.latitude,
        };

        let alfa = Math.atan2(pontoMedio.latitude, pontoMedio.longitude);

        let beta = alfa + Math.PI / 2;

        //Distancia da linha antiga para a nova
        const d = 0.001;

        let newFirstNode = {
          code: firstNode.code,
          latitude: parseFloat(firstNode.latitude, 10) + d * Math.sin(beta),
          longitude: parseFloat(firstNode.longitude, 10) + d * Math.cos(beta),
          name: firstNode.name,
          shortName: firstNode.shortName,
          isDepot: firstNode.isDepot,
          isReliefPoint: firstNode.isReliefPoint,
          crewTravelTimes: firstNode.crewTravelTimes
        };

        let newSecondNode = {
          code: secondNode.code,
          latitude: parseFloat(secondNode.latitude, 10) + d * Math.sin(beta),
          longitude: parseFloat(secondNode.longitude, 10) + d * Math.cos(beta),
          name: secondNode.name,
          shortName: secondNode.shortName,
          isDepot: secondNode.isDepot,
          isReliefPoint: secondNode.isReliefPoint,
          crewTravelTimes: secondNode.crewTravelTimes
        };

        let newSegment = {
          firstNodeID: newFirstNode,
          secondNodeID: newSecondNode,
          travelTimeBetweenNodes: segment.travelTimeBetweenNodes,
          distanceBetweenNodes: segment.crewTravelTimes
        }

        newSegments.push(newSegment);
      }
    }
    let newlinePath = {
      code: linePath.path.code,
      isEmpty: linePath.path.isEmpty,
      segments: newSegments,
      orientation: linePath.orientation
    }

    newLinePaths.push(newlinePath);

  }
  //console.log(newLinePaths);
  return newLinePaths;
}