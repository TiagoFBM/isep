import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { Link } from "react-router-dom";
import { getAllVehicleDuties, getTripById } from "../service/VehicleDutyService";
import { submitDriverDuty, getDriverDuties, deleteDriverDuties } from "../service/DriverDutyService";


function DriverDuty() {
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
    const [limit, setLimit] = useState(10);

    const [code, setCode] = useState("");
    const [name, setName] = useState("");
    const [color, setColor] = useState("");
    const [workblocks, setWorkBlocks] = useState([]);
    const [vehicleDutys, setVehicleDutys] = useState([]);
    const [workblocksVD, setVDWorkBlocks] = useState([]);
    const [selectedWorkBlocks, setSelectedWB] = useState([]);
    const [selectedWBText, setSelectedWBText] = useState("");
    const [wbDesc, setWBDesc] = useState("");
    const [workblock, setWorkBlock] = useState("");
    const [vehicleDuty, setVehicleDuty] = useState({
        vehicleDutyId: "",
        vehicleDutyCode: "",
        trips: [],
        vehicle: {
            vehicleID: "",
            registration: "",
            vin: "",
            entranceDate: "",
            vehicleTypes: ""
        },
        workBlocks: []

    });
    const [vhDesc, setVDDesc] = useState("");

    const [driverDutyImported, setDriverDutyImported] = useState({
        "driverdutyId": "",
        "workBlocks": []
    });


    async function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setCode("");
        setName("");
        setColor("");
        setWorkBlocks([]);
        fetchData({});
    }

    function openModal() {
        setIsOpen(true);
    }


    async function getDriverById(id) {

        let vehicleDuty = await getTripById(id);
        setVehicleDuty(vehicleDuty);
        setVDWorkBlocks(vehicleDuty.workBlocks);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {

        setVehicleDutys(await getAllVehicleDuties());
        setData(await getDriverDuties({ skip: _skip, limit: _limit }));

    }

    const removeObjectFromArray = (objectArray, objectCode) => {
        let i;

        for (const [key, value] of Object.entries(objectArray)) {
            if (value.workBlockId === objectCode) {
                i = key;
                break;
            }
        }
        const temp = [...objectArray];
        temp.splice(i, 1);
        return temp;
    }


    const addWorkBlock = (e) => {
        if (workblock !== "") {
            setVDWorkBlocks(removeObjectFromArray(workblocksVD, workblock));
            selectedWorkBlocks.push(workblock);
            setSelectedWB([...selectedWorkBlocks]);
            let wbTxt = selectedWBText + wbDesc + "\n";
            setSelectedWBText(wbTxt);
            setWorkBlock("");
            setWBDesc("");
        }
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
            driverDutyCode: code,
            listWorkBlocks: selectedWorkBlocks
        });
        let ok = await submitDriverDuty(json);
        if (ok) {
            alert("Successfully added to DB.");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    const generate = async e => {
        e.preventDefault();

        var url = new URL('http://localhost:9800/generateDriverDuties');
        const fetchData = async () => {
            const info = await fetch(url);

            if (info.ok) {
                await deleteDriverDuties();
                var flag = true;
                const data = await info.json();
                {
                    (data.lista.map(async elem => {
                        const json = JSON.stringify({
                            driverDutyCode: elem.driverdutyId,
                            listWorkBlocks: elem.workBlocks
                        });

                        let ok = await submitDriverDuty(json);
                        if (ok) {
                            flag = true;
                        } else {
                            alert("Error adding the driver duty" + elem.driverdutyId);
                            flag = false;
                        }

                    }))
                    console.log(flag);
                    if (flag) {
                        console.log('HELLO');
                        alert("All driver duties were generated and imported sucessfully");
                    }
                }



            } else {
                alert("Error");
            }

        }
        fetchData();
    }

    return (
        <div>
            <div className="createElem">
                <button data-testid="addVehicleDutyBtnTestID" className="addBtn" onClick={openModal}>Add Driver Duty</button>
                <form onSubmit={generate} method="GET">
                    <div>
                        <input data-testid="bestPathSubmitBtnTestID" name="generate" id="submit" type="submit" value="Generate"
                            style={{
                                float: "left",
                                padding: "10px 60px",
                                backgroundColor: "#373F51",
                                color: "#FFFFFF",
                                marginLeft: "220px"
                            }}></input><br />
                    </div>

                    <br />
                    <br />
                </form>

            </div>

            <table data-testid="vehicleDutyTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>WorkBlocks</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.driverDutyCode}</td>
                            <td>
                                <button data-testid="driverDutyWorkBlocksMoreInfoBtnTestID">
                                    <Link to={`/driverDuty/${elem.driverDutyId}`}>
                                        More Info
                                    </Link>
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
                    <form data-testid="driverDutySubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <label data-testid="driverDutyCodeInputTestID">Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <br />
                        <hr />
                        <br />

                        <label data-testid="driverDutyVDWorkBlocksInputTestID">Choose a Vehicle Duty</label><br />
                        <select name="vehicleDuty" value={vehicleDuty.vehicleDutyId} onChange={e => { getDriverById(e.target.value) }}>
                            <option value="" defaultValue disabled hidden></option>
                            {vehicleDutys.map(elem => (

                                <option value={elem.vehicleDutyId}>{elem.vehicleDutyCode}

                                </option>
                            ))}
                        </select><br />

                        <br />
                        <hr />
                        <br />

                        <label data-testid="driverDutyWorkBlocksInputTestID">Choose WorkBlocks</label><br />
                        <select name="workblock" value={workblock} onChange={e => { setWorkBlock(e.target.value); var index = e.target.selectedIndex; setWBDesc(e.target[index].text) }}>
                            <option value="" defaultValue disabled hidden></option>

                            {workblocksVD.map(elem => (
                                <option value={elem.workBlockId}>{elem.startingTime}- {elem.endingTime}</option>
                            ))}
                        </select>

                        <br />
                        <button type="button" className="addField" onClick={addWorkBlock}>Add</button>
                        <br />
                        <label>WorkBlocks</label>
                        <textarea className="textField" rows="10" name="workblocks" value={selectedWBText} disabled></textarea><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div >
    );

}

export default DriverDuty;
