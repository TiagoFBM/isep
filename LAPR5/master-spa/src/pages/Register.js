import React, { useState, useEffect } from "react";
import "./../css/Login.css";
import Modal from 'react-modal';
import { Link } from 'react-router-dom';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { register } from "../service/AuthenticationService";

import { useHistory, Redirect } from 'react-router-dom';

import Cookies from "universal-cookie";
const cookies = new Cookies();

function Login() {

    const history = useHistory();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [name, setName] = useState("");
    const [acceptedTerms, setAcceptedTerms] = useState(false);

    useEffect(() => {
    }, []);



    const handleSubmit = async e => {
        e.preventDefault();
        const json = { email: email, username: username, password: password, name: name, birthDate: birthDate, acceptedTerms: acceptedTerms, isDataAdmin: false };
        let res = await register(json);
        if (!res) {
            alert("Could not create user");
        } else {
            window.location = "/login";
        }
    }

    function render() {
        if (cookies.get("jwtToken") !== undefined) {
          return <Redirect to='/'/>;
        } else {
          return (<div className="form">
                    <form onSubmit={handleSubmit} method="POST">
                        <label >Email</label><br />
                        <input className="textField" type="email" name="email" value={email} onChange={e => setEmail(e.target.value)}></input><br />

                        <label >Username</label><br />
                        <input className="textField" type="text" name="username" value={username} onChange={e => setUsername(e.target.value)}></input><br />

                        <label >Password</label><br />
                        <input className="textField" name="password" type="password" value={password} onChange={e => setPassword(e.target.value)}></input><br />

                        <label >Name</label><br />
                        <input className="textField" type="text" name="name" value={name} onChange={e => setName(e.target.value)}></input><br />

                        <label >Brith Date</label><br />
                        <input className="textField" type="date" name="birthDate" value={birthDate} onChange={e => setBirthDate(e.target.value)}></input><br />

                        <Link to="/terms"><label className="linkTerms">Terms of Service</label></Link><br />
                        <input type="checkbox" name="acceptedTerms" checked={acceptedTerms ? "checked": ""} onChange={e => setAcceptedTerms(e.target.checked)}></input><br />

                        <input data-testid="submitModalTestID" id="submit" type="submit" value="Submit"></input>
                    </form>
                </div>);
        }
      }

    return (
        <div className="loginForm">
            {render()}
        </div>
    );
}

export default Login;