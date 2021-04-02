import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface UserNameProps {
    value: string;
}

export class UserName extends ValueObject<UserNameProps> {

    private constructor(props: UserNameProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(name: string): Result<UserName> {
        const nameIsEmpty = Guard.isEmptyString(name, 'name');

        if (nameIsEmpty.succeeded) {
            return Result.fail<UserName>(nameIsEmpty.message);
        }

        return Result.ok<UserName>(new UserName({ value: name }))
    }

}