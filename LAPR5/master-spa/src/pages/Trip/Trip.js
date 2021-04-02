import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import {
  BiFirstPage,
  BiLastPage,
  BiChevronLeft,
  BiChevronRight,
  BiX,
} from "react-icons/bi";
import { Link } from "react-router-dom";
import {
  getTrips,
  getTripCount,
  submitSimpleTrip,
  submitComplexTrip,
} from "../../service/TripService";
import { getAllLines, getLinePathsByCode } from "../../service/LineService";

function Trip() {
  const customModalStyle = {
    content: {
      top: "45%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      overflow: "scroll",
    },
  };

  const [data, setData] = useState([]);
  const [numberOfElems, setNumberOfElems] = useState(0);

  const [simpleModalIsOpen, setSimpleIsOpen] = useState(false);
  const [complexModalIsOpen, setComplexIsOpen] = useState(false);

  const [skip, setSkip] = useState(0);
  const [limit, setLimit] = useState(10);

  const [lines, setLines] = useState([]);
  const [line, setLine] = useState("");

  const [paths, setPaths] = useState([]);
  const [path, setPath] = useState("");

  const [departTime, setDepartTime] = useState("");

  const [frequency, setFrequency] = useState(0);

  const [numberOfTrips, setNumberOfTrips] = useState(0);

  const [outwardPath, setOutwardPath] = useState("");

  const [returnPath, setReturnPath] = useState("");

  async function afterOpenSimpleModal() {}

  async function afterOpenComplexModal() {}

  function closeSimpleModal() {
    setSimpleIsOpen(false);
    clearSimpleModalFields();
  }

  function clearSimpleModalFields() {
    setLine("");
    setPath("");
    setDepartTime("");
    setPaths([]);
  }

  function closeComplexModal() {
    setComplexIsOpen(false);
    clearComplexModalFields();
  }

  function clearComplexModalFields() {
    setLine("");
    setPaths([]);
    setReturnPath("");
    setOutwardPath("");
    setDepartTime("");
    setFrequency(0);
    setNumberOfTrips(0);
  }

  function openSimpleModal() {
    setSimpleIsOpen(true);
  }

  function openComplexModal() {
    setComplexIsOpen(true);
  }

  useEffect(() => {
    fetchData({});
  }, []);

  const fetchData = async ({ _skip = skip, _limit = limit }) => {
    setData(await getTrips({ skip: _skip, limit: _limit }));
    setNumberOfElems(await getTripCount());
    setLines(await getAllLines());
  };

  const updatePath = async (id) => {
    const linePaths = await getLinePathsByCode(id);
    const tmpPaths = [];

    linePaths.forEach((elem) => tmpPaths.push(elem.path));

    setPaths(tmpPaths);
  };

  const firstPage = async () => {
    var newSkip = 0;
    await setSkip(newSkip);
    fetchData({ _skip: newSkip, _limit: limit });
  };

  const nextPage = async () => {
    var newSkip = skip + limit;
    if (newSkip < numberOfElems) {
      setSkip(newSkip);
      await fetchData({ _skip: newSkip, _limit: limit });
    }
  };

  const previousPage = async () => {
    var newSkip = skip - limit;
    if (newSkip >= 0) {
      setSkip(newSkip);
      await fetchData({ _skip: newSkip, _limit: limit });
    }
  };

  const lastPage = async () => {
    var newSkip = Math.floor(numberOfElems / limit) * limit;
    await setSkip(newSkip);
    fetchData({ _skip: newSkip, _limit: limit });
  };

  const handleSubmitSimple = async (e) => {
    e.preventDefault();
    let newDepartTime;

    if (departTime.split(":").length == 2) {
      newDepartTime = departTime + ":00";
    } else {
      newDepartTime = departTime;
    }

    const json = JSON.stringify({
      lineID: line,
      pathID: path,
      tripDepartureTime: newDepartTime,
    });
    let ok = await submitSimpleTrip(json);

    if (ok) {
      alert("Successfully added to DB.");
      fetchData({});
      closeSimpleModal();
    } else {
      alert("Error adding to DB.");
    }
  };

  const handleSubmitComplex = async (e) => {
    e.preventDefault();
    let newDepartTime;

    if (departTime.split(":").length == 2) {
      newDepartTime = departTime + ":00";
    } else {
      newDepartTime = departTime;
    }

    const json = JSON.stringify({
      lineID: line,
      outwardPathID: outwardPath,
      returnPathID: returnPath,
      tripDepartureTime: newDepartTime,
      frequency: frequency,
      nTrips: numberOfTrips,
    });
    let ok = await submitComplexTrip(json);
    if (ok) {
      alert("Successfully added to DB.");
      fetchData({});
      closeComplexModal();
    } else {
      alert("Error adding to DB.");
    }
  };

  return (
    <div>
      <div className="createElem">
        <button
          data-testid="addComplexTripBtnTestID"
          className="addBtn"
          onClick={openComplexModal}
        >
          Add Trips
        </button>
        <button
          data-testid="addTripBtnTestID"
          className="addBtn"
          onClick={openSimpleModal}
        >
          Add Trip Ad Hoc
        </button>
      </div>

      <table data-testid="TripTableTestID" className="content-table">
        <thead>
          <tr>
            <th>Line</th>
            <th>Path</th>
            <th>Depart Time</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          {data.map((elem) => (
            <tr>
              <td>
                <button data-testid="tripLineBtnTestID">
                  <Link to={`/trip/line/${elem.lineID.id}`}>
                    {elem.lineID.id}
                  </Link>
                </button>
              </td>

              <td>
                <button data-testid="tripPathBtnTestID">
                  <Link to={`/path/${elem.pathID.id}`}>{elem.pathID.id}</Link>
                </button>
              </td>

              <td>{elem.tripDepartureTime}</td>

              <td>
                <button data-testid="tripMoreInfoBtnTestID">
                  <Link to={`/trip/${elem.tripId}`}>More info</Link>
                </button>
              </td>

              <td className="action">
                <button>Update</button>
                <button>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="pagination">
        <span className="clickableSpan" onClick={firstPage}>
          <BiFirstPage />
        </span>
        <span className="clickableSpan" onClick={previousPage}>
          <BiChevronLeft />
        </span>
        {skip / limit + 1}
        <span className="clickableSpan" onClick={nextPage}>
          <BiChevronRight />
        </span>
        <span className="clickableSpan" onClick={lastPage}>
          <BiLastPage />
        </span>
      </div>

      <Modal
        ariaHideApp={false}
        wariaHideApp={false}
        isOpen={simpleModalIsOpen}
        onAfterOpen={afterOpenSimpleModal}
        onRequestClose={closeSimpleModal}
        style={customModalStyle}
        contentLabel="Example Modal"
      >
        <div className="form">
          <span className="clickableSpan closeCross" onClick={closeSimpleModal}>
            <BiX />
          </span>
          <br />
          <form
            data-testid="TripSubmitBtnTestID"
            onSubmit={handleSubmitSimple}
            method="POST"
          >
            <label>Line </label>
            <select
              name="line"
              value={line}
              onChange={(e) => {
                setLine(e.target.value);
                updatePath(e.target.value);
              }}
            >
              <option value="" defaultValue disabled hidden></option>
              {lines.map((elem) => (
                <option value={elem.code}>{elem.code}</option>
              ))}
            </select>
            <br />

            <br />
            <hr />

            <br />
            <label>Path </label>
            <select
              name="path"
              value={path}
              onChange={(e) => {
                setPath(e.target.value);
              }}
            >
              <option value="" defaultValue disabled hidden></option>
              {paths.map((elem) => (
                <option value={elem.code}>{elem.code}</option>
              ))}
            </select>
            <br />

            <br />
            <hr />
            <br />

            <label data-testid="tripDepartTimeTestID">Depart Time</label>
            <br />
            <input
              type="time"
              name="departTime"
              step="2"
              value={departTime}
              className="form-control"
              placeholder="Time"
              onChange={(e) => setDepartTime(e.target.value)}
            ></input>
            <br />

            <input id="submit" type="submit" value="Submit"></input>
          </form>
        </div>
      </Modal>

      <Modal
        ariaHideApp={false}
        wariaHideApp={false}
        isOpen={complexModalIsOpen}
        onAfterOpen={afterOpenComplexModal}
        onRequestClose={closeComplexModal}
        style={customModalStyle}
        contentLabel="Example Modal"
      >
        <div className="form">
          <span
            className="clickableSpan closeCross"
            onClick={closeComplexModal}
          >
            <BiX />
          </span>
          <br />
          <form
            data-testid="TripComplexSubmitBtnTestID"
            onSubmit={handleSubmitComplex}
            method="POST"
          >
            <label>Line </label>
            <select
              name="line"
              value={line}
              onChange={(e) => {
                setLine(e.target.value);
                updatePath(e.target.value);
              }}
            >
              <option value="" defaultValue disabled hidden></option>
              {lines.map((elem) => (
                <option value={elem.code}>{elem.code}</option>
              ))}
            </select>
            <br />
            <br />

            <hr />

            <br />
            <label>Outward Path </label>
            <select
              name="path"
              value={outwardPath}
              onChange={(e) => {
                setOutwardPath(e.target.value);
              }}
            >
              <option value="" defaultValue disabled hidden></option>
              {paths.map((elem) => (
                <option value={elem.code}>{elem.code}</option>
              ))}
            </select>

            <br />
            <br />

            <label>Return Path </label>
            <select
              name="path"
              value={returnPath}
              onChange={(e) => {
                setReturnPath(e.target.value);
              }}
            >
              <option value="" defaultValue disabled hidden></option>
              {paths.map((elem) => (
                <option value={elem.code}>{elem.code}</option>
              ))}
            </select>
            <br />
            <br />

            <hr />

            <br />

            <label data-testid="tripDepartTimeTestID">Depart Time: </label>
            <input
              type="time"
              name="departTime"
              step="2"
              value={departTime}
              className="form-control"
              placeholder="Time"
              onChange={(e) => setDepartTime(e.target.value)}
            ></input>
            <br />

            <br />
            <hr />
            <br />

            <label data-testid="tripFrequencyLblTestID">
              Frequency (in minutes):
            </label>
            <input
              className="shortTextField"
              type="number"
              name="frequency"
              value={frequency}
              onChange={(e) => setFrequency(e.target.value)}
            ></input>
            <br />
            <br />

            <label data-testid="tripNumberOfTripsLblTestID">
              Number of Trips:
            </label>
            <input
              className="shortTextField"
              type="number"
              name="numberOfTrips"
              value={numberOfTrips}
              onChange={(e) => setNumberOfTrips(e.target.value)}
            ></input>
            <br />
            <br />

            <input id="submit" type="submit" value="Submit"></input>
          </form>
        </div>
      </Modal>
    </div>
  );
}

export default Trip;
