import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import VehicleType from '../../VehicleType';

describe("Driver Type Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<VehicleType />)
        expect(queryByTestId("vehicleTypeModalOpenBtnTestID")).toBeTruthy()
        expect(queryByTestId("vehicleTypeTableTestID")).toBeTruthy()
    })

    it("Opens modal", () => {
        const { queryByTestId } = render(<VehicleType />)
        expect(queryByTestId("vehicleTypeCodeTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('vehicleTypeModalOpenBtnTestID'))

        expect(queryByTestId("vehicleTypeCodeTestID")).toBeTruthy();
    })

});
