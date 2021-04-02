import React, { useState, useEffect } from "react";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { Link } from "react-router-dom";
import { getAllPaths } from "../../service/PathService";
import { getAllDriverTypes } from "../../service/DriverTypeService";
import { getAllVehicleTypes } from "../../service/VehicleTypeService";
import { getLines, getLineCount, submitLine } from "../../service/LineService";

function Line() {
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
    const [description, setDescription] = useState("");
    const [lineColor, setLineColor] = useState("");

    const [paths, setPaths] = useState([]);

    const [linePaths, setLinePaths] = useState([]);
    const [linePathsText, setLinePathsText] = useState("");

    const [driverTypes, setDriverTypes] = useState([]);
    const [driverTypeRestriction, setDriverTypeRestriction] = useState("");
    const [driverTypesText, setDriverTypesText] = useState("");

    const [vehicleTypes, setVehicleTypes] = useState([]);
    const [vehicleTypeRestriction, setVehicleTypeRestriction] = useState("");
    const [vehicleTypesText, setVehicleTypesText] = useState("");

    const [path, setPath] = useState("");
    const [orientation, setOrientation] = useState("");
    const [allowedDriverTypes, setAllowedDriverTypes] = useState([]);
    const [deniedDriverTypes, setDenniedDriverTypes] = useState([]);
    const [allowedVehicleTypes, setAllowedVehicleTypes] = useState([]);
    const [deniedVehicleTypes, setDeniedVehicleTypes] = useState([]);

    const [driverType, setDriverType] = useState("");
    const [vehicleType, setVehicleType] = useState("");

    async function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setCode("");
        setDescription("");
        setLineColor("");
        setLinePaths([]);
        setLinePathsText("");
        setPath("");
        setOrientation("");
        setDriverType("");
        setVehicleType("");
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getLines({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getLineCount());
        setPaths(await getAllPaths());
        setDriverTypes(await getAllDriverTypes());
        setVehicleTypes(await getAllVehicleTypes());
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
            code: code, description: description, linePaths: linePaths,
            allowedVehicles: allowedVehicleTypes, deniedVehicles: deniedVehicleTypes,
            allowedDrivers: allowedDriverTypes, deniedDrivers: deniedDriverTypes,
            lineColor: lineColor
        });
        let ok = await submitLine(json);
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
            if (value.code === objectCode) {
                i = key;
                break;
            }
        }
        const temp = [...objectArray];
        temp.splice(i, 1);
        return temp;
    }


    const addLinePath = (e) => {
        if (path !== "" && orientation !== "") {
            const json = { path: path, orientation: orientation };
            linePaths.push(json);
            let linePathsTxt = linePathsText + `${path} - Orientation: ${orientation}\n`;
            setLinePaths([...linePaths]);
            setLinePathsText(linePathsTxt);
            setPath("");
            setOrientation("");
        }
    }

    const addVehicleType = (e) => {
        if (vehicleType !== "" && vehicleTypeRestriction !== "") {
            setVehicleTypes(removeObjectFromArray(vehicleTypes, vehicleType));
            if (vehicleTypeRestriction === "Allow") {
                allowedVehicleTypes.push(vehicleType);
            } else {
                deniedVehicleTypes.push(vehicleType);
            }
            let vehicleTypeTxt = vehicleTypesText + `${vehicleType} - Restriction: ${vehicleTypeRestriction}\n`;
            setAllowedVehicleTypes([...allowedVehicleTypes]);
            setDeniedVehicleTypes([...deniedVehicleTypes]);
            setVehicleTypesText(vehicleTypeTxt);
            setVehicleType("");
            setVehicleTypeRestriction("");
        }
    }

    const addDriverType = (e) => {
        if (driverType !== "" && driverTypeRestriction !== "") {
            setDriverTypes(removeObjectFromArray(driverTypes, driverType));
            if (driverTypeRestriction === "Allow") {
                allowedDriverTypes.push(driverType);
            } else {
                deniedDriverTypes.push(driverType);
            }
            let driverTypeTxt = driverTypesText + `${driverType} - Restriction: ${driverTypeRestriction}\n`;
            setAllowedDriverTypes([...allowedDriverTypes]);
            setDenniedDriverTypes([...deniedDriverTypes]);
            setDriverTypesText(driverTypeTxt);
            setDriverType("");
            setDriverTypeRestriction("");
        }
    }


    return (
        <div>
            <div className="createElem">
                <button data-testid="addLineBtnTestID" className="addBtn" onClick={openModal}>Add Line</button>
            </div>
            <table data-testid="lineTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Color</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.code}</td>
                            <td>{elem.description}</td>
                            <td>{elem.lineColor}</td>
                            <td>
                                <button data-testid="lineMoreInfoBtnTestID">
                                    <Link to={`/line/${elem.code}`}>More Info</Link>
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
                    <form data-testid="lineSubmitBtnTestID" onSubmit={handleSubmit} method="POST">
                        <label data-testid="lineCodeInputTestID">Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <label data-testid="lineDescriptionInputTestID">Description</label><br />
                        <textarea className="textField" rows="4" name="description" value={description} onChange={e => setDescription(e.target.value)}></textarea><br />

                        <br />
                        <hr />
                        <br />

                        <label>Path </label>
                        <select name="path" value={path} onChange={e => { setPath(e.target.value); }}>
                            <option value="" defaultValue disabled hidden></option>
                            {paths.map(elem => (
                                <option value={elem.code}>{elem.code}</option>
                            ))}
                        </select><br />

                        <br />

                        <label>Orientation</label><br />
                        <label>Go </label>
                        <input type="radio" name="orientation" checked={orientation === "Go" ? "checked" : ""} value="Go" onChange={e => setOrientation(e.target.value)}></input>
                        <label style={{
                            marginLeft: "20px"
                        }}>Return </label>
                        <input type="radio" id="orientationReturn" name="orientation" checked={orientation === "Return" ? "checked" : ""} value="Return" onChange={e => setOrientation(e.target.value)}></input><br />

                        <br /><button type="button" className="addField" onClick={addLinePath}>Add</button>
                        <label>Paths</label>
                        <textarea className="textField" rows="5" name="paths" value={linePathsText} disabled></textarea><br />


                        <br />
                        <hr />
                        <br />

                        <label>Vehicle Type</label><br />
                        <select name="vehicleType" value={vehicleType} onChange={e => { setVehicleType(e.target.value); }}>
                            <option value="" defaultValue disabled hidden></option>
                            {vehicleTypes.map(elem => (
                                <option value={elem.code}>{elem.code}</option>
                            ))}
                        </select><br />
                        <br />
                        <label>Allow </label>
                        <input type="radio" name="vehicleType" checked={vehicleTypeRestriction === "Allow" ? "checked" : ""} value="Allow" onChange={e => setVehicleTypeRestriction(e.target.value)}></input>
                        <label style={{
                            marginLeft: "20px"
                        }}>Deny </label>
                        <input type="radio" name="vehicleType" checked={vehicleTypeRestriction === "Deny" ? "checked" : ""} value="Deny" onChange={e => setVehicleTypeRestriction(e.target.value)}></input>

                        <br /><button type="button" className="addField" onClick={addVehicleType}>Add</button>
                        <label>vehicleTypes</label>
                        <textarea className="textField" rows="5" name="vehicleTypes" value={vehicleTypesText} disabled></textarea><br />

                        <br />
                        <hr />
                        <br />

                        <label >Driver Type</label><br />
                        <select name="driverType" value={driverType} onChange={e => { setDriverType(e.target.value); }}>
                            <option value="" defaultValue disabled hidden></option>
                            {driverTypes.map(elem => (
                                <option value={elem.code}>{elem.code}</option>
                            ))}
                        </select><br />
                        <br />
                        <label >Allow </label>
                        <input type="radio" name="driverType" checked={driverTypeRestriction === "Allow" ? "checked" : ""} value="Allow" onChange={e => setDriverTypeRestriction(e.target.value)}></input>
                        <label style={{
                            marginLeft: "20px"
                        }}>Deny </label>
                        <input type="radio" name="driverType" checked={driverTypeRestriction === "Deny" ? "checked" : ""} value="Deny" onChange={e => setDriverTypeRestriction(e.target.value)}></input>

                        <br /><button type="button" className="addField" onClick={addDriverType}>Add</button>
                        <label >driverTypes</label>
                        <textarea className="textField" rows="5" name="driverTypes" value={driverTypesText} disabled></textarea><br />

                        <label >Color (RGB): </label><br />
                        <input className="textField" type="text" name="lineColor" value={lineColor} onChange={e => setLineColor(e.target.value)}></input><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );


}

export default Line;