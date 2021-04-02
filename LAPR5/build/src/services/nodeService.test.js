/*const sinon = require('sinon');
import Container from 'typedi';
import config from '../../config'

import { Result } from '../core/logic/Result';

import { NodeID } from '../domain/node/NodeID';
import { NodeName } from '../domain/node/NodeName';
import { Coordinates } from '../domain/node/Coordinates';
import { NodeShortName } from '../domain/node/NodeShortName';
import { Node } from '../domain/Node';
import INodeDTO from '../dto/INodeDTO';
import INodeRepo from './IRepos/INodeRepo';
import NodeService from './NodeService';
import ICrewTravelTimeDTO from '../dto/ICrewTravelTimeDTO';
import { NodeCrewTravelTimes } from '../domain/node/NodeCrewTravelTimes';

describe('Node service create', () => {

    const code = NodeID.create('Node:11');
    const name = NodeName.create("Baltar");
    const coordinates = Coordinates.create( "41.1937898023744", "-8.38716802227697");
    const shortName = NodeShortName.create("BALTR");
    //const crewTravelTimesDTO: ICrewTravelTimeDTO = {node: "Node:11", duration:1234};
    //const crewTravelTimes:Array<NodeCrewTravelTimes> = [NodeCrewTravelTimes.create(crewTravelTimesDTO).getValue()];
    

    let node: Result<Node> = Node.create({
        code: code.getValue().value,
        name: name.getValue().value,
        latitude: coordinates.getValue().latitude,
        longitude: coordinates.getValue().longitude,
        shortName: shortName.getValue().value,
        isDepot: false,
        isReliefPoint: false,
        //crewTravelTimes: crewTravelTimes

    })
    

    let res = Result.ok<INodeDTO>({
        code: 'Node:11',
        name: 'Baltar',
        latitude: "41.1937898023744",
        longitude: '-8.38716802227697',
        shortName: "BALTR",
        isDepot: false,
        isReliefPoint: false,
        //crewTravelTimes: [ctt]
    })
    
    const dto: INodeDTO = {
        code: "Node:11",
        name: "Baltar",
        latitude: "41.1937898023744",
        longitude: "-8.38716802227697",
        shortName: "BALTR",
        isDepot: false,
        isReliefPoint: false,
        //crewTravelTimes: [{node: 'Node:11', duration: 1233}]
    }

    

    let NodeRepoClass = require(config.repos.node.path).default
    let NodeRepoInstance: INodeRepo = Container.get(NodeRepoClass)
    Container.set(config.repos.node.name, NodeRepoInstance)
    NodeRepoInstance = Container.get(config.repos.node.name);

    let NodeServiceInstance = new NodeService(NodeRepoInstance);

    beforeEach(() => {
    });

    afterEach(function () {
        sinon.restore();
    });

    it('should create ', async () => {
        sinon.stub(NodeRepoInstance, "save").returns(node.getValue())
        sinon.assert.match((NodeServiceInstance.createNode(dto)), res);
    })
})*/ 
//# sourceMappingURL=nodeService.test.js.map