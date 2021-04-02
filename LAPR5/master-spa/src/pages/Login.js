import React, { useState, useEffect } from "react";
import "./../css/Login.css";
import Modal from 'react-modal';
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';
import { login } from "../service/AuthenticationService";

import { useHistory, Redirect, Link } from 'react-router-dom';

import Cookies from "universal-cookie";
const cookies = new Cookies();

function Login() {

    const history = useHistory();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
    }, []);



    const handleSubmit = async e => {
        e.preventDefault();
        const json = { email: email, password: password };
        let token = await login(json);
        if (token === undefined || token === null) {
            alert("User not authenticated");
        } else {
            let d = new Date();
            d.setTime(d.getTime() + 3600*1000);
            cookies.set("jwtToken",token, {path: "/", expires: d});
            window.location.reload();
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

                        <label >Password</label><br />
                        <input className="textField" name="password" type="password" value={password} onChange={e => setPassword(e.target.value)}></input><br />

                        <input data-testid="submitModalTestID" id="submit" type="submit" value="Submit"></input>
                        <Link className="createAccount" to="/register">Create account</Link>
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