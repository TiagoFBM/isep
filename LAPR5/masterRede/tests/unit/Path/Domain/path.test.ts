
import { expect } from 'chai';
import { Result } from "../../../../src/core/logic/Result";
import { Path } from "../../../../src/domain/path/Path";
import IPathSegmentDTO from '../../../../src/dto/IPathSegmentDTO';

describe('Path Test', () => {


    it('Can create Path with code, a boolean to see if its empty and segments.', () => {

        let pathSegment1: IPathSegmentDTO = ({
            firstNodeID: "nodeID1",
            secondNodeID: "nodeID2",
            travelTimeBetweenNodes: 100,
            distanceBetweenNodes: 1000
        });

        let expected = Result.ok<Path>();
        let result = Path.create({ code: "Node:11", isEmpty: true, segments: [pathSegment1] });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });


    it('Cant create Path with null DTO.', () => {

        let expected = Result.fail<Path>("PathDTO can\'t be null.");
        let result = Path.create(null);

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create Path with null code.', () => {

        let expected = Result.fail<Path>("PathDTO code can't be null.");
        let result = Path.create({
            code: null,
            isEmpty: true,
            segments: []
        });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });


    it('Cant create Path with empty code.', () => {
        let pathSegment1: IPathSegmentDTO = ({
            firstNodeID: "nodeID1",
            secondNodeID: "nodeID2",
            travelTimeBetweenNodes: 100,
            distanceBetweenNodes: 1000
        });

        let expected = Result.fail<Path>("code is not alphanumeric.");
        let result = Path.create({
            code: "",
            isEmpty: true,
            segments: [pathSegment1]
        });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create Path with null variable which represents if the path its empty or not .', () => {

        let pathSegment1: IPathSegmentDTO = ({
            firstNodeID: "nodeID1",
            secondNodeID: "nodeID2",
            travelTimeBetweenNodes: 100,
            distanceBetweenNodes: 1000
        });

        let expected = Result.fail<Path>("PathDTO isEmpty can't be null.");
        let result = Path.create({
            code: "Cod1",
            isEmpty: null,
            segments: [pathSegment1]
        });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });


});
