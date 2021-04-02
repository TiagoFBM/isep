import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { Link } from "react-router-dom";
// import { getAllPaths } from "../../service/PathService";
// import { getAllDriverTypes } from "../../service/DriverTypeService";
// import { getAllVehicleTypes } from "../../service/VehicleTypeService";
// import { getLines, getLineCount, submitLine } from "../../service/LineService";

import { getAllVehicles } from "../service/VehicleService";
import { getAllTrips } from "../service/TripService";
import { getVehicleDuties, submitVehicleDuty } from "../service/VehicleDutyService";

function VehicleDuty() {
    const customModalStyle = {
        content: {
            top: '45%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)',
            overflow: "scroll"
        }
    };

    const [data, setData] = useState([]);
    const [numberOfElems, setNumberOfElems] = useState(0);

    const [modalIsOpen, setIsOpen] = useState(false);

    const [skip, setSkip] = useState(0);
    const [limit, setLimit] = useState(20);

    const [code, setCode] = useState("");

    const [vehicle, setVehicle] = useState("");
    const [trip, setTrip] = useState("");
    const [tripDesc, setTripDesc] = useState("");
    const [selectedTrips, setSelectedTrips] = useState([]);
    const [selectedTripsText, setSelectedTripsText] = useState("");
    const [availableTrips, setAvailableTrips] = useState([]);
    const [availableVehicles, setAvailableVehicles] = useState([]);

    async function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setCode("");
        setTrip("");
        setTripDesc("");
        setSelectedTripsText("");
        setSelectedTrips([]);
        setVehicle("");
        fetchData({});
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getVehicleDuties({ skip: _skip, limit: _limit }));
        // setNumberOfElems(await getLineCount());
        // setPaths(await getAllPaths());
        // setDriverTypes(await getAllDriverTypes());
        // setVehicleTypes(await getAllVehicleTypes());
        setAvailableVehicles(await getAllVehicles());
        setAvailableTrips(await getAllTrips());
    }

    const firstPage = async () => {
        var newSkip = 0;
        await setSkip(newSkip);
        fetchData({ _skip: newSkip, _limit: limit });
    }

    const nextPage = async () => {
        var newSkip = skip + limit;
        if (newSkip < numberOfElems) {
            setSkip(newSkip);
            await fetchData({ _skip: newSkip, _limit: limit });
        }
    }

    const previousPage = async () => {
        var newSkip = skip - limit;
        if (newSkip >= 0) {
            setSkip(newSkip);
            await fetchData({ _skip: newSkip, _limit: limit });
        }
    }

    const lastPage = async () => {
        var newSkip = (Math.floor(numberOfElems / limit)) * limit;
        await setSkip(newSkip);
        fetchData({ _skip: newSkip, _limit: limit });
    }

    const handleSubmit = async e => {
        e.preventDefault();
        const json = JSON.stringify({
            vehicleDutyCode: code,
            trips: selectedTrips,
            vehicle: vehicle
        });
        let ok = await submitVehicleDuty(json);
        if (ok) {
            alert("Successfully added to DB.");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    const removeObjectFromArray = (objectArray, objectCode) => {
        let i;

        for (const [key, value] of Object.entries(objectArray)) {
            if (value.tripId === objectCode) {
                console.log(objectCode);
                console.log(value.tripId);
                i = key;
                break;
            }
        }
        const temp = [...objectArray];
        temp.splice(i, 1);
        console.log(temp);
        return temp;
    }

    const addTrip = (e) => {
        if (trip !== "") {
            setAvailableTrips(removeObjectFromArray(availableTrips, trip));
            selectedTrips.push(trip);
            setSelectedTrips([...selectedTrips]);
            let tripTxt = selectedTripsText + tripDesc + "\n";
            setSelectedTripsText(tripTxt);
            setTrip("");
            setTripDesc("");
        }
    }


    return (
        <div>
            <div className="createElem">
                <button data-testid="addVehicleDutyBtnTestID" className="addBtn" onClick={openModal}>Add Vehicle Duty</button>
            </div>
            <table data-testid="vehicleDutyTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.vehicleDutyCode}</td>
                            <td>
                                <button data-testid="vehicleDutyMoreInfoBtnTestID">
                                    <Link to={`/vehicleDuty/${elem.vehicleDutyId}`}>More Info</Link>
                                </button>
                            </td>
                            <td className="action">
                                <button>Update</button>
                                <button>Delete</button>
                            </td>

                        </tr>

                    ))}
                </tbody>
            </table>


            <div className="pagination">
                <span className="clickableSpan" onClick={firstPage}><BiFirstPage /></span>
                <span className="clickableSpan" onClick={previousPage}><BiChevronLeft /></span>
                {skip / limit + 1}
                <span className="clickableSpan" onClick={nextPage}><BiChevronRight /></span>
                <span className="clickableSpan" onClick={lastPage}><BiLastPage /></span>
            </div>


            <Modal
                ariaHideApp={false}
                wariaHideApp={false}
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customModalStyle}
                contentLabel="Example Modal">
                <div className="form">
                    <span className="clickableSpan closeCross" onClick={closeModal}><BiX /></span><br />
                    <form data-testid="vehicleDutySubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <label data-testid="vehicleDutyCodeInputTestID">Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <br />
                        <hr />
                        <br />

                        <br />
                        <hr />
                        <br />

                        <label>Available Trips </label>
                        <select name="trip" value={trip} onChange={e => { setTrip(e.target.value); var index = e.target.selectedIndex; setTripDesc(e.target[index].text)}}>
                            <option value="" defaultValue disabled hidden></option>
                            {availableTrips.map(elem => (
                                <option value={elem.tripId}>{elem.lineID.id + " - " + elem.pathID.id + " | " + elem.tripDepartureTime }</option>
                            ))}
                        </select><br />

                        <br />
                        <br /><button type="button" className="addField" onClick={addTrip}>Add</button>
                        <label>Trips</label>
                        <textarea className="textField" rows="5" name="paths" value={selectedTripsText} disabled></textarea><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );


}

export default VehicleDuty;