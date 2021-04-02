import { IDriverTypePersistence } from '../../dataschema/IDriverTypePersistence';
import mongoose from 'mongoose';

const DriverType = new mongoose.Schema({
    code: {
      type: String,
      required: [true, 'Please enter Driver Type Code.'],
      unique: true
    },
    description: {
      type: String,
      required: [true, 'Please enter Driver Type Description.']
    }
  },
  { timestamps: true }
);

export default mongoose.model<IDriverTypePersistence & mongoose.Document>('DriverType', DriverType);
