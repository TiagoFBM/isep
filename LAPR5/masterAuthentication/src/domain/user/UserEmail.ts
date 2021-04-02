import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface UserEmailProps {
    value: string;
}

export class UserEmail extends ValueObject<UserEmailProps> {

    private constructor(props: UserEmailProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(email: string): Result<UserEmail> {
        const emailIsEmpty = Guard.isEmptyString(email, 'email');

        if (emailIsEmpty.succeeded) {
            return Result.fail<UserEmail>(emailIsEmpty.message);
        }

        return Result.ok<UserEmail>(new UserEmail({ value: email }))
    }

    public toString(): string {
        return this.props.value;
    }

}