import React, { useState, useEffect } from "react";

import config from "../config";
import Modal from "react-modal";
import "./../css/Import.css";
import { BiFirstPage, BiLastPage, BiChevronLeft, BiChevronRight, BiX } from 'react-icons/bi';

function Import() {

    const [file, setFile] = useState(null);

    const handleSubmit = async (e) => {

        e.preventDefault();

        const formData = new FormData();
        formData.append("file", file);

        const options = {
            method: 'POST',
            body: formData
        }
        let res = await fetch(config.apiURL + "/import", options)
        if (res.ok) {
            let res2 = await fetch(config.mdvURL + "/import", options)
            if (res2.ok) {
                alert("Success");
                setFile(null);
            } else {
                alert("error mdv");
            }
        } else {
            alert("error mdr");
        }
    }

    return (
        <div>
            <div className="importDiv">

                <form encType="multipart/form-data" onSubmit={handleSubmit}>
                    <div>
                        <input data-testid="importFileInputTestID" type="file" name="file" accept=".xml" onChange={e => setFile(e.target.files[0])} />
                    </div>
                    <div>
                        <input data-testid="importFileSubmitTestID" type="submit" value="Submit"
                            disabled={file == null ? "disabled" : ""}
                            style={{
                                padding: "10px 120px"
                            }} />
                    </div>
                </form>
            </div>

        </div>
    );
}

export default Import;