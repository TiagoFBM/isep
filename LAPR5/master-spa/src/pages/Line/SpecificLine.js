import React, { useState, useEffect } from "react";
import "./../../css/SpecificLine.css";
import { BiCheckShield, BiBlock } from 'react-icons/bi';
import { getLineByCode } from "./../../service/LineService";
import { Link } from "react-router-dom";

function SpecificLine({ match }) {

    const [line, setLine] = useState({
        code: "",
        description: "",
        linePaths: [],
        allowedVehicles: [],
        deniedVehicles: [],
        allowedDrivers: [],
        deniedDrivers: [],
        lineColor: ""
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        let line = await getLineByCode(match.params.code);
        setLine(line);
    }

    return (

        <div>

            <span data-testid="specificLineTitleTestID" className="specificLineTitle">{line.code}</span>
            <br />
            <br />

            <div>
                <table data-testid="specificLineTableTestID" className="content-table">
                    <thead>
                        <tr>
                            <th>Path</th>
                            <th>Orientation</th>
                        </tr>
                    </thead>
                    <tbody>
                        {line.linePaths.map(elem => (
                            <tr>
                                <td><Link to={`/path/${elem.path.code}`}>{elem.path.code}</Link></td>
                                <td>{elem.orientation}</td>
                            </tr>
                        ))}
                    </tbody>

                </table>
            </div>

            <br />

            <div>
                <table className="content-table">
                    <thead>
                        <tr>
                            <th>Vehicle Type</th>
                            <th>Restriction</th>
                        </tr>
                    </thead>
                    <tbody>
                        {line.allowedVehicles.map(elem => (
                            <tr>
                                <td>{elem.code}</td>
                                <td><BiCheckShield /></td>
                            </tr>
                        ))}
                        {line.deniedVehicles.map(elem => (
                            <tr>
                                <td>{elem.code}</td>
                                <td><BiBlock /></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <br />
            <div>
                <table className="content-table">
                    <thead>
                        <tr>
                            <th>Driver Type</th>
                            <th>Restriction</th>
                        </tr>
                    </thead>
                    <tbody>
                        {line.allowedDrivers.map(elem => (
                            <tr>
                                <td>{elem.code}</td>
                                <td><BiCheckShield /></td>
                            </tr>
                        ))}
                        {line.deniedDrivers.map(elem => (
                            <tr>
                                <td>{elem.code}</td>
                                <td><BiBlock /></td>
                            </tr>
                        ))}
                    </tbody>

                </table>
            </div>
        </div>
    )
}

export default SpecificLine;