import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Import from '../../Import';

describe("Import Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<Import />)
        expect(queryByTestId("importFileInputTestID")).toBeTruthy()
        expect(queryByTestId("importFileSubmitTestID")).toBeTruthy()
    })
});
