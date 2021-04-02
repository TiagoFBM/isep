import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";

import INodeDTO from "./../../dto/INodeDTO";
import { Coordinates } from "./Coordinates";
import { NodeID } from "./NodeID";
import { NodeName } from "./NodeName";
import { NodeShortName } from "./NodeShortName";
import { NodeCrewTravelTimes } from "./NodeCrewTravelTimes";
import { isUndefined } from "lodash";


interface NodeProps {

  code: NodeID;
  name: NodeName;
  coordinates: Coordinates
  shortName: NodeShortName;
  isDepot:boolean;
  isReliefPoint:boolean;
  crewTravelTimes?:Array <NodeCrewTravelTimes>;

}

export class Node extends AggregateRoot<NodeProps> {
  
  private constructor (props: NodeProps, id?: UniqueEntityID) {
    super(props, id);
  }

  get coordinates (): Coordinates {
    return this.props.coordinates;
  }

  get code (): NodeID {
    return this.props.code;
  }

  get name (): NodeName {
    return this.props.name;
  }

  get shortName (): NodeShortName {
    return this.props.shortName;
  }

  get crewTravelTimes (): Array <NodeCrewTravelTimes>{
    return this.props.crewTravelTimes;
  }

  get isDepot ():boolean{
    return this.props.isDepot;
  }

  get isReliefPoint ():boolean{
    return this.props.isReliefPoint;
  }


  public static create (nodeDTO: INodeDTO): Result<Node> {

    if (Guard.isNull(nodeDTO,'nodeDTO').succeeded){
      return Result.fail<Node>("NodeDTO can\'t be null.");
    }

    if (Guard.isNull(nodeDTO.code,'nodeDTO.code').succeeded || Guard.isEmptyString(nodeDTO.code,'nodeDTO.code').succeeded){
      return Result.fail<Node>("nodeDTO Code is invalid.");
    }

    if (Guard.isNull(nodeDTO.name,'nodeDTO.name').succeeded || Guard.isEmptyString(nodeDTO.name,'nodeDTO.name').succeeded){
      return Result.fail<Node>("nodeDTO Name is invalid.");
    }    

    if (Guard.isNull(nodeDTO.latitude,'nodeDTO.latitude').succeeded){
      return Result.fail<Node>("nodeDTO Latitude is invalid.");
    }

    if (Guard.isNull(nodeDTO.longitude,'nodeDTO.longitude').succeeded){
      return Result.fail<Node>("nodeDTO Longitude is invalid.");
    }

    if (Guard.isNull(nodeDTO.shortName,'nodeDTO.shortName').succeeded || Guard.isEmptyString(nodeDTO.shortName,'nodeDTO.shortName').succeeded){
      return Result.fail<Node>("nodeDTO Short Name is invalid.");
    }

    if (Guard.isNull(nodeDTO.isDepot,'nodeDTO.isDepot').succeeded){
      return Result.fail<Node>("nodeDTO Depot is invalid.");
    }

    if (Guard.isNull(nodeDTO.isReliefPoint,'nodeDTO.isReliefPoint').succeeded){
      return Result.fail<Node>("nodeDTO Relief Point  is invalid.");
    }

    const code = NodeID.create(nodeDTO.code);
    if(code.isFailure){
      return Result.fail<Node>(code.error);
    }

    const name = NodeName.create(nodeDTO.name);
    if(name.isFailure){
      return Result.fail<Node>(name.error);
    }

    const coordenadas = Coordinates.create(nodeDTO.latitude,nodeDTO.longitude);
    if(coordenadas.isFailure){
      return Result.fail<Node>(coordenadas.error);
    }

    const shortName = NodeShortName.create(nodeDTO.shortName);
    if(shortName.isFailure){
      return Result.fail<Node>(shortName.error);
    }
    
    var crewTravelTimes: Array<NodeCrewTravelTimes> = [];

    if (!isUndefined(nodeDTO.crewTravelTimes)) {
      nodeDTO.crewTravelTimes.forEach(element => {
        var crewTravelTimeOrFailure = NodeCrewTravelTimes.create(element);
        if (crewTravelTimeOrFailure.isSuccess) crewTravelTimes.push(crewTravelTimeOrFailure.getValue());
      });
    }

    return Result.ok<Node>(new Node({
        code: code.getValue(), 
        name: name.getValue(), 
        coordinates: coordenadas.getValue(), 
        shortName: shortName.getValue(), 
        isDepot: nodeDTO.isDepot, 
        isReliefPoint: nodeDTO.isReliefPoint, 
        crewTravelTimes: crewTravelTimes 
      }
      ));

  }
}
