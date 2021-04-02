import mongoose from 'mongoose';
import { ILinePersistence } from '../../dataschema/ILinePersistence';

const Line = new mongoose.Schema({
  code: {
    type: String,
    required: [true, 'Please enter Line Code.'],
    unique: true
  },
  description: {
    type: String,
    required: [true, 'Please enter Line Description.']
  },
  linePaths: [{
    path: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Path',
      required: [true, 'Please enter LinePath Path.']
    },
    orientation: {
      type: String,
      required: [true, 'Please enter Line Orientation.']
    }
  }],
  allowedVehicles: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'VehicleType'
  }],
  deniedVehicles: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'VehicleType'
  }],
  allowedDrivers: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'DriverType'
  }],
  deniedDrivers: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'VehicleType'
  }],
  lineColor: {
    type: String,
    required: [true, 'Please enter Line Color.']
  }
},
  { timestamps: true }
);

export default mongoose.model<ILinePersistence & mongoose.Document>('Line', Line);
