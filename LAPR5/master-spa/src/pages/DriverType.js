import React, { useState, useEffect } from "react";
import "./../css/DriverType.css";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { getDriverTypes, getDriverTypeCount, submitDriverType } from "../service/DriverTypeService";

function DriverType() {

    const customModalStyle = {
        content: {
            top: '15%',
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
        setData(await getDriverTypes({ skip: _skip, limit: _limit }));
        setNumberOfElems(await getDriverTypeCount());
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
        const json = JSON.stringify({ code: code, description: description });
        let ok = await submitDriverType(json);
        if (ok) {
            alert("Successfully added to DB.");
            setCode("");
            setDescription("");
            fetchData({});
            closeModal();
        } else {
            alert("Error adding to DB.");
        }
    }

    return (
        <div>
            <div className="createElem">
                <button data-testid="addDriverTypeBtnID" className="addBtn" onClick={openModal}>Add Driver Type</button>
            </div>
            <table data-testid="driverTypeTableID" className="content-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(elem => (
                        <tr>
                            <td>{elem.code}</td>
                            <td>{elem.description}</td>
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
                        <label >Code</label><br />
                        <input className="textField" type="text" name="code" value={code} onChange={e => setCode(e.target.value)}></input><br />

                        <label >Description</label><br />
                        <textarea className="textField" rows="4" name="description" value={description} onChange={e => setDescription(e.target.value)}></textarea><br />

                        <input data-testid="submitModalTestID" id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );
}

export default DriverType;