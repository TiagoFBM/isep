import React, { useState, useEffect, useReducer } from "react";
import "./../css/GeneticAlgorithm.css";
import config from "../config";

function GeneticAlgorithm() {

    const [data, setData] = useState({ drivers: [], times: [] });

    const [vehicleName, setVehicleName] = useState("");
    const [generationsNumber, setGenerationNumber] = useState("");
    const [dimension, setDimension] = useState("");
    const [crossingProbability, setCrossingProbability] = useState("");
    const [mutationProbability, setMutationProbability] = useState("");

    var color;
    var schedule;

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    if (data.drivers.length != 0) {

        // eliminar repetidos de um array

        var newSet = new Set(data.drivers);

        var newArray = [...newSet];

        var newArrayColor = [];

        for (let j = 0; j < newArray.length; j++) {
            newArrayColor[j] = getRandomColor();

        }

        // inicializar a matriz

        /*var linhas = newArray.length;
        var colunas = 2;
        var matriz = new Array(linhas);
        for (var i = 0; i < linhas; i++) {
            matriz[i] = new Array(colunas);
        }

        // preencher a matriz

        for (var j = 0; j < matriz.length; j++) {

            matriz[j][0] = newArray[j];
            matriz[j][1] = getRandomColor();

        }*/
    }




    const handleSubmit = async e => {
        e.preventDefault();

        var url = new URL('http://localhost:9800/genericAlgorithm');

        url.searchParams.append("vehicleID", vehicleName);
        url.searchParams.append("generationNumber", generationsNumber);
        url.searchParams.append("dimension", dimension);
        url.searchParams.append("crossingProbability", crossingProbability);
        url.searchParams.append("mutationProbability", mutationProbability);

        const fetchData = async () => {
            const info = await fetch(url);
            if (info.ok) {

                const data = await info.json();

                setData(data);

            } else {
                alert("Error");
            }
        }
        fetchData();
    }



    return <div>
        <div>
            <form onSubmit={handleSubmit} method="GET">
                <div>
                    <label data-testid="vehicleName" className="label">Vehicle Name: </label>
                    <input type="number" name="vehicleName" className="inputs" value={vehicleName} onChange={e => setVehicleName(e.target.value)} required></input><br />
                    <label data-testid="generationsNumber" className="label">Number of generations</label>
                    <input type="number" name="generationNumber" className="inputs" value={generationsNumber} onChange={e => setGenerationNumber(e.target.value)} required></input><br />

                    <label data-testid="dimension" className="label">Dimension</label>
                    <input type="number" name="dimension" className="inputs" value={dimension} onChange={e => setDimension(e.target.value)} required></input> <br />

                    <label data-testid="crossingProbability" className="label">Crossing Probability</label>
                    <input type="number" name="crossingProbability" className="inputs" value={crossingProbability} onChange={e => setCrossingProbability(e.target.value)} required></input><br />

                    <label data-testid="mutationProbability" className="label">Mutation Probability</label>
                    <input type="number" name="mutationProbability" className="inputs" value={mutationProbability} onChange={e => setMutationProbability(e.target.value)} required></input> <br />


                    <input data-testid="bestPathSubmitBtnTestID" name= "generate" id="submit" type="submit" value="Generate"
                        style={{
                            float: "left",
                            padding: "10px 60px",
                            backgroundColor: "#373F51",
                            color: "#FFFFFF",
                            marginLeft: "220px"
                        }}></input><br />

                    <br />
                    <br />
                    <br />
                    <br />
                    <br />

                </div>

                <br />
                <br />

                <div id="divTable">
                    <table id="table1">
                        <tr>
                            {(data.times.map(time => (
                                schedule = (parseInt(time / 3600) + ":") +
                                ((parseInt((time % 3600) / 60)) + ":") +
                                ((parseInt(time % 60))),

                                <th>{schedule}</th>

                            )))}
                        </tr>
                        <tr>
                            {(data.drivers.map(elem => (

                                color = newArrayColor[newArray.indexOf(elem)],



                                <td style={{
                                    backgroundColor: color
                                }}>

                                    {elem}</td>
                            )))
                            }

                        </tr>
                    </table>
                </div>

            </form>
        </div>


    </div >

}


export default GeneticAlgorithm;