import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface ModuleUrlPathProps {
    value: string;
}

export class ModuleUrlPath extends ValueObject<ModuleUrlPathProps> {

    private constructor(props: ModuleUrlPathProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(urlPath: string): Result<ModuleUrlPath> {
        const urlPathIsEmpty = Guard.isEmptyString(urlPath, 'urlPath');

        if (urlPathIsEmpty.succeeded) {
            return Result.fail<ModuleUrlPath>(urlPathIsEmpty.message);
        }

        return Result.ok<ModuleUrlPath>(new ModuleUrlPath({ value: urlPath }))
    }

}