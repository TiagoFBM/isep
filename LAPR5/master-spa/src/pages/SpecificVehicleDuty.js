import React, {useState, useEffect} from "react";
import "./../css/Path.css";
import { BiX, BiCheck } from 'react-icons/bi';
import { getTripById , addWorkblocksToVehicleDuty} from "../service/VehicleDutyService";
import { Link } from "react-router-dom";
import Modal from 'react-modal';

function SpecificVehicleDuty({ match }) {

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

    async function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setDuration("");
        setMaxNumber("");
        setConfigs([]);
        setSelectedConfigText("");
        fetchData();
    }

    function openModal() {
        setIsOpen(true);
    }

    const [modalIsOpen, setIsOpen] = useState(false);

    const [maxNumber, setMaxNumber] = useState("");
    const [duration, setDuration] = useState("");

    const [configs, setConfigs] = useState([]);
    const [selectedConfigText, setSelectedConfigText] = useState("");

    const [vehicleDuty, setVehicleDuty] = useState({vehicleDutyCode:"", trips: [], workBlocks:[]});

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        let vd = await getTripById(match.params.id);
        setVehicleDuty(vd);
    }

    const handleSubmit = async e => {
        e.preventDefault();
        const json = JSON.stringify({
            vehicleDutyId: match.params.id,
            workblockConfigs: configs
        });
        let ok = await addWorkblocksToVehicleDuty(json);
        if (ok) {
            alert("Successfully added to DB.");
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    const addConfig = (e) => {
        if (maxNumber > 0 && duration > 0) {
            const json = {maximumNumber: maxNumber, workBlockMaxDuration: duration};
            configs.push(json);
            let configText = selectedConfigText + "Max Number -> " + maxNumber + " | Max Duration -> " + duration + "\n";
            setSelectedConfigText(configText);
            setConfigs([...configs]);
            setMaxNumber("");
            setDuration("");
            console.log(configs);
        }
        // if (travelTime >= 0 && distance >= 0 && firstNode !== "" && secondNode !== "") {
        //     const json = { firstNodeID: firstNode, secondNodeID: secondNode, travelTimeBetweenNodes: travelTime, distanceBetweenNodes: distance };
        //     segments.push(json);
        //     let segmText = segmentText + `${firstNode} -> ${secondNode} - Travel time = ${travelTime} | Distance = ${distance}\n`;
        //     setSegments([...segments]);
        //     setSegmentText(segmText);
        //     setFirstNode(secondNode);
        //     setSecondNode("");
        //     setDistance(0);
        //     setTravelTime(0);
        // }
    }

    return (
        <div>
            <div className="createElem">
                <button data-testid="pathModalOpenBtnTestID" className="addBtn" onClick={openModal}>Add work blocks</button>
            </div>
            <table className="content-table"> 
                <thead>
                    <tr>
                        <th>Code</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{vehicleDuty.vehicleDutyCode}</td>
                    </tr>
                </tbody>
            </table>
            <br />
            <table className="content-table">
                <thead>
                    <tr>
                        <th>Line Id</th>
                        <th>Path Id</th>
                        <th>Departure Time</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    {vehicleDuty.trips.map(elem => (
                        <tr>
                            <td>{elem.lineID.id}</td>
                            <td>{elem.pathID.id}</td>
                            <td>{elem.tripDepartureTime}</td>
                            <td>
                                <button data-testid="vehicleDutyMoreInfoBtnTestID">
                                    <Link to={`/trip/${elem.tripId}`}>More Info</Link>
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <br/>
            <table className="content-table">
                <thead>
                    <tr>
                        <th>Workblock number</th>
                        <th>Starting Time</th>
                        <th>Ending Time</th>
                    </tr>
                </thead>
                <tbody>
                    {vehicleDuty.workBlocks.map((elem, key) => (
                        <tr>
                            <td>{"WorkBlock:" + (key + 1)}</td>
                            <td>{elem.startingTime}</td>
                            <td>{elem.endingTime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
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
                    <form data-testid="workblockSubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <label data-testid="workblockMaxNumberInputTestID">Max Number of Workblocks</label><br />
                        <input className="textField" type="number" name="maxNumber" value={maxNumber} onChange={e => setMaxNumber(e.target.value)}></input><br />
                        <br />
                        <label data-testid="workblockMaxNumberInputTestID">Max Workblock duration (in minutes)</label><br />
                        <input className="textField" type="number" name="duration" value={duration} onChange={e => setDuration(e.target.value)}></input><br />
                        <br />
                        <br /><button type="button" className="addField" onClick={addConfig}>Add</button>
                        <label>Selected configs</label>
                        <textarea className="textField" rows="5" name="configs" value={selectedConfigText} disabled></textarea><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );
}

export default SpecificVehicleDuty;