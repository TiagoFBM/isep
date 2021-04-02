import { DriverType } from "../../../../src/domain/driver/DriverType";
import  NodeMap from "../../../../src/mappers/NodeMap";
import INodeDTO from "../../../../src/dto/INodeDTO";
import { Node } from "../../../../src/domain/node/Node";
import { NodeCrewTravelTimes } from "../../../../src/domain/node/NodeCrewTravelTimes";
import ICrewTravelTimeDTO from "../../../../src/dto/ICrewTravelTimeDTO";

describe('Driver Type Mapper Test', () => {

    var assert = require('chai').assert

    it('Convert to DTO', () => {
        
        //const crewTravelTimesDTO: ICrewTravelTimeDTO = {node: "Node:11", duration:1234};
       // const crewTravelTimes:Array<NodeCrewTravelTimes> = [NodeCrewTravelTimes.create(crewTravelTimesDTO).getValue()];

        let node = Node.create({code:"Node:11", name: "Baltar", 
        latitude:"41.1937898023744", longitude: "-8.38716802227697", 
        shortName: "BALTR", isDepot: false, isReliefPoint: false, 
        crewTravelTimes: [{node: "Node:11", duration:300}]});

        let result:INodeDTO = NodeMap.toDTO(node.getValue());

        let expected:INodeDTO = {
            code:"Node:11", 
            name: "Baltar", 
            latitude:"41.1937898023744", 
            longitude: "-8.38716802227697", 
            shortName: "BALTR", 
            isDepot: false, 
            isReliefPoint: false, 
            crewTravelTimes: [{ node: "Node:11", duration:300}]
            }
         

         assert.isOk(result, expected);
    }),

    it('Convert to Domain', () => {
        //const crewTravelTimesDTO: ICrewTravelTimeDTO = {node: "Node:11", duration:1234};
       // const crewTravelTimes:Array<NodeCrewTravelTimes> = [NodeCrewTravelTimes.create(crewTravelTimesDTO).getValue()];

        let nodeDTO:INodeDTO = {
            code:"Node:11", 
            name: "Baltar", 
            latitude:"41.1937898023744",
            longitude: "-8.38716802227697", 
            shortName: "BALTR", 
            isDepot: false, 
            isReliefPoint: false, 
            crewTravelTimes: [{node: "Node:11", duration:300}]
        }

        let result:Node = NodeMap.toDomain(nodeDTO);

        let expected = Node.create({code:"Node:11", name: "Baltar",
        latitude:"41.1937898023744", longitude: "-8.38716802227697",
        shortName: "BALTR", isDepot: false, isReliefPoint: false,
        crewTravelTimes: [{node: "Node:11", duration:300}]});

         assert.isOk(result, expected);
    }),

    it('Convert to Persistence', () => {

        let node = Node.create({code:"Node:11", name: "Baltar",
        latitude:"41.1937898023744", longitude: "-8.38716802227697",
        shortName: "BALTR", isDepot: false, isReliefPoint: false,
        crewTravelTimes: [{node: "Node:11", duration:300}]});
        
        let result:INodeDTO = NodeMap.toDTO(node.getValue());
        let expected = {
            code:"Node:11", 
            name: "Baltar", 
            latitude:"41.1937898023744",
            longitude:" -8.38716802227697", 
            shortName: "BALTR", 
            isDepot: false, 
            isReliefPoint: false 
            //crewTravelTimes: [{node: "Node:11", duration:300}]
         }

         assert.isOk(result, expected);
    })

});