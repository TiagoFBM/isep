"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Guard = void 0;
class Guard {
    static combine(guardResults) {
        for (let result of guardResults) {
            if (result.succeeded === false)
                return result;
        }
        return { succeeded: true };
    }
    static againstNullOrUndefined(argument, argumentName) {
        if (argument === null || argument === undefined) {
            return { succeeded: false, message: `${argumentName} is null or undefined` };
        }
        else {
            return { succeeded: true };
        }
    }
    static againstNullOrUndefinedBulk(args) {
        for (let arg of args) {
            const result = this.againstNullOrUndefined(arg.argument, arg.argumentName);
            if (!result.succeeded)
                return result;
        }
        return { succeeded: true };
    }
    static isOneOf(value, validValues, argumentName) {
        let isValid = false;
        for (let validValue of validValues) {
            if (value === validValue) {
                isValid = true;
            }
        }
        if (isValid) {
            return { succeeded: true };
        }
        else {
            return {
                succeeded: false,
                message: `${argumentName} isn't oneOf the correct types in ${JSON.stringify(validValues)}. Got "${value}".`
            };
        }
    }
    static inRange(num, min, max, argumentName) {
        const isInRange = num >= min && num <= max;
        if (!isInRange) {
            return { succeeded: false, message: `${argumentName} is not within range ${min} to ${max}.` };
        }
        else {
            return { succeeded: true };
        }
    }
    static allInRange(numbers, min, max, argumentName) {
        let failingResult = null;
        for (let num of numbers) {
            const numIsInRangeResult = this.inRange(num, min, max, argumentName);
            if (!numIsInRangeResult.succeeded)
                failingResult = numIsInRangeResult;
        }
        if (failingResult) {
            return { succeeded: false, message: `${argumentName} is not within the range.` };
        }
        else {
            return { succeeded: true };
        }
    }
    static isNegativeOrZero(argument, argumentName) {
        if (argument <= 0) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} can not be negative or zero.` };
        }
    }
    static isPositiveOrZero(argument, argumentName) {
        if (argument >= 0) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} can not be negative.` };
        }
    }
    static againstInvalidLatitude(argument, argumentName) {
        var validLatitudeReg = new RegExp("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$");
        let result = argument.match(validLatitudeReg);
        if (result != null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not in a valid WGS84 format.` };
        }
    }
    static againstInvalidLongitude(argument, argumentName) {
        var validLongitudeReg = new RegExp("^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$");
        let result = argument.match(validLongitudeReg);
        if (result != null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not in a valid WGS84 format.` };
        }
    }
    static isAlphaNumericString(argument, argumentName) {
        var letterNumber = new RegExp("[a-zA-Z0-9:_]");
        let result = argument.match(letterNumber);
        if (result != null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not alphanumeric.` };
        }
    }
    static isEmptyString(argument, argumentName) {
        if (argument === "") {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is empty.` };
        }
    }
    static isNull(argument, argumentName) {
        if (argument === null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not empty.` };
        }
    }
    static hasLessThanMaxStringSize(argument, maxSize, argumentName) {
        if (argument.length > maxSize) {
            return { succeeded: false, message: `${argumentName} is above the max size limit.` };
        }
        else {
            return { succeeded: true };
        }
    }
    static hasCorrectStringSize(argument, size, argumentName) {
        if (argument.match(new RegExp('[a-zA-Z0-9]')) && argument.length === size) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} isn't a 20 character alphanumeric string.` };
        }
    }
    static isBiggerThanSize(argument, minSize, argumentName) {
        if (argument.length > minSize) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is bellow the min size limit.` };
        }
    }
    static isShortName(argument, argumentName) {
        var validShortName = new RegExp("^[A-Z]{0,20}$");
        let result = argument.match(validShortName);
        if (result != null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not valid as Short Name for Node.` };
        }
    }
    static isValidColor(color, argumentName) {
        var validColor = new RegExp('RGB[(]([0-9]+),([0-9]+),([0-9]+)[)]');
        let result = color.match(validColor);
        if (result != null) {
            return { succeeded: true };
        }
        else {
            return { succeeded: false, message: `${argumentName} is not valid Color for line.` };
        }
    }
}
exports.Guard = Guard;
//# sourceMappingURL=Guard.js.map