import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX, } from "react-icons/bi";
import { Link } from "react-router-dom";
import { getVehicles, getVehicleCount, submitVehicle } from "../../service/VehicleService";
import { getAllVehicleTypes } from "../../service/VehicleTypeService"


function Vehicle() {
    const customModalStyle = {
        content: {
            top: '45%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }

    };

    const [data, setData] = useState([]);
    const [numberOfElems, setNumberOfElems] = useState(0);

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [skip, setSkip] = useState(0);
    const [limit, setLimit] = useState(10);

    const [registration, setRegistration] = useState("");
    const [vin, setVin] = useState("");
    const [entranceDate, setEntranceDate] = useState("");
    const [vehicleType, setVehicleType] = useState("");
    const [vehicleTypes, setVehicleTypes] = useState([]);

    async function afterOpenModal() { }

    function closeModal() {
        setModalIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setRegistration("");
        setVin("");
        setEntranceDate("");
        setVehicleType("");
    }

    function openModal() {
        setModalIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getVehicles({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getVehicleCount());
        setVehicleTypes(await getAllVehicleTypes());
        console.log(data);
    }


    const firstPage = async () => {
        var newSkip = 0;
        await setSkip(newSkip);
        fetchData({ _skip: newSkip, _limit: limit });
    };

    const nextPage = async () => {
        var newSkip = skip + limit;
        if (newSkip < numberOfElems) {
            setSkip(newSkip);
            await fetchData({ _skip: newSkip, _limit: limit });
        }
    };

    const previousPage = async () => {
        var newSkip = skip - limit;
        if (newSkip >= 0) {
            setSkip(newSkip);
            await fetchData({ _skip: newSkip, _limit: limit });
        }
    };

    const lastPage = async () => {
        var newSkip = Math.floor(numberOfElems / limit) * limit;
        await setSkip(newSkip);
        fetchData({ _skip: newSkip, _limit: limit });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        let splitEntranceDate = entranceDate.split("-");
        let newEntranceDate = splitEntranceDate[2] + "/" + splitEntranceDate[1] + "/" + splitEntranceDate[0];

        const json = JSON.stringify({
            registration: registration,
            vin: vin,
            entranceDate: newEntranceDate,
            vehicleType: vehicleType.toString()
        });
        let ok = await submitVehicle(json);

        if (ok) {
            alert("Successfully added to DB.");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    };


    return (

        <div>
            <div className="createElem">
                <button data-testid="addVehicleBtnTestID" className="addBtn" onClick={openModal}>
                    Add Vehicle
                </button>
            </div>
            <table data-testid="VehicleTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Registration</th>
                        <th>VIN</th>
                        <th>Entrance Date</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.registration}</td>
                            <td>{elem.vin}</td>
                            <td>{elem.entranceDate}</td>
                            <td>{elem.vehicleTypes}</td>
                            <td className="action">
                                <button>Update</button>
                                <button>Delete</button>
                            </td>

                        </tr>

                    ))}

                </tbody>
            </table>

            <div className="pagination">
                <span className="clickableSpan" onClick={firstPage}>
                    <BiFirstPage />
                </span>
                <span className="clickableSpan" onClick={previousPage}>
                    <BiChevronLeft />
                </span>
                {skip / limit + 1}
                <span className="clickableSpan" onClick={nextPage}>
                    <BiChevronRight />
                </span>
                <span className="clickableSpan" onClick={lastPage}>
                    <BiLastPage />
                </span>
            </div>

            <Modal
                ariaHideApp={false}
                wariaHideApp={false}
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customModalStyle}
                contentLabel="Example Modal"
            >

                <div className="form">
                    <span className="clickableSpan closeCross" onClick={closeModal}>
                        <BiX />
                    </span>
                    <br />
                    <form data-testid="VehicleSubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <h3>Create a new Vehicle</h3>
                        <hr />
                        <label>Registration: </label><br />
                        <input type="text" name="registration" value={registration} onChange={(e) => setRegistration(e.target.value)}></input><br />

                        <label>Vehicle Identification Number: </label><br />
                        <input type="text" name="vin" value={vin} onChange={(e) => setVin(e.target.value)}></input><br />

                        <hr />
                        <label>Entrance Date: </label><br />
                        <input type="date" name="entranceDate" value={entranceDate} onChange={(e) => setEntranceDate(e.target.value)}></input><br />

                        <hr />
                        <label>Vehicle Type: </label>

                        <select name="vehicleType" value={vehicleType} onChange={e => { setVehicleType(e.target.value); }}>
                            <option value="" defaultValue disabled hidden></option>
                            {vehicleTypes.map(elem => (
                                <option value={elem.code}>{elem.code}</option>
                            ))}
                        </select><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>

            </Modal>

        </div >
    );
}


export default Vehicle;