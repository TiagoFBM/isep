import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Trip from '../../Trip/Trip';

describe("Trip Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<Trip />)
        expect(queryByTestId("addComplexTripBtnTestID")).toBeTruthy()
        expect(queryByTestId("addTripBtnTestID")).toBeTruthy()
        expect(queryByTestId("TripTableTestID")).toBeTruthy()
    })

    it("Opens Complex Modal Sucessfuly", () => {
        const { queryByTestId } = render(<Trip />)
        expect(queryByTestId("TripComplexSubmitBtnTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('addComplexTripBtnTestID'))

        expect(queryByTestId("TripComplexSubmitBtnTestID")).toBeTruthy();
    })

    it("Opens Simple Modal Sucessfuly", () => {
        const { queryByTestId } = render(<Trip />)
        expect(queryByTestId("TripSubmitBtnTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('addTripBtnTestID'))

        expect(queryByTestId("TripSubmitBtnTestID")).toBeTruthy();
    })


});
