import { IModulePersistence } from '../../dataschema/IModulePersistence';
import mongoose from 'mongoose';

const Module = new mongoose.Schema({
    id: {
      type: String,
      required: [true, 'Please enter ID.'],
      unique: true
    },
    name: {
      type: String,
      required: [true, 'Please enter Name.']
    },
    urlPath: {
        type: String,
        required: [true, 'Please enter URL Path.']
    },
    viewPath: {
        type: String,
        required: [true, 'Please enter View Path.']
    },
    isExact: {
        type: Boolean,
        required: [true, 'Please enter Is Exact.']
    },
    isNavItem: {
        type: Boolean,
        required: [true, 'Please enter Is Nav Item.']
    },
    needsAdmin: {
      type: Boolean,
      required: [true, "Please specify if module needs admin permission."]
    }
  },
  { timestamps: true }
);

export default mongoose.model<IModulePersistence & mongoose.Document>('Module', Module);
