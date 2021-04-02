import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface UserUsernameProps {
    value: string;
}

export class UserUsername extends ValueObject<UserUsernameProps> {

    private constructor(props: UserUsernameProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(username: string): Result<UserUsername> {
        const usernameIsEmpty = Guard.isEmptyString(username, 'username');

        if (usernameIsEmpty.succeeded) {
            return Result.fail<UserUsername>(usernameIsEmpty.message);
        }

        return Result.ok<UserUsername>(new UserUsername({ value: username }))
    }

}