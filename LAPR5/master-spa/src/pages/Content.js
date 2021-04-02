import React, {lazy, useState, useEffect} from "react";
import "./../css/Content.css";
import { Switch, Route, Redirect } from "react-router-dom";
import config from "../config";
import Login from "./Login";
import Register from "./Register";
import Terms from "./Terms";

import { getAllModules } from "../service/ModuleService";
import { getUserFromToken } from "../service/AuthenticationService";

import Cookies from "universal-cookie";
const cookies = new Cookies();



function Content(props) {

  const importView = filePath =>
  lazy(() =>
    import(`${filePath}`).catch(() =>
      import(`./Map`)
    )
  );
  
  const fetchData = async () => {
    setData(await getAllModules());
    setUser(await getUserFromToken(cookies.get("jwtToken")));
    // let d = new Date();
    // console.log(cookies.get("jwtToken"))
    // d.setTime(d.getTime() + 3600*1000);
    // cookies.set("jwtToken",await login({
    //   "email": "email1@gmail.com",
    //   "password": "password1"
    // }), {path: "/", expires: d});
  };

  const [data, setData] = useState([]);
  const [user, setUser] = useState({isDataAdmin: false});
  
  useEffect(() => {
    (async () => {await fetchData();})()
}, []);

function render() {
  if (cookies.get("jwtToken") === undefined || cookies.get("jwtToken") === null ) {
    if (window.location.pathname === "/register" || window.location.pathname === "/terms") {
      return;
    } else {
      return <Redirect to='/login'/>;
    }
  } else {
    return data.map(elem => {
      console.log(elem.needsAdmin + " | " + user.isDataAdmin);
      if (elem.needsAdmin) {
        if (user.isDataAdmin) {
          return <Route path={elem.urlPath} exact={elem.isExact? "exact" : ""} component={importView(elem.viewPath)}/>
        }
      } else {
        return <Route path={elem.urlPath} exact={elem.isExact? "exact" : ""} component={importView(elem.viewPath)}/>
      }
      return "";
    });
  }
}

  return (
    <Switch>
      <div className="content">
        {render()}
        <Route path="/login" exact component={Login}/>
        <Route path="/register" exact component={Register}/>
        <Route path="/terms" exact component={Terms}/>
      </div>
    </Switch>
  );
}

export default Content;
