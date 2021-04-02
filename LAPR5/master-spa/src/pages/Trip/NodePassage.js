import React, { useState, useEffect } from "react";
import { getTripByCode } from "./../../service/TripService";
import "./../../css/NodePassage.css"

function NodePassage({ match }) {

    const [trip, setTrip] = useState({nodePassages: []});

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setTrip(await getTripByCode(match.params.tripId));
    }

    return (
        <div>

            <span data-testid="NodePassageTitleTestID" className="nodePassageTitle">Estimated Travel Times</span>
            <br />
            <hr/>
            <br />

            <div>
                <table data-testid="NodePassageTableTestID" className="content-table">
                    <thead>
                        <tr>
                            <th>Node</th>
                            <th>Passage Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {trip.nodePassages.map(elem => (
                            <tr>
                                <td>{elem.nodeId}</td>
                                <td>{elem.passageTime}</td>
                            </tr>
                        ))}
                    </tbody>

                </table>
            </div>

        </div>
    )
}

export default NodePassage;