import React, { useRef, useEffect, useState } from "react";
import mapboxgl from "mapbox-gl";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import "./../css/Map.css";
import { getAllNodes } from "../service/NodeService";
import { adaptLines } from "../service/LineService";
import { log } from "three";


const busModel = "http://127.0.0.1:5500/master-spa/models/hcr2_bus/scene.gltf";



mapboxgl.accessToken =
  "pk.eyJ1IjoidGlhZ29tb3JlaXJhIiwiYSI6ImNraHhlZndqOTI1c2cyc2t6NmExMWc0Mm0ifQ.ML5CEkPxqMeah5N9CF0psg";

function isInsideCircle(circle_x, circle_y, rad, x, y) {
  if ((x - circle_x) * (x - circle_x) +
    (y - circle_y) * (y - circle_y) <= rad * rad)
    return true;
  else
    return false;
}

export async function getLines() { }

export default function Map() {
  const mapContainerRef = useRef(null);

  const [infoNodes, setInfoNodes] = useState([]);
  const [infoLines, setInfoLines] = useState([]);
  const [intensityValue, setIntensityValue] = useState(100);
  const [angleValue, setAngleValue] = useState(50);

  // initialize map when component mounts
  useEffect(() => {
    const map = new mapboxgl.Map({
      container: mapContainerRef.current,
      style: "mapbox://styles/mapbox/streets-v11",
      center: { lat: 41.1987898023744, lng: -8.38716802227697 },
      zoom: 15
    });

    // add navigation control (the +/- zoom buttons)
    map.addControl(new mapboxgl.NavigationControl(), "bottom-right");

    var busMarkerInUse = false;

    // pixels the map pans when the up or down arrow is clicked
    var deltaDistance = 50;

    // degrees the map rotates when the left or right arrow is clicked
    var deltaDegrees = 10;

    function easing(t) {
      return t * (2 - t);
    }

    // Converts numeric degrees to radians
    function toRad(Value) {
      return Value * Math.PI / 180;
    }

    function calcNearest(coordinatesMap, coordinatesNodes) {
      var minDistancia = 99999999999999;
      let coordinates = [];

      coordinatesNodes.forEach((coords) => {

        var distance = calcCrow(coordinatesMap.lat, coordinatesMap.lng, coords.lat, coords.lng);

        if (distance < minDistancia) {
          coordinates = { lat: coords.lat, lng: coords.lng };
          minDistancia = distance;
        }
      });
      return coordinates;
    }

    function calcCrow(lat1, lon1, lat2, lon2) {
      var R = 6371; // km
      var dLat = toRad(lat2 - lat1);
      var dLon = toRad(lon2 - lon1);
      var lat1 = toRad(lat1);
      var lat2 = toRad(lat2);

      var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      var d = R * c;
      return d;
    }


    map.on("load", function () {
      let el = document.createElement("div");
      el.className = "busMarkerTop";
      const radius = 0.003;

      var busMarker = new mapboxgl.Marker(el).setLngLat(map.getCenter());

      let coordinates = [];
      let oldCoordinates = [];

      infoNodes.forEach((node) => {
        coordinates.push({ lat: parseFloat(node.latitude), lng: parseFloat(node.longitude) });
      });

      let markers2DArray = [];
      let buildings3DArray = [];
      map.dragRotate.disable();

      map.on('move', function (e) {

        let newCoordinates = calcNearest(map.getCenter(), coordinates);
        if (busMarkerInUse == true) {
          if (isInsideCircle(map.getCenter().lat, map.getCenter().lng, radius, newCoordinates.lat, newCoordinates.lng)) {

            alert("You crash!");
            console.log(oldCoordinates);
            busMarker.setLngLat({ lat: parseFloat(oldCoordinates.lat), lng: parseFloat(oldCoordinates.lng) });
            map.setCenter({ lat: parseFloat(oldCoordinates.lat), lng: parseFloat(oldCoordinates.lng) });
          } else {
            busMarker.setLngLat(map.getCenter());
            oldCoordinates = ({ lat: parseFloat(map.getCenter().lat), lng: parseFloat(map.getCenter().lng) });
          }
        }


      });

      map.getCanvas().focus();

      map.getCanvas().addEventListener(
        "keydown",
        function (e) {
          if (busMarkerInUse === true) {
            e.preventDefault();

            if (e.which === 87) {
              // up
              map.panBy([0, -deltaDistance], {
                easing: easing,
              });
            } else if (e.which === 83) {
              // down
              map.panBy([0, deltaDistance], {
                easing: easing,
              });
            } else if (e.which === 65) {
              // left

              map.easeTo({
                bearing: map.getBearing() - deltaDegrees,
                easing: easing,
              });
            } else if (e.which === 68) {
              // right
              map.easeTo({
                bearing: map.getBearing() + deltaDegrees,
                easing: easing,
              });
            }
          }
        },
        true
      );

      // Insert the layer beneath any symbol layer.
      let layers = map.getStyle().layers;

      //3D
      let buildings3DLayer = {
        id: "3d-buildings",
        source: "composite",
        "source-layer": "building",
        filter: ["==", "extrude", "true"],
        type: "fill-extrusion",
        minzoom: 15,
        paint: {
          "fill-extrusion-color": "#aaa",

          // use an 'interpolate' expression to add a smooth transition effect to the
          // buildings as the user zooms in
          "fill-extrusion-height": [
            "interpolate",
            ["linear"],
            ["zoom"],
            5,
            0,
            15.05,
            ["get", "height"],
          ],
          "fill-extrusion-base": [
            "interpolate",
            ["linear"],
            ["zoom"],
            15,
            0,
            15.05,
            ["get", "min_height"],
          ],
          "fill-extrusion-opacity": 1,
        },
      };
      map.addLayer(buildings3DLayer);

      let link3d = document.createElement("a");
      link3d.href = "#";
      link3d.className = "active";
      link3d.textContent = "3D";

      link3d.onclick = function (e) {
        e.preventDefault();
        e.stopPropagation();

        let buildingsLayer = map.getLayer(buildings3DLayer.id);

        if (buildingsLayer != undefined) {
          let visibility = map.getLayoutProperty(
            buildings3DLayer.id,
            "visibility"
          );

          // toggle layer visibility by changing the layout object's visibility property
          if (visibility === "visible") {
            //2D
            map.setLayoutProperty(buildings3DLayer.id, "visibility", "none");
            this.className = "";
            map.dragRotate.disable();
            map.setPitch(0);

            map.setLayoutProperty(buildings3DLayer.id, "visibility", "none");

            markers2DArray.forEach((marker) => {
              marker.addTo(map);
            });

            buildings3DArray.forEach((building) => {
              map.removeLayer(building.id);
            });

            busMarker.remove();

            let el = document.createElement("div");
            el.className = "busMarkerTop";

            busMarker = new mapboxgl.Marker(el).setLngLat(map.getCenter());

            if (busMarkerInUse === true) {
              busMarker.addTo(map);
            }
          } else {
            //3D
            this.className = "active";
            map.setLayoutProperty(buildings3DLayer.id, "visibility", "visible");
            map.dragRotate.enable();
            map.setPitch(80);

            markers2DArray.forEach((marker) => {
              marker.remove();
            });

            buildings3DArray.forEach((building) => {
              map.addLayer(building);
            });

            busMarker.remove();

            let el = document.createElement("div");
            el.className = "busMarker";

            busMarker = new mapboxgl.Marker(el).setLngLat(map.getCenter());

            if (busMarkerInUse === true) {
              busMarker.addTo(map);
            }
          }
        }
      };

      let thirdPersonlink = document.createElement("a");
      thirdPersonlink.href = "#";
      thirdPersonlink.className = "active";
      thirdPersonlink.textContent = "Third Person";

      let sliderIntensityDiv = document.createElement("div");
      let sliderIntensityText = document.createElement("span");
      sliderIntensityText.innerText = "Light intensity: ";

      let sliderIntensity = document.createElement("input");
      sliderIntensity.setAttribute("type", "range");
      sliderIntensity.setAttribute("min", "0");
      sliderIntensity.setAttribute("max", "100");
      sliderIntensity.setAttribute("value", intensityValue);
      sliderIntensity.setAttribute("id", "sliderIntensity");

      sliderIntensityDiv.appendChild(sliderIntensityText);
      sliderIntensityDiv.appendChild(sliderIntensity);

      sliderIntensity.onchange = (e => {
        setIntensityValue(document.getElementById("sliderIntensity").value);
      });

      let sliderAngleDiv = document.createElement("div");
      let sliderAngleText = document.createElement("span");
      sliderAngleText.innerText = "Light angle: ";

      let sliderAngle = document.createElement("input");
      sliderAngle.setAttribute("type", "range");
      sliderAngle.setAttribute("min", "0");
      sliderAngle.setAttribute("max", "100");
      sliderAngle.setAttribute("value", angleValue);
      sliderAngle.setAttribute("id", "sliderAngle");

      sliderAngleDiv.appendChild(sliderAngleText);
      sliderAngleDiv.appendChild(sliderAngle);

      sliderAngle.onchange = (e => {
        setAngleValue(document.getElementById("sliderAngle").value);
      })

      thirdPersonlink.onclick = function (e) {
        if (busMarkerInUse === true) {
          busMarker.remove();
          busMarkerInUse = false;
        } else {
          busMarker.addTo(map);
          busMarkerInUse = true;
        }
      };

      layers = document.getElementsByClassName("map-container")[0];
      const x = document.createElement("NAV");
      x.id = "menu";
      x.appendChild(link3d);
      x.appendChild(thirdPersonlink);
      x.appendChild(sliderIntensityDiv);
      x.appendChild(sliderAngleDiv);
      layers.appendChild(x);
      infoNodes.forEach((node) => {
        let marker = createMarker(node, false);
        let hiddenMarker = createMarker(node, true);

        marker.addTo(map);
        hiddenMarker.addTo(map);

        let filePath;

        if (node.isDepot || node.isReliefPoint) {
          filePath = "http://127.0.0.1:5500/master-spa/models/dp.gltf";
        } else {
          filePath = "http://127.0.0.1:5500/master-spa/models/rp.gltf";
        }

        let customLayer = addObject3D(
          node.latitude,
          node.longitude,
          filePath,
          0,
          5
        );

        markers2DArray.push(marker);
        buildings3DArray.push(customLayer);
      });

      //console.log(infoLines);

      for (let i in infoLines) {
        let line = infoLines[i];
        //console.log(line);

        for (let k in line.linePaths) {
          let path = line.linePaths[k];

          let coordinates = [];

          for (let j in path.segments) {
            let segment = path.segments[j];

            coordinates.push([
              parseFloat(segment.firstNodeID.longitude),
              parseFloat(segment.firstNodeID.latitude),
            ]);
            coordinates.push([
              parseFloat(segment.secondNodeID.longitude),
              parseFloat(segment.secondNodeID.latitude),
            ]);
          }

          //Define coordenadas para as linhas ligarem
          //O Source deve ser único

          let sourceCoordinates = "coordinates".concat(i).concat(k);
          map.addSource(sourceCoordinates, {
            type: "geojson",
            data: {
              type: "Feature",
              properties: {},
              geometry: {
                type: "LineString",
                coordinates: coordinates,
              },
            },
          });

          //Adiciona a linha com base nos pontos definidos em "coordinates"
          //O id deve ser único
          //console.log(sourceCoordinates);
          //console.log(line.lineColor);
          let IDLinha = "linha".concat(i).concat(k);
          map.addLayer({
            id: IDLinha,
            type: "line",
            source: sourceCoordinates,
            layout: {
              "line-join": "round",
              "line-cap": "round",
            },
            paint: {
              "line-color": line.lineColor,
              "line-width": 6,
              "line-opacity": 1,
            },
          });

          //Aparecem informações da linha quando é clicada
          map.on("click", IDLinha, function (e) {
            new mapboxgl.Popup({
              offset: 10,
              closeOnClick: true,
              closeButton: false,
              closeOnMove: true,
            })
              .setHTML(
                "<h3>" +
                line.description +
                "</h3>" +
                "<p> Number of Paths: " +
                line.linePaths.length +
                " </p>" +
                "<p> Orientation: " +
                path.orientation +
                " </p>"
              )
              .setLngLat(e.lngLat)
              .addTo(map);
          });

          //Reduz opacidade da linha quando o rato está em cima dela
          map.on("mouseenter", IDLinha, function (e) {
            map.getCanvas().style.cursor = "pointer";
            map.setPaintProperty(IDLinha, "line-opacity", 0.3);
          });

          //Retorna à opacidade normal quando o rato sai da linha
          map.on("mouseleave", IDLinha, function (e) {
            map.getCanvas().style.cursor = "";
            map.setPaintProperty(
              "linha".concat(i).concat(k),
              "line-opacity",
              1
            );
          });
        }
      }
    });

    function addObject3D(lat, lng, gltfFile, ladoGraus, scale) {
      let novoLadoGraus;
      if (ladoGraus < 0) {
        novoLadoGraus = 360 + ladoGraus;
      } else {
        novoLadoGraus = ladoGraus;
      }

      if (novoLadoGraus >= 360) {
        novoLadoGraus = novoLadoGraus - 360;
      }

      // parameters to ensure the model is georeferenced correctly on the map
      var modelOrigin = [lng, lat];
      var modelAltitude = 0;
      var modelRotate = [Math.PI / 2, fromGrauToRadianos(novoLadoGraus), 0];

      var modelAsMercatorCoordinate = mapboxgl.MercatorCoordinate.fromLngLat(
        modelOrigin,
        modelAltitude
      );

      // transformation parameters to position, rotate and scale the 3D model onto the map
      var modelTransform = {
        translateX: modelAsMercatorCoordinate.x,
        translateY: modelAsMercatorCoordinate.y,
        translateZ: modelAsMercatorCoordinate.z,
        rotateX: modelRotate[0],
        rotateY: modelRotate[1],
        rotateZ: modelRotate[2],
        /* Since our 3D model is in real world meters, a scale transform needs to be
         * applied since the CustomLayerInterface expects units in MercatorCoordinates.
         */
        scale: modelAsMercatorCoordinate.meterInMercatorCoordinateUnits(),
      };

      // configuration of the custom layer for a 3D model per the CustomLayerInterface
      return {
        id:
          "3dmodel;".concat(lat) + ";".concat(lng) + ";".concat(novoLadoGraus),
        type: "custom",
        renderingMode: "3d",
        onAdd: function (map, gl) {
          this.camera = new THREE.Camera();
          this.scene = new THREE.Scene();

          const dirLight = new THREE.DirectionalLight(0xffffff, intensityValue / 100);
          dirLight.position.set(angleValue - 50, 70, 100);
          let d = 1000;
          let r = 2;
          let mapSize = 8192;
          dirLight.castShadow = true;
          dirLight.shadow.radius = r;
          dirLight.shadow.mapSize.width = mapSize;
          dirLight.shadow.mapSize.height = mapSize;
          dirLight.shadow.camera.top = dirLight.shadow.camera.right = d;
          dirLight.shadow.camera.bottom = dirLight.shadow.camera.left = -d;
          dirLight.shadow.camera.near = 1;
          dirLight.shadow.camera.far = 400000000;
          dirLight.shadow.camera.visible = true;

          this.scene.add(dirLight);
          this.scene.add(new THREE.DirectionalLightHelper(dirLight, 10));

          // create two three.js lights to illuminate the model
          // var directionalLight = new THREE.DirectionalLight(0xffffff);
          // directionalLight.position.set(0, -70, 100).normalize();
          // this.scene.add(directionalLight);

          // var directionalLight2 = new THREE.DirectionalLight(0xffffff);
          // directionalLight2.position.set(0, 70, 100).normalize();
          // this.scene.add(directionalLight2);

          // use the three.js GLTF loader to add the 3D model to the three.js scene
          var loader = new GLTFLoader();
          loader.load(
            gltfFile,
            function (gltf) {
              gltf.scene.traverse(function(model) {
                if (model.isMesh) {
                  model.castShadow = true;
                }
              });
              this.scene.add(gltf.scene);

              const s = new THREE.Box3().setFromObject(gltf.scene).getSize(new THREE.Vector3(0, 0, 0));
              const sizes = [s.x, s.y, s.z];
              const planeSize = Math.max(...sizes) * 10;
              const planeGeo = new THREE.PlaneBufferGeometry(planeSize, planeSize);
              const planeMat = new THREE.ShadowMaterial();
              planeMat.opacity = 0.5;
              let plane = new THREE.Mesh(planeGeo, planeMat);
              plane.rotateX(-Math.PI / 2);
              plane.receiveShadow = true;
              this.scene.add(plane);
            }.bind(this)
          );
          this.map = map;

          // use the Mapbox GL JS map canvas for three.js
          this.renderer = new THREE.WebGLRenderer({
            canvas: map.getCanvas(),
            context: gl,
          });

          this.renderer.autoClear = false;
          //this.renderer.shadowMap.enabled = true;
        },
        render: function (gl, matrix) {
          var rotationX = new THREE.Matrix4().makeRotationAxis(
            new THREE.Vector3(1, 0, 0),
            modelTransform.rotateX
          );
          var rotationY = new THREE.Matrix4().makeRotationAxis(
            new THREE.Vector3(0, 1, 0),
            modelTransform.rotateY
          );
          var rotationZ = new THREE.Matrix4().makeRotationAxis(
            new THREE.Vector3(0, 0, 1),
            modelTransform.rotateZ
          );

          var m = new THREE.Matrix4().fromArray(matrix);
          var l = new THREE.Matrix4()
            .makeTranslation(
              modelTransform.translateX,
              modelTransform.translateY,
              modelTransform.translateZ
            )
            .scale(
              new THREE.Vector3(
                modelTransform.scale * scale,
                -modelTransform.scale * scale,
                modelTransform.scale * scale
              )
            )
            .multiply(rotationX)
            .multiply(rotationY)
            .multiply(rotationZ);

          this.camera.projectionMatrix = m.multiply(l);
          this.renderer.state.reset();
          this.renderer.render(this.scene, this.camera);
          this.map.triggerRepaint();
        },
      };
    }

    function createMarker(node, bool) {
      let el = document.createElement("div");

      if (bool) {
        el.className = "invisibleMarker";
      } else {
        if (node.isDepot || node.isReliefPoint) {
          el.className = "greenMarker";
        } else {
          el.className = "redMarker";
        }
      }

      return new mapboxgl.Marker(el)
        .setLngLat({ lat: node.latitude, lng: node.longitude })
        .setPopup(
          new mapboxgl.Popup({
            offset: 35,
            closeOnClick: true,
            closeButton: false,
            closeOnMove: true,
          }).setHTML(
            "<h3>" +
            node.shortName +
            "</h3>" +
            "<br> Name: " +
            node.name +
            " </br>" +
            "<br> Latitude: " +
            node.latitude +
            " </br>" +
            "<br> Longitude: " +
            node.longitude +
            " </br>" +
            "<br> isDepot: " +
            node.isDepot +
            " </br>" +
            "<br> isReliefPoint: " +
            node.isReliefPoint +
            " </br>"
          )
        );
    }

    function fromGrauToRadianos(grau) {
      return (grau / 180) * Math.PI;
    }

    // clean up on unmount
    return () => {
      map.remove();
      const btn = document.getElementById("menu");
      if (btn != null) {
        btn.remove();
      }
    };
  }, [infoNodes, infoLines, intensityValue, angleValue]); // eslint-disable-line react-hooks/exhaustive-deps

  useEffect(() => {
    (async function fetchData() {
      setInfoNodes(await getAllNodes());
      setInfoLines(await adaptLines());
    })();
  }, []);

  return <div className="map-container" ref={mapContainerRef} />;
}
