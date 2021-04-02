import React, { useState, useEffect } from "react";

import { BrowserRoute as Router, Switch, Route } from "react-router-dom";
import config from "../config";



function BestPath() {

    const [data, setData] = useState({ lista: [] });

    const [initialNode, setInitialNode] = useState("");
    const [finalNode, setFinalNode] = useState("");
    const [schedule, setSchedule] = useState("");


    const handleSubmit = async e => {
        e.preventDefault();

        var url = new URL(config.planningURL + '/bestPath');

        url.searchParams.append("initialNode", initialNode);
        url.searchParams.append("finalNode", finalNode);
        url.searchParams.append("schedule", schedule.toString());

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

    return (
        <div>
            <div className="divBestPath">
                <form onSubmit={handleSubmit} method="GET">
                    <label data-testid="bestPathInitialNodeInputTestID">Initial Node</label><br />
                    <input type="text" name="initialNode" value={initialNode} onChange={e => setInitialNode(e.target.value)} required></input><br />

                    <label data-testid="bestPathFinalNodeInputTestID">Final Node</label><br />
                    <input type="text" name="finalNode" value={finalNode} onChange={e => setFinalNode(e.target.value)} required></input><br />

                    <label data-testid="bestPathScheduleInputTestID">Schedule</label><br />
                    <input type="number" name="schedule" value={schedule} onChange={e => setSchedule(e.target.value)} required></input><br />
                    <div>
                        <input data-testid="bestPathSubmitBtnTestID" id="submit" type="submit" value="Submit"
                            style={{
                                float: "left",
                                padding: "10px 60px",
                                marginTop: "10px",

                            }}></input><br /> <br /><br /><br />

                    </div>
                </form>
            </div>

            <div>
                <div style={{
                    width: "100%",
                    float: "left",
                    padding: "10px 60px",
                    marginTop: "10px",

                }}>
                    <label className="labelBestPath"> Time: </label>
                    <input type="text" name="durationPath" className="inputBestPath" value={
                        (data.tempo === undefined) ? " " :
                            (parseInt(data.tempo / 3600) + ":") +
                            ((parseInt((data.tempo % 3600) / 60)) + ":") +
                            ((parseInt(data.tempo % 60)))
                    } disabled>
                    </input>
                </div>
                <table className="content-table-best-path">
                    <thead>
                        <tr>
                            <th>Best Path</th>
                        </tr>
                    </thead>
                    <tbody>
                        {(data.lista.map(elem => (
                            <tr>
                                <td>{elem}</td>
                            </tr>
                        )))
                        }
                    </tbody>
                </table>
            </div>
        </div >
    )
}


export default BestPath;