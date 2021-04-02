import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";

import IVehicleTypeDTO from "../../dto/IVehicleTypeDTO";
import { VehicleTypeCode } from "./VehicleTypeCode";
import { VehicleTypeDescription } from "./VehicleTypeDescription";
import { VehicleTypeAutonomy } from "./VehicleTypeAutonomy";
import { VehicleTypeFuelType } from "./VehicleTypeFuelType";
import { VehicleTypeCostPerKilometer } from "./VehicleTypeCostPerKilometer";
import { VehicleTypeAverageConsuption } from "./VehicleTypeAverageConsuption";
import { VehicleTypeAverageSpeed } from "./VehicleTypeAverageSpeed";

interface VehicleTypeProps {
    code: VehicleTypeCode;
    description: VehicleTypeDescription;
    autonomy: VehicleTypeAutonomy;
    fuelType: VehicleTypeFuelType;
    costPerKilometer: VehicleTypeCostPerKilometer;
    averageConsuption: VehicleTypeAverageConsuption;
    averageSpeed: VehicleTypeAverageSpeed;
}

export class VehicleType extends AggregateRoot<VehicleTypeProps>{
    private constructor(props: VehicleTypeProps) {
        super(props);
    }

    get vehicleTypeId(): VehicleTypeCode {
        return VehicleTypeCode.create(this.vehicleTypeCode.value).getValue();
    }

    get vehicleTypeDescription(): VehicleTypeDescription {
        return this.props.description;
    }

    get vehicleTypeCode(): VehicleTypeCode {
        return this.props.code;
    }

    get vehicleTypeAutonomy(): VehicleTypeAutonomy {
        return this.props.autonomy;
    }

    get vehicleTypeFuelType(): VehicleTypeFuelType {
        return this.props.fuelType;
    }

    get vehicleTypeCostPerKilometer(): VehicleTypeCostPerKilometer {
        return this.props.costPerKilometer;
    }

    get vehicleTypeAverageConsuption(): VehicleTypeAverageConsuption {
        return this.props.averageConsuption;
    }

    get vehicleTypeAverageSpeed(): VehicleTypeAverageSpeed {
        return this.props.averageSpeed;
    }

    set vehicleTypeCode(code: VehicleTypeCode) {
        this.props.code = code;
    }

    set vehicleTypeDescription(description: VehicleTypeDescription) {
        this.props.description = description;
    }

    set vehicleTypeAutonomy(autonomy: VehicleTypeAutonomy) {
        this.props.autonomy = autonomy;
    }

    set vehicleTypeFuelType(fuelType: VehicleTypeFuelType) {
        this.props.fuelType = fuelType;
    }

    set vehicleTypeCostPerKilometer(costPerKM: VehicleTypeCostPerKilometer) {
        this.props.costPerKilometer = costPerKM;
    }

    set vehicleTypeAverageConsuption(avgConsuption: VehicleTypeAverageConsuption) {
        this.props.averageConsuption = avgConsuption;
    }

    set vehicleTypeAverageSpeed(avgSpeed: VehicleTypeAverageSpeed) {
        this.props.averageSpeed = avgSpeed;
    }

    public static create(vehicleTypeDTO: IVehicleTypeDTO): Result<VehicleType> {

        if (Guard.isNull(vehicleTypeDTO, 'vehicleTypeDTO').succeeded) {
            return Result.fail<VehicleType>("vehicleTypeDTO cant be null.");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.code, 'code').succeeded || Guard.isEmptyString(vehicleTypeDTO.code, 'code').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO code is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.description, 'description').succeeded || Guard.isEmptyString(vehicleTypeDTO.description, 'description').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO description is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.autonomy, 'autonomy').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO autonomy is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.fuelType, 'fuel type').succeeded || Guard.isEmptyString(vehicleTypeDTO.fuelType, 'fuel type').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO fuel type is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.costPerKilometer, 'cost per kilometer').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO cost per kilometer is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.averageConsuption, 'average consuption').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO average consuption is invalid");
        }

        if (!Guard.againstNullOrUndefined(vehicleTypeDTO.averageSpeed, 'average speed').succeeded) {
            return Result.fail<VehicleType>("VehicleTypeDTO average speed is invalid");
        }

        const code = VehicleTypeCode.create(vehicleTypeDTO.code);
        if (code.isFailure) {
            return Result.fail<VehicleType>(code.error);
        }
        const description = VehicleTypeDescription.create(vehicleTypeDTO.description);
        if (description.isFailure) {
            return Result.fail<VehicleType>(description.error);
        }
        const autonomy = VehicleTypeAutonomy.create(vehicleTypeDTO.autonomy);
        if (autonomy.isFailure) {
            return Result.fail<VehicleType>(autonomy.error);
        }
        const fuelType = VehicleTypeFuelType.create(vehicleTypeDTO.fuelType);
        if (fuelType.isFailure) {
            return Result.fail<VehicleType>(fuelType.error);
        }
        const costPerKM = VehicleTypeCostPerKilometer.create(vehicleTypeDTO.costPerKilometer);
        if (costPerKM.isFailure) {
            return Result.fail<VehicleType>(costPerKM.error);
        }
        const averageConsuption = VehicleTypeAverageConsuption.create(vehicleTypeDTO.averageConsuption);
        if (averageConsuption.isFailure) {
            return Result.fail<VehicleType>(averageConsuption.error);
        }
        const averageSpeed = VehicleTypeAverageSpeed.create(vehicleTypeDTO.averageSpeed);
        if (averageSpeed.isFailure) {
            return Result.fail<VehicleType>(averageSpeed.error);
        }


        return Result.ok<VehicleType>(new VehicleType({ code: code.getValue(), description: description.getValue(), autonomy: autonomy.getValue(), fuelType: fuelType.getValue(), costPerKilometer: costPerKM.getValue(), averageConsuption: averageConsuption.getValue(), averageSpeed: averageSpeed.getValue() }));

    }
}
