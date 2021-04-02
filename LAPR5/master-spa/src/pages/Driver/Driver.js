import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX, } from "react-icons/bi";
import { Link } from "react-router-dom";
import { getAllDrivers, getAllDriversCount, submitDriver } from "../../service/DriverService";
import { getAllDriverTypes } from "../../service/DriverTypeService"



function Driver() {
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

    const [mecanographicNumber, setMecanographicNumber] = useState("");
    const [driverName, setDriverName] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [citizenCardNumber, setCitizenCardNumber] = useState(0);
    const [driverNIF, setDriverNIF] = useState(0);
    const [entranceDate, setEntranceDate] = useState("");
    const [departureDate, setDepartureDate] = useState("");
    const [numberDriverLicense, setNumberDriverLicense] = useState("");
    const [dateDriverLicense, setDateDriverLicense] = useState("");
    const [driverType, setDriverType] = useState("");
    const [driverTypes, setDriverTypes] = useState([]);
    const [driverTypesSelected, setDriverTypesSelected] = useState([]);
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");
    const [driverTypeText, setDriverTypeText] = useState("");

    async function afterOpenModal() { }

    function closeModal() {
        setModalIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setMecanographicNumber("");
        setDriverName("");
        setBirthDate("");
        setCitizenCardNumber("");
        setDriverNIF("");
        setEntranceDate("");
        setDepartureDate("");
        setNumberDriverLicense("");
        setDateDriverLicense("");
        setDriverType("");
    }

    function openModal() {
        setModalIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const removeObjectFromArray = (objectArray, objectCode) => {
        let i;

        for (const [key, value] of Object.entries(objectArray)) {
            if (value.code === objectCode) {
                i = key;
                break;
            }
        }
        const temp = [...objectArray];
        temp.splice(i, 1);
        return temp;
    }

    const addDriverType = (e) => {

        if (driverType !== "") {
            setDriverTypes(removeObjectFromArray(driverTypes, driverType));
            driverTypesSelected.push(`${driverType}`);
            let driverTypeTxt = driverTypeText + `${driverType}` + '\n';
            setDriverTypesSelected([...driverTypesSelected]);
            setDriverTypeText(driverTypeTxt);
        }
    }

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getAllDrivers({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getAllDriversCount());
        setDriverTypes(await getAllDriverTypes());
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

        let splitBirthDate = birthDate.split("-");
        let newBirthDate = splitBirthDate[2] + "/" + splitBirthDate[1] + "/" + splitBirthDate[0];

        let splitEntranceDate = entranceDate.split("-");
        let newEntranceDate = splitEntranceDate[2] + "/" + splitEntranceDate[1] + "/" + splitEntranceDate[0];

        let splitDepartureDate = departureDate.split("-");
        let newDepartureDate = splitDepartureDate[2] + "/" + splitDepartureDate[1] + "/" + splitDepartureDate[0];

        let splitExpirationDate = dateDriverLicense.split("-");
        let newExpirationDate = splitExpirationDate[2] + "/" + splitExpirationDate[1] + "/" + splitExpirationDate[0];


        const json = JSON.stringify({
            mecanographicNumber: mecanographicNumber,
            driverName: driverName,
            birthDate: newBirthDate,
            citizenCardNumber: citizenCardNumber,
            driverNIF: driverNIF,
            entranceDate: newEntranceDate,
            departureDate: newDepartureDate,
            numberDriverLicense: numberDriverLicense,
            dateDriverLicense: newExpirationDate,
            driverTypes: driverTypesSelected
        });

        console.log(json);
        let ok = await submitDriver(json);

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
                <button data-testid="addDriverBtnTestID" className="addBtn" onClick={openModal}>
                    Add Driver
                </button>
            </div>
            <table data-testid="DriverTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Mecanographic Number</th>
                        <th>Entrance Date</th>
                        <th>Departure Date</th>
                        <th>More Info</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.mecanographicNumber}</td>
                            <td>{elem.entranceDate}</td>
                            <td>{elem.departureDate}</td>
                            <td>
                                <button data-testid="driverTypeMoreInfoBtnTestID">
                                    <Link to={`/driver/${elem.driverID}`}>More Info</Link>
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
                    <form data-testid="DriverSubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <h3>Create a new Driver</h3>
                        <hr />
                        <label>Mecanographic Number: </label><br />
                        <input type="text" name="mecanographicNumber" value={mecanographicNumber} onChange={(e) => setMecanographicNumber(e.target.value)} required></input><br />

                        <label>Driver Name: </label><br />
                        <input type="text" name="driverName" value={driverName} onChange={(e) => setDriverName(e.target.value)} required></input><br />

                        <label>Birth Date: </label><br />
                        <input type="date" name="birthDate" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} required></input><br />

                        <label>Citizen Card Number: </label><br />
                        <input type="number" name="citizenCardNumber" value={citizenCardNumber} onChange={(e) => setCitizenCardNumber(e.target.value)} required></input> <br />

                        <label>NIF: </label><br />
                        <input type="number" name="driverNIF" value={driverNIF} onChange={(e) => setDriverNIF(e.target.value)} required></input><br />

                        <hr />
                        <label>Entrance Date: </label><br />
                        <input type="date" name="entranceDate" value={entranceDate} onChange={(e) => setEntranceDate(e.target.value)} required></input><br />

                        <label>Departure Date: </label><br />
                        <input type="date" name="departureDate" value={departureDate} onChange={(e) => setDepartureDate(e.target.value)} min={entranceDate} required></input><br />

                        <hr />
                        <label>Number of the Driver Licence: </label><br />
                        <input type="text" name="numberDriverLicense" value={numberDriverLicense} onChange={(e) => setNumberDriverLicense(e.target.value)} placeholder="P-0000000 0" required></input><br />

                        <label>Driver Licence expiration date</label><br />
                        <input type="date" name="dateDriverLicense" value={dateDriverLicense} onChange={(e) => setDateDriverLicense(e.target.value)} required></input><br />

                        <hr />
                        <label>Driver Type: </label>

                        <select name="driverType" value={driverType} onChange={e => { setDriverType(e.target.value); }}>
                            <option value="" defaultValue disabled hidden></option>
                            {driverTypes.map(elem => (
                                <option value={elem.code}>{elem.code}</option>
                            ))}
                        </select><br /><br />

                        <br /><button type="button" className="addField" onClick={addDriverType}>Add</button>

                        <textarea className="textField" rows="5" name="paths" value={driverTypeText} disabled></textarea><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>

            </Modal>

        </div >
    );
}


export default Driver;