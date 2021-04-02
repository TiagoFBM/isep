import { Service, Inject } from 'typedi';

import INodeRepo from "../services/IRepos/INodeRepo";
import { Node } from "../domain/node/Node";
import { NodeID } from "../domain/node/NodeID";
import NodeMap from "../mappers/NodeMap";
import { isUndefined } from "lodash";
import { Document, Model } from 'mongoose';
import INodePersistence from '../dataschema/INodePersistence';
import config from "../../config";
import { NodeShortName } from '../domain/node/NodeShortName';


@Service()
export default class NodeRepo implements INodeRepo {
    private models: any;

    constructor(
        @Inject(config.schemas.node.name) private NodeSchema: Model<INodePersistence & Document>,
    ) { }

    private createBaseQuery(): any {
        return {
            where: {},
        }
    }

    public async exists(node: Node): Promise<boolean> {

        const codeX = node.props.code.value;

        const query = { code: codeX };
        const nodeFound = await this.NodeSchema.findOne(query);

        return !!nodeFound === true;
    }


    public async save(node: Node): Promise<Node> {
        const query = { code: node.id.toString() };

        const NodeDocument = await this.NodeSchema.findOne(query);

        try {
            if (NodeDocument === null) {
                const rawNode: any = NodeMap.toPersistence(node);

                const NodeCreated = await this.NodeSchema.create(rawNode);

                return NodeMap.toDomain(NodeCreated);
            } else {

                NodeDocument.name = node.props.name.value;
                NodeDocument.latitude = node.props.coordinates.latitude;
                NodeDocument.longitude = node.props.coordinates.longitude;
                NodeDocument.shortName = node.props.shortName.value;
                NodeDocument.isDepot = node.props.isDepot;
                NodeDocument.isReliefPoint = node.props.isReliefPoint;

                var newCrewTravelTime: Array<any> = [];
                if (!isUndefined(node.crewTravelTimes)) {
                    node.crewTravelTimes.forEach(element => {
                        newCrewTravelTime.push({
                            node: element.node,
                            duration: element.duration
                        })
                    });
                }

                NodeDocument.crewTravelTimes = newCrewTravelTime;
                await NodeDocument.save();

                return node;
            }
        } catch (err) {
            throw err;
        }
    }

    public async findByCode(nodeCode: NodeID | string): Promise<Node> {
        const query = { code: nodeCode.toString() };
        const NodeRecord = await this.NodeSchema.findOne(query);

        if (NodeRecord != null) {
            return NodeMap.toDomain(NodeRecord);
        }
        else
            return null;
    }

    public async findAll(): Promise<Array<Node>> {
        const records = await this.NodeSchema.find({});

        var arr = [];
        records.forEach(elem => {
            arr.push(NodeMap.toDomain(elem));
        });
        return arr;

    }

    public async findPaginated({skip, limit}: any): Promise<Array<Node>> {
        const DriverTypeRecords = await this.NodeSchema.find({}).skip(parseInt(skip)).limit(parseInt(limit));
        var arr = [];
        DriverTypeRecords.forEach(elem => {
          arr.push(NodeMap.toDomain(elem));
        });
        return arr;
    }

    public async findByCodeDB(Code: NodeID | string): Promise<Document & INodePersistence> {
        const query = {code: Code.toString()};
        const NodeRecord = await this.NodeSchema.findOne(query);
        if (NodeRecord != null) {
            return NodeRecord;
        } else {
            return null;
        }
    }

    public async getNumberOfDocuments(): Promise<Number> {
        const numberOfDocuments = await this.NodeSchema.countDocuments({});
    
        return numberOfDocuments;
      }
}