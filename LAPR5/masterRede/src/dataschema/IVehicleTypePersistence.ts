import { EnumFuelType } from "../domain/vehicle/EnumFuelType";

export interface IVehicleTypePersistence {
    _id: string;
    code: string;
    description: string;
    autonomy: number;
    fuelType: EnumFuelType;
    costPerKilometer: number;
    averageConsuption: number;
    averageSpeed: number;
}