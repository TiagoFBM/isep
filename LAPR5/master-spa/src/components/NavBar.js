import React, {useEffect, useState} from "react";
import "./../css/NavBar.css";
import { Link } from "react-router-dom";

import config from "../config";
import { getUserFromToken } from "../service/AuthenticationService";
import { getAllModules } from "../service/ModuleService";

import Cookies from "universal-cookie";
const cookies = new Cookies();



function NavBar() {

    const [data, setData] = useState([]);
    const [user, setUser] = useState({isDataAdmin: false});

    const fetchData = async () => {
        setUser(await getUserFromToken(cookies.get("jwtToken")));
        setData(await getAllModules());
    };

    useEffect(() => {
        (async () => {await fetchData();})()
    }, []);

    return (
        <div className="nav">
            <div className="linkDiv">
                <ul className="linkUL">
                    <Link className="navLink" to="/login"><li className="link">Home</li></Link>
                    {cookies.get("jwtToken") !== undefined && data.map(elem => {
                        if (elem.isNavItem) {
                            if (elem.needsAdmin) {
                                if (user.isDataAdmin) {
                                    return <Link className="navLink" to={elem.urlPath}><li className="link">{elem.name}</li></Link>
                                }
                            } else {
                                return <Link className="navLink" to={elem.urlPath}><li className="link">{elem.name}</li></Link>
                            }
                        }
                        return "";
                    })}
                </ul>
            </div>
        </div>
    );
}

export default NavBar;