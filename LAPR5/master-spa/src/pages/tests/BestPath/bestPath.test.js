import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import BestPath from '../../BestPath';

describe("Best Path Test", () => {

    it("Renders without crashing", () => {
        const { queryByTestId } = render(<BestPath />)
        expect(queryByTestId("bestPathInitialNodeInputTestID")).toBeTruthy()
        expect(queryByTestId("bestPathFinalNodeInputTestID")).toBeTruthy()
        expect(queryByTestId("bestPathScheduleInputTestID")).toBeTruthy()
        expect(queryByTestId("bestPathSubmitBtnTestID")).toBeTruthy()
    })
});
