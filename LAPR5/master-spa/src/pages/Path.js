import React, { useState, useEffect } from "react";
import "./../css/Path.css";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX, BiCheck } from 'react-icons/bi';
import { getPaths, getPathCount, submitPath } from "../service/PathService";
import { getAllNodes } from "../service/NodeService";
import { Link } from "react-router-dom";

function Path() {

    const customModalStyle = {
        content: {
            top: '30%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };

    const [data, setData] = useState([]);
    const [numberOfElems, setNumberOfElems] = useState(0);

    const [nodes, setNodes] = useState([]);

    const [modalIsOpen, setIsOpen] = useState(false);

    const [skip, setSkip] = useState(0);
    const [limit, setLimit] = useState(10);

    const [code, setCode] = useState("");
    const [isEmpty, setIsEmpty] = useState(false);
    const [travelTime, setTravelTime] = useState(0);
    const [distance, setDistance] = useState(0);
    const [firstNode, setFirstNode] = useState("");
    const [secondNode, setSecondNode] = useState("");
    const [segmentText, setSegmentText] = useState("");
    const [segments, setSegments] = useState([]);

    async function afterOpenModal() {
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setCode("");
        setIsEmpty(false);
        setFirstNode("");
        setSecondNode("");
        setDistance(0);
        setTravelTime(0);
        setSegmentText("");
        setSegments([]);
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getPaths({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getPathCount());
        setNodes(await getAllNodes());
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
        const json = JSON.stringify({ code: code, isEmpty: isEmpty, segments: segments });
        let ok = await submitPath(json);
        if (ok) {
            alert("Successfully added to DB.");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    const addSegment = (e) => {
        if (travelTime >= 0 && distance >= 0 && firstNode !== "" && secondNode !== "") {
            const json = { firstNodeID: firstNode, secondNodeID: secondNode, travelTimeBetweenNodes: travelTime, distanceBetweenNodes: distance };
            segments.push(json);
            let segmText = segmentText + `${firstNode} -> ${secondNode} - Travel time = ${travelTime} | Distance = ${distance}\n`;
            setSegments([...segments]);
            setSegmentText(segmText);
            setFirstNode(secondNode);
            setSecondNode("");
            setDistance(0);
            setTravelTime(0);
        }
    }

    const teste = (codNode) => {
        let i;

        for (const [key, value] of Object.entries(nodes)) {
            if (value.code === codNode) {
                i = key;
                break;
            }
        }
        const temp = [...nodes];
        temp.splice(i, 1);
        return temp;
    }

    return (
        <div>
            <div className="createElem">
                <button data-testid="pathModalOpenBtnTestID" className="addBtn" onClick={openModal}>Add name</button>
            </div>
            <table data-testid="pathTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Empty</th>
                        <th>Segments</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.code}</td>
                            <td>{elem.isEmpty ? <BiCheck /> : <BiX />}</td>
                            <td>
                                <button>
                                    <Link to={`/path/${elem.code}`}>Segments</Link>
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
                contentLabel="Example Modal">
                <div className="form">
                    <span className="clickableSpan closeCross" onClick={closeModal}><BiX /></span><br />
                    <form onSubmit={handleSubmit} method="POST">
                        <label data-testid="pathCodeTestID">Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <label>Is Empty?</label><br />
                        <input type="checkbox" name="isEmpty" value={isEmpty} onChange={e => setIsEmpty(e.target.checked)}></input><br />

                        <hr />

                        <label>First Node</label>
                        <select name="firstNode" value={firstNode} disabled={segments.length > 0 ? "disabled" : ""} onChange={e => { setFirstNode(e.target.value);}}>
                            <option value="" defaultValue disabled hidden></option>
                            {nodes.map(elem => (
                                <option value={elem.code}>{elem.shortName}</option>
                            ))}
                        </select><br />

                        <label>Second Node</label>
                        <select name="secondNode" value={secondNode} onChange={e => { setSecondNode(e.target.value) }}>
                            <option value="" defaultValue disabled hidden></option>
                            {teste(firstNode).map(elem => (
                                <option value={elem.code}>{elem.shortName}</option>
                            ))}
                        </select><br />

                        <label>Travel Time</label><br />
                        <input className="textField" type="number" name="travelTime" value={travelTime} onChange={e => setTravelTime(e.target.value)}></input><br />

                        <label>Distance</label><br />
                        <input className="textField" type="number" name="distance" value={distance} onChange={e => setDistance(e.target.value)}></input><br />
                        <br /><button type="button" className="addField" onClick={addSegment}>Add</button>
                        <label>Description</label>
                        <textarea className="textField" rows="5" name="segments" value={segmentText} disabled></textarea><br />



                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );
}

export default Path;