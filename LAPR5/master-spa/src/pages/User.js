import React, { useState, useEffect } from "react";
import "./../css/Node.css";
import { BrowserRoute as Router, Switch, Route } from "react-router-dom";
import config from "../config";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX, BiCheck } from 'react-icons/bi';
import { getNodes, getNodeCount, submitNode, getAllNodes } from "../service/NodeService";
import { getUsers, updateUser } from "../service/UserService";
import { Link } from "react-router-dom";

function User() {

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

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [name, setName] = useState("");
    const [birthday, setBirthday] = useState("");
    const [isAdmin, setIsAdmin] = useState(false);


    async function afterOpenModal() {
        //setAvailableNodes(await getAllNodes());
    }

    function closeModal() {
        setIsOpen(false);
        clearModalFields();
    }

    function clearModalFields() {
        setEmail("");
        setUsername("");
        setBirthday("");
        setName("");
        setIsAdmin(false);
    }

    function openModal() {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchData({});
    }, []);

    const fetchData = async ({ _skip = skip, _limit = limit }) => {
        setData(await getUsers({skip: _skip, limit: _limit}));
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
        const json = { email: email, username: username, name: name, birthDate: birthday, isDataAdmin: isAdmin };
        let ok = await updateUser(json);
        if (ok) {
            alert("Successfully updated.");
            clearModalFields();
            fetchData({});
            closeModal();
        } else {
            alert("Error updating.");
        }
    }

    const handleUpdateClick = async e => {
        const currentUser = data[e.target.getAttribute("data-key")];
        console.log(currentUser);
        setEmail(currentUser.email);
        setUsername(currentUser.username);
        setBirthday(currentUser.birthDate);
        setName(currentUser.name);
        setIsAdmin(currentUser.isDataAdmin);
        openModal();
    }

    return (
        <div>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <table data-testid="userTableTestID" className="content-table">
                <thead>
                    <tr>
                        <th>Email</th>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Birthday</th>
                        <th>Is Admin?</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((elem, index) => (
                        <tr>
                            <td>{elem.email}</td>
                            <td>{elem.username}</td>
                            <td>{elem.name}</td>
                            <td>{elem.birthDate}</td>
                            <td>{elem.isDataAdmin ? <BiCheck /> : <BiX />}</td>

                            <td className="action">
                                <button data-key={index + skip} onClick={handleUpdateClick}>Update</button>
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
                        <label data-testid="nodeCodeTestID" >Email</label><br />
                        <input className="textField" type="email" name="email" value={email} onChange={e => setEmail(e.target.value)}></input><br />

                        <label>Username</label><br />
                        <input className="textField" type="text" name="username" value={username} onChange={e => setUsername(e.target.value)}></input><br />

                        <label>Name</label><br />
                        <input className="textField" type="text" name="name" value={name} onChange={e => setName(e.target.value)}></input><br />

                        <label>BirthDay</label><br />
                        <input className="textField" type="date" name="birthDay" value={birthday} onChange={e => setBirthday(e.target.value)}></input><br />

                        <label>Is Admin?</label>
                        <input type="checkbox" name="isAdmin" checked={isAdmin ? "checked" : ""} onChange={e => setIsAdmin(e.target.checked)}></input><br />

                        <input id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>
            </Modal>
        </div>
    );
}

export default User;