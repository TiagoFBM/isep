import React, {useState, useEffect} from "react";
import "./../css/Path.css";
import { BiX, BiCheck } from 'react-icons/bi';
import { getNodeByCode } from "../service/NodeService";

function SpecificNode({ match }) {

    const [node, setNode] = useState({crewTravelTimes:[]});


    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        let Node = await getNodeByCode(match.params.code);
        setNode(Node);
    }

    return (
        <div>
            <table className="content-table"> 
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Name</th>
                        <th>Short Name</th>
                        <th>Is Depot?</th>
                        <th>Is Relief Point?</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{node.code}</td>
                        <td>{node.latitude}</td>
                        <td>{node.longitude}</td>
                        <td>{node.name}</td>
                        <td>{node.shortName}</td>
                        <td>{node.isDepot ? <BiCheck /> : <BiX />}</td>
                        <td>{node.isReliefPoint ? <BiCheck /> : <BiX />}</td>
                    </tr>
                </tbody>
            </table>
            <br />
            <table className="content-table">
                <thead>
                    <tr>
                        <th>Node</th>
                        <th>Duration</th>
                    </tr>
                </thead>
                <tbody>
                    {node.crewTravelTimes.map(elem => (
                        <tr>
                            <td>{elem.node}</td>
                            <td>{elem.duration}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default SpecificNode;