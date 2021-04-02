import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { getVehicleTypes, getVehicleTypeCount, submitVehicleType } from "../service/VehicleTypeService";

function VehicleType() {
    const customModalStyle = {
        content: {
            top: '35%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };

    const [data, setData] = useState([]);
    const [numberOfElems, setNumberOfElems] = useState(0);

    const [modalIsOpen, setIsOpen] = useState(false);

    const [skip, setSkip] = useState(0);
    const [limit, setLimit] = useState(10);

    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");
    const [autonomy, setAutonomy] = useState("");
    const [fuelType, setFuelType] = useState("");
    const [costPerKilometer, setCostPerKilometer] = useState("");
    const [averageConsuption, setAverageConsuption] = useState("");
    const [averageSpeed, setAverageSpeed] = useState("");

    function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {

        setData(await getVehicleTypes({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getVehicleTypeCount());
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
        const json = JSON.stringify({ code: code, description: description, autonomy: autonomy, fuelType: fuelType, costPerKilometer: costPerKilometer, averageConsuption: averageConsuption, averageSpeed: averageSpeed });
        let ok = await submitVehicleType(json);

        if (ok) {
            alert("Successfully added to DB.");
            setCode("");
            setDescription("");
            setAutonomy("");
            setFuelType("");
            setCostPerKilometer("");
            setAverageConsuption("");
            setAverageSpeed("");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }
    return (
        <div>
            <div className="createElem">
                <button data-testid="vehicleTypeModalOpenBtnTestID" className="addBtn" onClick={openModal}>Add Vehicle Type</button>
            </div>

            <table data-testid="vehicleTypeTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Autonomy</th>
                        <th>Fuel Type</th>
                        <th>Cost Per Kilometer</th>
                        <th>Average Consuption</th>
                        <th>Average Speed</th>
                    </tr>
                </thead>
                <tbody>

                    {data.map(elem => (
                        <tr>
                            <td>{elem.code}</td>
                            <td>{elem.description}</td>
                            <td>{elem.autonomy}</td>
                            <td>{elem.fuelType}</td>
                            <td>{elem.costPerKilometer}</td>
                            <td>{elem.averageConsuption}</td>
                            <td>{elem.averageSpeed}</td>
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
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customModalStyle}
                contentLabel="Example Modal">
                <div className="form">
                    <span className="clickableSpan closeCross" onClick={closeModal}><BiX /></span><br />
                    <form onSubmit={handleSubmit} method="POST">
                        <label data-testid="vehicleTypeCodeTestID">Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <label>Description</label><br />
                        <textarea className="textField" rows="4" name="description" value={description} onChange={e => setDescription(e.target.value)}></textarea><br />

                        <label>Autonomy</label><br />
                        <input className="textField" type="number" name="autonomy" value={autonomy} onChange={e => setAutonomy(e.target.value)}></input><br />

                        <label>Choose the Fuel Type</label><br />
                        {/* <select>
                            <option name="Diesel" value={fuelType} onChange={e => setFuelType(e.target.value)}>Diesel</option>
                            <option name="Gasolina" value={fuelType} onChange={e => setFuelType(e.target.value)}>Gasolina</option>
                            <option name="Elétrico" value={fuelType} onChange={e => setFuelType(e.target.value)}>Elétrico</option>
                            <option name="GPL" value={fuelType} onChange={e => setFuelType(e.target.value)}>GPL</option>
                        </select><br /> */}
                        <input className="textField" type="text" name="fuelType" value={fuelType} onChange={e => setFuelType(e.target.value)}></input><br />

                        <label>Cost Per Kilometer</label><br />
                        <input className="textField" type="number" name="costPerKilometer" value={costPerKilometer} onChange={e => setCostPerKilometer(e.target.value)}></input><br />

                        <label>Average Consumption</label><br />
                        <input className="textField" type="number" name="averageConsuption" value={averageConsuption} onChange={e => setAverageConsuption(e.target.value)}></input><br />

                        <label>Average Speed</label><br />
                        <input className="textField" type="number" name="averageSpeed" value={averageSpeed} onChange={e => setAverageSpeed(e.target.value)}></input><br />


                        <input id="submit" name="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );


}

export default VehicleType;