import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface NodeNameProps {
  value: string;
}

export class NodeName extends ValueObject<NodeNameProps> {

  get value (): string {
    return this.props.value;
  }
  
  private constructor (props: NodeNameProps) {
    super(props);
  }

  public static create (name: string): Result<NodeName> {

    const nameIsAlphanumeric = Guard.isAlphaNumericString(name, 'name');
    if (!nameIsAlphanumeric.succeeded) {
        return Result.fail<NodeName>(nameIsAlphanumeric.message);
    }
      
    const nameIsSmallerThan200 = Guard.hasLessThanMaxStringSize(name, 200, 'name');
    if (!nameIsSmallerThan200.succeeded) {
        return Result.fail<NodeName>(nameIsSmallerThan200.message);
    }

    return Result.ok<NodeName>(new NodeName({ value: name }))
    
  }
}

