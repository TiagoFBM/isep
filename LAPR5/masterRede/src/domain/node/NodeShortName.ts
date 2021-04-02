import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface NodeShortNameProps {
  value: string;
}

export class NodeShortName extends ValueObject<NodeShortNameProps> {

  get value (): string {
    return this.props.value;
  }
  
  private constructor (props: NodeShortNameProps) {
    super(props);
  }

  public static create (name: string): Result<NodeShortName> {
      
    const nameIsValid = Guard.isShortName(name, 'name');
    if (!nameIsValid.succeeded) {
        return Result.fail<NodeShortName>(nameIsValid.message);
    }

    return Result.ok<NodeShortName>(new NodeShortName({ value: name }))
    
  }
}

