import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface ModuleViewPathProps {
    value: string;
}

export class ModuleViewPath extends ValueObject<ModuleViewPathProps> {

    private constructor(props: ModuleViewPathProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(viewPath: string): Result<ModuleViewPath> {
        const viewPathIsEmpty = Guard.isEmptyString(viewPath, 'viewPath');

        if (!viewPathIsEmpty.succeeded) {
            return Result.fail<ModuleViewPath>(viewPathIsEmpty.message);
        }

        return Result.ok<ModuleViewPath>(new ModuleViewPath({ value: viewPath }))
    }

}