import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Node from '../../Node';

describe("Node Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<Node />)
        expect(queryByTestId("nodeModalOpenBtnTestID")).toBeTruthy()
        expect(queryByTestId("nodeTableTestID")).toBeTruthy()
    })

    it("Opens Modal Sucessfuly", () => {
        const { queryByTestId } = render(<Node />)
        expect(queryByTestId("nodeCodeTestID")).toBeFalsy();

        fireEvent.click(queryByTestId('nodeModalOpenBtnTestID'))

        expect(queryByTestId("nodeCodeTestID")).toBeTruthy();
    })
});
