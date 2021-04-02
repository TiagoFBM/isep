import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface LineDescriptionProps {
  value: string;
}

export class LineDescription extends ValueObject<LineDescriptionProps> {

  get value (): string {
    return this.props.value;
  }
  
  private constructor (props: LineDescriptionProps) {
    super(props);
  }

  public static create (lineDescription: string): Result<LineDescription> {
    const descriptionIsSmallerThan250 = Guard.hasLessThanMaxStringSize(lineDescription, 250, 'lineDescription');
    if (!descriptionIsSmallerThan250.succeeded) {
        return Result.fail<LineDescription>(descriptionIsSmallerThan250.message);
    }

    return Result.ok<LineDescription>(new LineDescription({ value: lineDescription }))
    
  }
}