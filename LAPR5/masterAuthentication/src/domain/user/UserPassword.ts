import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface UserPasswordProps {
    value: string;
}

export class UserPassword extends ValueObject<UserPasswordProps> {

    private constructor(props: UserPasswordProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(password: string): Result<UserPassword> {
        const passwordIsEmpty = Guard.isEmptyString(password, 'password');

        if (passwordIsEmpty.succeeded) {
            return Result.fail<UserPassword>(passwordIsEmpty.message);
        }

        return Result.ok<UserPassword>(new UserPassword({ value: password }))
    }

}