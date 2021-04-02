import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface ModuleNameProps {
    value: string;
}

export class ModuleName extends ValueObject<ModuleNameProps> {

    private constructor(props: ModuleNameProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(name: string): Result<ModuleName> {
        const nameIsEmpty = Guard.isEmptyString(name, 'name');

        if (nameIsEmpty.succeeded) {
            return Result.fail<ModuleName>(nameIsEmpty.message);
        }

        return Result.ok<ModuleName>(new ModuleName({ value: name }))
    }

}