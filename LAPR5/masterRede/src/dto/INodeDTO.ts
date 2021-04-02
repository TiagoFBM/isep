import ICrewTravelTimeDTO from "./ICrewTravelTimeDTO";

export default interface INodeDTO {
    code: string;
    name: string;
    latitude: string;
    longitude: string;
    shortName: string;
    isDepot: boolean;
    isReliefPoint: boolean;
    crewTravelTimes?: Array<ICrewTravelTimeDTO>;
}