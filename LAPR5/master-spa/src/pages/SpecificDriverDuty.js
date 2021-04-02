import React, { useState, useEffect } from "react";
import "./../css/Path.css";
import { getDriverDutyById } from '../service/DriverDutyService';
import { Link } from "react-router-dom";


function SpecificDriverDuty({ match }) {
    const [driverDuty, setDriverDuty] = useState({
        driverDutyCode: "",
        listWorkBlocks: []

    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        console.log(match.params.driverDutyId);
        let driverDuty = await getDriverDutyById(match.params.driverDutyId);
        console.log(driverDuty);
        setDriverDuty(driverDuty);
    }

    return (

        <div>
            <br />
            <br />

            <div>

                <div>
                    <br />
                    <br />

                    <table data-testid="specificDriverDutyTimesTableTestID" className="content-table">
                        <thead>
                            <tr>
                                <th>Starting Time</th>
                                <th>Ending Time</th>

                            </tr>
                        </thead>
                        <tbody>
                            {
                                driverDuty.listWorkBlocks.map(elem => (

                                    <tr>
                                        <td>{elem.startingTime}</td>
                                        <td>{elem.endingTime}</td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>

                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />

                    <table data-testid="specificDriverDutyTableTestID" className="content-table">
                        <thead>
                            <tr>
                                <th>Line</th>
                                <th>Path</th>
                                <th>Depart Time</th>
                                <th>Trip Details</th>

                            </tr>
                        </thead>
                        <tbody>
                            {
                                driverDuty.listWorkBlocks.map(elem => (

                                    elem.trips.map(e => (
                                        <tr>
                                            <td><button data-testid="tripLineBtnTestID">
                                                <Link to={`/trip/line/${e.lineID.id}`}>
                                                    {e.lineID.id}
                                                </Link>
                                            </button></td>
                                            <td><button data-testid="tripPathBtnTestID">
                                                <Link to={`/path/${e.pathID.id}`}>{e.pathID.id}</Link>
                                            </button></td>
                                            <td>{e.tripDepartureTime}</td>
                                            <td>
                                                <button data-testid="tripMoreInfoBtnTestID">
                                                    <Link to={`/trip/${e.tripId}`}>More info</Link>
                                                </button>
                                            </td>
                                        </tr>
                                    ))

                                ))
                            }


                        </tbody>

                    </table>
                </div>
            </div>
        </div>

    )
};

export default SpecificDriverDuty;