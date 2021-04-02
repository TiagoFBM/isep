import React, { useState, useEffect } from "react";
import "./../css/Node.css";
import { BrowserRoute as Router, Switch, Route } from "react-router-dom";
import config from "../config";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX, BiCheck } from 'react-icons/bi';
import { getNodes, getNodeCount, submitNode, getAllNodes } from "../service/NodeService";
import { Link } from "react-router-dom";

function Node() {

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
    const [latitude, setLatitude] = useState("");
    const [longitude, setLongitude] = useState("");
    const [name, setName] = useState("");
    const [shortName, setShortName] = useState("");
    const [isDepot, setIsDepot] = useState(false);
    const [isReliefPoint, setIsReliefPoint] = useState(false);
    const [node, setNode] = useState("");
    const [duration, setDuration] = useState("");
    const [crewTravelTimes, setCrewTravelTimes] = useState([]);
    const [crewTravelTimesText, setCrewTravelTimesText] = useState("");


    const [availableNodes, setAvailableNodes] = useState([]);


    async function afterOpenModal() {
        setAvailableNodes(await getAllNodes());
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setCode("");
        setLatitude("");
        setLongitude("");
        setName("");
        setShortName("");
        setIsDepot(false);
        setIsReliefPoint(false);
        setNode("");
        setDuration("");
        setCrewTravelTimes([]);
        setCrewTravelTimesText("");
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getNodes({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getNodeCount());
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
        const json = JSON.stringify({ code: code, latitude: latitude, longitude: longitude, name: name, shortName: shortName, isDepot: isDepot, isReliefPoint: isReliefPoint, crewTravelTimes: crewTravelTimes });
        let ok = await submitNode(json);
        if (ok) {
            alert("Successfully added to DB.");
            clearModalFields();
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    const addCrewTravelTime = (e) => {
        if (duration >= 0 || duration !== "") {
            let nodeID = node === "" ? code : node;
            if (nodeID !== "") {
                const json = { node: nodeID, duration: duration };
                crewTravelTimes.push(json);
                let segmText = crewTravelTimesText + `${nodeID} -> Duration = ${duration} seconds\n`;
                setCrewTravelTimes([...crewTravelTimes]);
                setCrewTravelTimesText(segmText);
                setNode("");
                setDuration("");
            }
        }
    }

    return (
        <div>
            <div className="createElem">
                <button data-testid="nodeModalOpenBtnTestID" className="addBtn" onClick={openModal}>Add Node</button>
            </div>
            <table data-testid="nodeTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Name</th>
                        <th>ShortName</th>
                        <th>IsDepot</th>
                        <th>IsReliefPoint</th>
                        <th>Crew Travel Times</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.code}</td>
                            <td>{elem.latitude}</td>
                            <td>{elem.longitude}</td>
                            <td>{elem.name}</td>
                            <td>{elem.shortName}</td>
                            <td>{elem.isDepot ? <BiCheck /> : <BiX />}</td>
                            <td>{elem.isReliefPoint ? <BiCheck /> : <BiX />}</td>
                            <td><Link to={`/node/${elem.code}`}>Crew Travel Times</Link></td>

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
                {(skip / limit) + 1}
                <span className="clickableSpan" onClick={nextPage}><BiChevronRight /></span>
                <span className="clickableSpan" onClick={lastPage}><BiLastPage /></span>
            </div>
            <Modal
                ariaHideApp={false}
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customModalStyle}
                //crew travel times meter os outros elementos
                contentLabel="Example Modal">
                <div className="form">
                    <span className="clickableSpan closeCross" onClick={closeModal}><BiX /></span><br />
                    <form onSubmit={handleSubmit} method="POST">
                        <label data-testid="nodeCodeTestID" >Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <label>Latitude</label><br />
                        <input className="textField" type="number" name="latitude" value={latitude} onChange={e => setLatitude(e.target.value)}></input><br />

                        <label>Longitude</label><br />
                        <input className="textField" type="number" name="longitude" value={longitude} onChange={e => setLongitude(e.target.value)}></input><br />

                        <label>Name</label><br />
                        <input className="textField" type="text" name="name" value={name} onChange={e => setName(e.target.value)}></input><br />

                        <label>Short Name</label><br />
                        <input className="textField" type="text" name="shortName" value={shortName} onChange={e => setShortName(e.target.value)}></input><br />

                        <label>Is Depot?</label><br />
                        <input type="checkbox" name="isDepot" value={isDepot} onChange={e => setIsDepot(e.target.checked)}></input><br />

                        <label>Is Relief Point?</label><br />
                        <input type="checkbox" name="isReliefPoint" value={isReliefPoint} onChange={e => setIsReliefPoint(e.target.checked)}></input><br />

                        <hr />

                        <label>Node</label><br />
                        <select name="node" value={node} onChange={e => { setNode(e.target.value);}}>
                            <option value="" defaultValue>Self</option>
                            {availableNodes.map(elem => (
                                <option value={elem.code}>{elem.shortName}</option>
                            ))}
                        </select><br />

                        <label>Duration</label><br />
                        <input className="textField" type="number" name="duration" value={duration} onChange={e => setDuration(e.target.value)}></input><br />

                        <br /><button type="button" className="addField" onClick={addCrewTravelTime}>Add</button>

                        <label>Crew Travel Times</label>
                        <textarea className="textField" rows="5" name="crewTravelTimes" value={crewTravelTimesText} disabled></textarea><br />


                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );
}

export default Node;