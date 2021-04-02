"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Path = void 0;
const AggregateRoot_1 = require("../../core/domain/AggregateRoot");
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const PathCode_1 = require("./PathCode");
class Path extends AggregateRoot_1.AggregateRoot {
    constructor(props) {
        super(props);
    }
    get pathCode() {
        return this.props.code;
    }
    get pathIsEmpty() {
        return this.props.isEmpty;
    }
    get pathSegments() {
        return this.props.segments;
    }
    static create(pathDTO) {
        if (Guard_1.Guard.isNull(pathDTO, "pathDTO").succeeded) {
            return Result_1.Result.fail("PathDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(pathDTO.code, "pathDTO.code").succeeded) {
            return Result_1.Result.fail("PathDTO code can\'t be null.");
        }
        if (Guard_1.Guard.isNull(pathDTO.isEmpty, "pathDTO.isEmpty").succeeded) {
            return Result_1.Result.fail("PathDTO isEmpty can\'t be null.");
        }
        const pathCode = PathCode_1.PathCode.create(pathDTO.code);
        if (pathCode.isFailure) {
            return Result_1.Result.fail(pathCode.error);
        }
        return Result_1.Result.ok(new Path({ code: pathCode.getValue(), isEmpty: pathDTO.isEmpty, segments: [] }));
    }
    addSegment(segm) {
        if (Guard_1.Guard.isNull(segm, "segment").succeeded) {
            return false;
        }
        if (Guard_1.Guard.isNull(segm.firstNodeCode, "firstNodeCode").succeeded) {
            return false;
        }
        if (Guard_1.Guard.isNull(segm.secondNodeCode, "secondNodeCode").succeeded) {
            return false;
        }
        this.props.segments.push(segm);
    }
}
exports.Path = Path;
//# sourceMappingURL=Path.js.map