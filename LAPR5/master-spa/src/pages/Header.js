import React, { useState, useEffect } from "react";
import "./../css/Header.css"
import { Link } from 'react-router-dom';
import Cookies from "universal-cookie";
import Modal from 'react-modal';
import { getUserFromToken } from "../service/AuthenticationService";
import { removeUser } from "../service/UserService";
import { BiX } from 'react-icons/bi';
const cookies = new Cookies();

function Header() {

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

    function handleClick(e) {
        openModal();
    }

    async function deleteAccount() {
        let res = await removeUser(user.email);
        if (res) {
            alert("Successfully deleted your account.");
            logout();
        }
    }

    function logout() {
        cookies.remove("jwtToken");
        window.location.reload();
    }

    const [modalIsOpen, setIsOpen] = useState(false);

    const [user, setUser] = useState({email: "", username: "", name: "", birthdate: ""});

    function closeModal() {
        setIsOpen(false);
    }

    async function afterOpenModal() {
        //setAvailableNodes(await getAllNodes());
        setUser(await getUserFromToken(cookies.get("jwtToken")));
        openModal();
    }

    function openModal() {
        setIsOpen(true);
    }

    return (
        <div className="header">
            <div>
                <h1>OPT SPA</h1>
                {cookies.get("jwtToken") === undefined ?
                <Link className="loginHeader" to="/login">Login</Link> : <a className="loginHeader" href="#" onClick={handleClick}>Your Profile</a>
            }
            </div>
            <Modal
                ariaHideApp={false}
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customModalStyle}
                contentLabel="Example Modal">
                <div className="form">
                    <h1>Your profile<span className="clickableSpan closeCross" onClick={closeModal}><BiX /></span></h1>
                    <br />
                    <p>Email: {user.email}</p>
                    <p>Username: {user.username}</p>
                    <p>Name: {user.name}</p>
                    <p>Birthday: {user.birthDate}</p>
                    <br/>
                    <button className="profileBtn2" onClick={logout}>Logout</button>
                    <button className="profileBtn1" onClick={deleteAccount}>Delete account</button>
                </div>
            </Modal>
        </div>
    );
}

export default Header;