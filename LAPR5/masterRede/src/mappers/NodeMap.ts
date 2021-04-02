import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { Node } from "../domain/node/Node";
import INodeDTO from "../dto/INodeDTO";
import ICrewTravelTimeDTO from "../dto/ICrewTravelTimeDTO";
import { isUndefined } from "lodash";

export default class NodeMapper extends Mapper<Node> {

    public static toDTO(node: Node): INodeDTO {

        var list: Array <ICrewTravelTimeDTO> = [];
        if (!isUndefined(node.crewTravelTimes)) {
            node.crewTravelTimes.forEach(element => {
                var crewTravelTimeDTO = {node: element.node, duration: element.duration} as ICrewTravelTimeDTO;
                list.push(crewTravelTimeDTO);
            });
        }
        return {
            code: node.code.value,
            latitude: node.coordinates.latitude,
            longitude: node.coordinates.longitude,
            name: node.name.value,
            shortName: node.shortName.value,
            isDepot: node.isDepot,
            isReliefPoint: node.isReliefPoint,
            crewTravelTimes: list,
        } as INodeDTO;
    }

    public static toDomain(node: any | Model<INodeDTO & Document>): Node {
        const nodeOrError = Node.create(
            node
        );

        nodeOrError.isFailure ? console.log(nodeOrError.error) : '';

        return nodeOrError.isSuccess ? nodeOrError.getValue() : null;
    }

    public static toPersistence(node: Node): any {

        var newCrewTravelTime: Array<any> = [];
        if (!isUndefined(node.crewTravelTimes)) {
            node.crewTravelTimes.forEach(element => {
                newCrewTravelTime.push({
                node : element.node,
                duration : element.duration,
                })
            });
        }

        const node1 = {
            code: node.code.value,
            latitude: node.coordinates.latitude,
            longitude: node.coordinates.longitude,
            name: node.name.value,
            shortName: node.shortName.value,
            isDepot: node.isDepot,
            isReliefPoint: node.isReliefPoint,
            crewTravelTimes: newCrewTravelTime,
        }
        return node1;
    }
}