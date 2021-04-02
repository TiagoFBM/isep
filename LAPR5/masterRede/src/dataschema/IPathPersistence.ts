import INodePersistence from "./INodePersistence";

export interface IPathPersistence {
    _id: string;
    code: string;
    isEmpty: boolean;
    segments: Array<{
        firstNode: INodePersistence;
        secondNode: INodePersistence;
        travelTimeBetweenNodes: number;
        distanceBetweenNodes: number;
    }>;
}