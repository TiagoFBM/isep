"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LinePath = void 0;
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const LinePathOrientation_1 = require("./LinePathOrientation");
const Entity_1 = require("../../core/domain/Entity");
const Path_1 = require("../path/Path");
const PathSegment_1 = require("../path/PathSegment");
const lodash_1 = require("lodash");
class LinePath extends Entity_1.Entity {
    constructor(props) {
        super(props);
    }
    get path() {
        return this.props.path;
    }
    get linePathOrientation() {
        return this.props.orientation;
    }
    static create(LineDTO) {
        if (Guard_1.Guard.isNull(LineDTO, 'LineDTO').succeeded) {
            return Result_1.Result.fail("LineDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(LineDTO.path, 'LineDTO.path').succeeded) {
            return Result_1.Result.fail("LineDTO Path is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.orientation, 'LineDTO.linePathOrientation').succeeded) {
            return Result_1.Result.fail("LineDTO LinePathOrientation is invalid.");
        }
        const newLinePathOrientation = LinePathOrientation_1.LinePathOrientation.create(LineDTO.orientation);
        if (newLinePathOrientation.isFailure) {
            return Result_1.Result.fail(newLinePathOrientation.error);
        }
        var path;
        if (!lodash_1.isUndefined(LineDTO.path.code)) {
            path = Path_1.Path.create(LineDTO.path).getValue();
            let pathSegments = LineDTO.path.segments;
            pathSegments.forEach(elem => {
                var segm = PathSegment_1.PathSegment.create(elem).getValue();
                segm.addFirstNodeCode(elem.firstNodeID);
                segm.addSecondNodeCode(elem.secondNodeID);
                path.addSegment(segm);
            });
        }
        else {
            path = LineDTO.path;
        }
        return Result_1.Result.ok(new LinePath({
            path: path,
            orientation: newLinePathOrientation.getValue()
        }));
    }
}
exports.LinePath = LinePath;
//# sourceMappingURL=LinePath.js.map