
export interface IGuardResult {
  succeeded: boolean;
  message?: string;
}

export interface IGuardArgument {
  argument: any;
  argumentName: string;
}

export type GuardArgumentCollection = IGuardArgument[];

export class Guard {
  public static combine(guardResults: IGuardResult[]): IGuardResult {
    for (let result of guardResults) {
      if (result.succeeded === false) return result;
    }

    return { succeeded: true };
  }

  public static againstNullOrUndefined(argument: any, argumentName: string): IGuardResult {
    if (argument === null || argument === undefined) {
      return { succeeded: false, message: `${argumentName} is null or undefined` }
    } else {
      return { succeeded: true }
    }
  }

  public static againstNullOrUndefinedBulk(args: GuardArgumentCollection): IGuardResult {
    for (let arg of args) {
      const result = this.againstNullOrUndefined(arg.argument, arg.argumentName);
      if (!result.succeeded) return result;
    }

    return { succeeded: true }
  }

  public static isOneOf(value: any, validValues: any[], argumentName: string): IGuardResult {
    let isValid = false;
    for (let validValue of validValues) {
      if (value === validValue) {
        isValid = true;
      }
    }

    if (isValid) {
      return { succeeded: true }
    } else {
      return {
        succeeded: false,
        message: `${argumentName} isn't oneOf the correct types in ${JSON.stringify(validValues)}. Got "${value}".`
      }
    }
  }

  public static inRange(num: number, min: number, max: number, argumentName: string): IGuardResult {
    const isInRange = num >= min && num <= max;
    if (!isInRange) {
      return { succeeded: false, message: `${argumentName} is not within range ${min} to ${max}.` }
    } else {
      return { succeeded: true }
    }
  }

  public static allInRange(numbers: number[], min: number, max: number, argumentName: string): IGuardResult {
    let failingResult: IGuardResult = null;
    for (let num of numbers) {
      const numIsInRangeResult = this.inRange(num, min, max, argumentName);
      if (!numIsInRangeResult.succeeded) failingResult = numIsInRangeResult;
    }

    if (failingResult) {
      return { succeeded: false, message: `${argumentName} is not within the range.` }
    } else {
      return { succeeded: true }
    }
  }

  public static isNegativeOrZero(argument: number, argumentName: string): IGuardResult {
    if (argument <= 0) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} can not be negative or zero.` }
    }
  }

  public static isPositiveOrZero(argument: number, argumentName: string): IGuardResult {
    if (argument >= 0) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} can not be negative.` }
    }
  }


  public static againstInvalidLatitude(argument: string, argumentName: string): IGuardResult {
    var validLatitudeReg = new RegExp("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")
    let result = argument.match(validLatitudeReg)
    if (result != null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not in a valid WGS84 format.` }
    }
  }

  public static againstInvalidLongitude(argument: string, argumentName: string): IGuardResult {
    var validLongitudeReg = new RegExp("^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
    let result = argument.match(validLongitudeReg)
    if (result != null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not in a valid WGS84 format.` }
    }
  }

  public static isAlphaNumericString(argument: string, argumentName: string): IGuardResult {
    var letterNumber = new RegExp("[a-zA-Z0-9:_]");
    let result = argument.match(letterNumber);
    if (result != null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not alphanumeric.` }
    }
  }

  public static isEmptyString(argument: string, argumentName: string): IGuardResult {
    if (argument === "") {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is empty.` }
    }
  }

  public static isNull(argument: any, argumentName: string): IGuardResult {
    if (argument === null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not empty.` }
    }
  }

  public static hasLessThanMaxStringSize(argument: string, maxSize: number, argumentName: string): IGuardResult {
    if (argument.length > maxSize) {
      return { succeeded: false, message: `${argumentName} is above the max size limit.` }
    } else {
      return { succeeded: true }
    }
  }

  public static hasCorrectStringSize(argument: string, size: number, argumentName: string): IGuardResult {
    if (argument.match(new RegExp('[a-zA-Z0-9]')) && argument.length === size) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} isn't a 20 character alphanumeric string.` }
    }
  }


  public static isBiggerThanSize(argument: string, minSize: number, argumentName: string): IGuardResult {
    if (argument.length > minSize) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is bellow the min size limit.` }
    }
  }

  public static isShortName(argument: string, argumentName: string): IGuardResult {
    var validShortName = new RegExp("^[A-Z]{0,20}$")
    let result = argument.match(validShortName)
    if (result != null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not valid as Short Name for Node.` }
    }
  }

  public static isValidColor(color: string, argumentName: string): IGuardResult {
    var validColor = new RegExp('RGB[(]([0-9]+),([0-9]+),([0-9]+)[)]');
    let result = color.match(validColor)
    if (result != null) {
      return { succeeded: true }
    } else {
      return { succeeded: false, message: `${argumentName} is not valid Color for line.` }
    }  }

}