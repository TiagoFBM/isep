import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Line from '../../Line/Line';
import SpecificLine from '../../Line/SpecificLine';

describe("Line Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<Line />)
        expect(queryByTestId("addLineBtnTestID")).toBeTruthy()
        expect(queryByTestId("lineTableTestID")).toBeTruthy()
    })

    it("Opens Modal Sucessfuly", () => {
        const { queryByTestId } = render(<Line />)
        expect(queryByTestId("lineCodeInputTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('addLineBtnTestID'))

        expect(queryByTestId("lineCodeInputTestID")).toBeTruthy();
    })
});
