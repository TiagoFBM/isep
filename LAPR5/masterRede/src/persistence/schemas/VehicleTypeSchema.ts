import { IVehicleTypePersistence } from '../../dataschema/IVehicleTypePersistence';
import { EnumFuelType } from "../../domain/vehicle/EnumFuelType";

import mongoose from 'mongoose';

const VehicleType = new mongoose.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Vehicle Type Code.'],
        unique: true
    },
    description: {
        type: String,
        required: [true, 'Please enter Vehicle Type Description.']
    },
    autonomy: {
        type: Number,
        required: [true, 'Please enter Autonomy']
    },
    fuelType: {
        type: String,
        required: [true, 'Please enter the Fuel Type']
    },
    costPerKilometer: {
        type: Number,
        required: [true, 'Please enter the cost per kilometer']
    },
    averageConsuption: {
        type: Number,
        required: [true, 'Please enter the average consuption']
    },
    averageSpeed: {
        type: Number,
        required: [true, 'Please enter the average speed']
    }
},
    { timestamps: true }
);

export default mongoose.model<IVehicleTypePersistence & mongoose.Document>('VehicleType', VehicleType);