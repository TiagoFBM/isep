export default interface INodePersistence {
    _id: string;
    code: string;
    name: string;
    latitude: string,
    longitude: string;
    shortName: string;
    isDepot: boolean;
    isReliefPoint: boolean;
    crewTravelTimes?: Array<{node: string, duration: number }>;
}