import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface NodeIDProps {
    value: string;
}

export class NodeID extends ValueObject<NodeIDProps> {

    get value(): string {
        return this.props.value;
    }

    private constructor(props: NodeIDProps) {
        super(props);
    }

    public static create(code: string): Result<NodeID> {
        const codeIsAlphanumeric = Guard.isAlphaNumericString(code, 'code');
        if (!codeIsAlphanumeric.succeeded) {
            return Result.fail<NodeID>(codeIsAlphanumeric.message);
        }

        const codeIsSmallerThan20 = Guard.hasLessThanMaxStringSize(code, 20, 'code');
        if (!codeIsSmallerThan20.succeeded) {
            return Result.fail<NodeID>(codeIsSmallerThan20.message);
        }

        return Result.ok<NodeID>(new NodeID({ value: code }))
    }

    public toString() {
        return this.props.value;
    }

}