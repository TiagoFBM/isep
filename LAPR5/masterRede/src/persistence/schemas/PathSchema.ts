import mongoose from 'mongoose';
import { IPathPersistence } from '../../dataschema/IPathPersistence';

const Path = new mongoose.Schema({
    code: {
        type: String,
        required: [true, "Please enter the Path Code."],
        unique: true
    },
    isEmpty: {
        type: Boolean,
        required: [true, "Please enther whether the Path is empty or not."]
    },
    segments: [{
        firstNodeID: {type: mongoose.Schema.Types.ObjectId, ref: "Node", required: true},
        secondNodeID: {type: mongoose.Schema.Types.ObjectId, ref: "Node", required: true},
        travelTimeBetweenNodes: {type: Number, required: true},
        distanceBetweenNodes: {type: Number, required: true},
    }]

}, {timestamps: true});

export default mongoose.model<IPathPersistence & mongoose.Document>("Path", Path);