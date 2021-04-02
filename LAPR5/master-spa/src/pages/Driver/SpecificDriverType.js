import React, { useState, useEffect } from "react";
import { BiCheckShield, BiBlock } from 'react-icons/bi';
import { getDriverByCode } from "./../../service/DriverService";

function SpecificDriverType({ match }) {

    const [driver, setDriver] = useState({
        mecanographicNumber: "",
        citizenCard: {
            driverName: "",
            birthDate: "",
            citizenCardNumber: 0,
            driverNIF: 0
        },
        entranceDate: "",
        departureDate: "",
        driverLicense: {
            numberDriverLicense: "",
            dateDriverLicense: ""
        },
        driverTypes: []
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {

        let driver = await getDriverByCode(match.params.driverId);
        setDriver(driver);
        console.log(driver);
    }

    return (

        <div>
            <br />
            <br />

            <div>

                <div>
                    <table data-testid="specificDriverTypeTableTestID" className="content-table">
                        <thead>
                            <tr>
                                <th>Driver Name</th>
                                <th>Birth Date</th>
                                <th>CC Number</th>
                                <th>NIF</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                <tr>
                                    <td>{driver.citizenCard.driverName}</td>
                                    <td>{driver.citizenCard.birthDate}</td>
                                    <td>{driver.citizenCard.citizenCardNumber}</td>
                                    <td>{driver.citizenCard.driverNIF}</td>
                                </tr>

                            }
                        </tbody>

                    </table>
                </div>
            </div>
            <br />
            <br />
            <br />
            <br />
            <div>

                <div>
                    <table data-testid="specificDriverTypeTableTestID" className="content-table">
                        <thead>
                            <tr>
                                <th>Driver License Number</th>
                                <th>Expiration Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                <tr>
                                    <td>{driver.driverLicense.numberDriverLicense}</td>
                                    <td>{driver.driverLicense.dateDriverLicense}</td>
                                </tr>

                            }
                        </tbody>

                    </table>
                </div>
            </div>
            <br />
            <br />
            <br />
            <br />
            <div>
                <table data-testid="specificDriverTypeTableTestID" className="content-table">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            driver.driverTypes.map(elem => (
                                <tr>
                                    <td>{elem.code}</td>
                                    <td>{elem.description}</td>
                                </tr>
                            ))
                        }
                    </tbody>

                </table>
            </div>
        </div>
    )
}

export default SpecificDriverType;