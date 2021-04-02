import { IUserPersistence } from '../../dataschema/IUserPersistence';
import mongoose from 'mongoose';

const User = new mongoose.Schema({
    email: {
      type: String,
      required: [true, 'Please enter email.'],
      unique: true
    },
    username: {
      type: String,
      required: [true, 'Please enter Username.'],
      unique: true
    },
    password: {
        type: String,
        required: [true, 'Please enter Password.']
    },
    name: {
        type: String,
        required: [true, 'Please enter Name.']
    },
    birthDate: {
        type: String,
        required: [true, 'Please enter Birth date.']
    },
    acceptedTerms: {
      type: Boolean,
      required: [true, 'Please enter if user accepted terms.']
    },
    isDataAdmin: {
      type: Boolean,
      required: [true, 'Please enter if user is DataAdmin.']
    }
  },
  { timestamps: true }
);

export default mongoose.model<IUserPersistence & mongoose.Document>('User', User);
