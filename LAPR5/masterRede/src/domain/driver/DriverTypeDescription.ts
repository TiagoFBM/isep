import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface DriverTypeDescriptionProps {
  value: string;
}

export class DriverTypeDescription extends ValueObject<DriverTypeDescriptionProps> {

  get value (): string {
    return this.props.value;
  }
  
  private constructor (props: DriverTypeDescriptionProps) {
    super(props);
  }

  public static create (description: string): Result<DriverTypeDescription> {
    const descriptionIsSmallerThan250 = Guard.hasLessThanMaxStringSize(description, 250, 'description');
    if (!descriptionIsSmallerThan250.succeeded) {
        return Result.fail<DriverTypeDescription>(descriptionIsSmallerThan250.message);
    }

    return Result.ok<DriverTypeDescription>(new DriverTypeDescription({ value: description }))
    
  }
}

