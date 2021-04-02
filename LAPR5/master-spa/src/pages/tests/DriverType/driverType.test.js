import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import DriverType from '../../DriverType';

describe("Driver Type Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<DriverType />)
        expect(queryByTestId("addDriverTypeBtnID")).toBeTruthy()
        expect(queryByTestId("driverTypeTableID")).toBeTruthy()
    })

    it("Opens modal", () => {
        const { queryByTestId } = render(<DriverType />)
        expect(queryByTestId("submitModalTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('addDriverTypeBtnID'))

        expect(queryByTestId("submitModalTestID")).toBeTruthy();
    })

});
