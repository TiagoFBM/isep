import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getTripByLineCode } from "./../../service/TripService";
import "./../../css/TripOfLine.css";

function TripOfLine({ match }) {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    var tst = await getTripByLineCode(match.params.lineId);
    console.log(tst);

    setData(await getTripByLineCode(match.params.lineId));
  };

  return (
    <div>
      <button data-testid="TripOfLineTitleTestID" className="TripOfLineTitle">
        <Link to={`/line/${match.params.lineId}`}>{match.params.lineId}</Link>
      </button>
      <br />
      <hr />
      <br />

      <table data-testid="TripOfLineTableTestID" className="content-table">
        <thead>
          <tr>
            <th>Path</th>
            <th>Depart Time</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          {data.map((elem) => (
            <tr>
              <td>
                <button data-testid="TripOfLineBtnTestID">
                  <Link to={`/path/${elem.pathID.id}`}>{elem.pathID.id}</Link>
                </button>
              </td>

              <td>{elem.tripDepartureTime}</td>

              <td>
                <button data-testid="tripMoreInfoBtnTestID">
                  <Link to={`/trip/${elem.tripId}`}>More info</Link>
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TripOfLine;
