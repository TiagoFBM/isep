import INodePersistence from '../../dataschema/INodePersistence';

import mongoose from 'mongoose';

const Node = new mongoose.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Node Code.'],
        unique: true
    },
    latitude: {
        type: String,
        required: [true, 'Please enter Node Latitude.']
    },
    longitude: {
        type: String,
        required: [true, 'Please enter Node Longitude.']
    },
    name: {
        type: String,
        required: [true, 'Please enter Node Name.']
    },
    shortName: {
        type: String,
        required: [true, 'Please enter Node Short Name.']
    },
    isDepot: {
        type: Boolean,
        required: [true, 'Please enter true if the Node is a Collection Station.']
    },
    isReliefPoint: {
        type: Boolean,
        required: [true, 'Please enter true if the Node is a Relief Point.']
    },
    crewTravelTimes: [{
        node: {
            type: String,
            required: [true, 'Please enter Crew Travel Time Node.']
        },
        duration: {
            type: Number,
            required: [true, 'Please enter Crew Travel Time Duration.']
        }
    }],

},
    { timestamps: true }
);

export default mongoose.model<INodePersistence & mongoose.Document>('Node', Node);