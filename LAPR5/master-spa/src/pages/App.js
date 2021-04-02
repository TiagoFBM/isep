import React from "react";
import "./../css/App.css"
import Header from "./Header";
import Content from "./Content";
import NavBar from "../components/NavBar";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function App() {
    return (
        <div>
            <Router>
                <NavBar/>
                <div className="main">
                    <Header />
                    <Content />
                </div>
            </Router>
        </div>
    );
}

export default App;