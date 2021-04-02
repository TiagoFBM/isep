import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Path from '../../Path';

describe("Path Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<Path />)
        expect(queryByTestId("pathModalOpenBtnTestID")).toBeTruthy()
        expect(queryByTestId("pathTableTestID")).toBeTruthy()
    })

    it("Opens Modal Sucessfuly", () => {
        const { queryByTestId } = render(<Path />)
        expect(queryByTestId("pathCodeTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('pathModalOpenBtnTestID'))

        expect(queryByTestId("pathCodeTestID")).toBeTruthy();
    })
});
