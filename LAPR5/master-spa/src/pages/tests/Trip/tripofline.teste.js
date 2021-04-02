import React from "react";
import { render, fireEvent } from "@testing-library/react";
import TripOfLine from "../../Trip/TripOfLine";

describe("Trip of Line Test", () => {
  it("Renders without crashing", () => {

    const { queryByTestId } = render(<TripOfLine/>);
    expect(queryByTestId("TripOfLineTitleTestID")).toBeTruthy();
    expect(queryByTestId("TripOfLineTableTestID")).toBeTruthy();
  });
});