import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface UserBirthDateProps {
    value: string;
}

export class UserBirthDate extends ValueObject<UserBirthDateProps> {

    private constructor(props: UserBirthDateProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(birthDate: string): Result<UserBirthDate> {
        const birthDateIsValid = Guard.followsRegex(birthDate, /^[12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/, 'birthDate');
        if (!birthDateIsValid.succeeded) {
            return Result.fail<UserBirthDate>("Invalid Birth Date");
        }

        return Result.ok<UserBirthDate>(new UserBirthDate({ value: birthDate }))
    }

}