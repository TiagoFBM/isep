import React, {useState, useEffect} from "react";
import "./../css/Path.css";
import { BiX, BiCheck } from 'react-icons/bi';
import { getPathByCode } from "../service/PathService";

function SpecificPath({ match }) {

    const [path, setPath] = useState({code:"", isEmpty: false, segments:[]});

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        let Path = await getPathByCode(match.params.code);
        setPath(Path);
    }

    return (
        <div>
            <table className="content-table"> 
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Is Empty</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{path.code}</td>
                        <td>{path.isEmpty ? <BiCheck /> : <BiX />}</td>
                    </tr>
                </tbody>
            </table>
            <br />
            <table className="content-table">
                <thead>
                    <tr>
                        <th>First Node</th>
                        <th>Second Node</th>
                        <th>Travel Time</th>
                        <th>Distance</th>
                    </tr>
                </thead>
                <tbody>
                    {path.segments.map(elem => (
                        <tr>
                            <td>{elem.firstNodeID.shortName}</td>
                            <td>{elem.secondNodeID.shortName}</td>
                            <td>{elem.travelTimeBetweenNodes}</td>
                            <td>{elem.distanceBetweenNodes}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default SpecificPath;