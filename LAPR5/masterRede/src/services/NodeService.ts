import { Service, Inject } from 'typedi';
import config from "../../config";
import INodeDTO from '../dto/INodeDTO';
import { Node } from "../domain/node/Node";
import INodeRepo from '../services/IRepos/INodeRepo';
import INodeService from './IServices/INodeService';
import { Result } from "../core/logic/Result";
import NodeMap from "../mappers/NodeMap";
import { NodeID } from '../domain/node/NodeID';

@Service()
export default class NodeService implements INodeService {
  constructor(
      @Inject(config.repos.node.name) private NodeRepo : INodeRepo
  ) {}

  public async createNode(NodeDTO: INodeDTO): Promise<Result<INodeDTO>> {
    try {
      
      const NodeOrError = await Node.create( NodeDTO );

      if (NodeOrError.isFailure) {
        return Result.fail<INodeDTO>(NodeOrError.errorValue());
      }

      const NodeResult = NodeOrError.getValue();

      await this.NodeRepo.save(NodeResult);

      const NodeDTOResult = NodeMap.toDTO( NodeResult ) as INodeDTO;
      return Result.ok<INodeDTO>( NodeDTOResult )
    } catch (e) {
      throw e;
    }
  }

  public async findAllNodes(): Promise<Array<INodeDTO>> {
    try {
      const recs = await this.NodeRepo.findAll();

      var arr = [];
      recs.forEach(elem => {
        arr.push(NodeMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async findNodesPaginated({ skip, limit }): Promise<Array<INodeDTO>> {
    try {
      const recs = await this.NodeRepo.findPaginated({skip, limit});
      var arr = [];
      recs.forEach(elem => {
        arr.push(NodeMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfNodes(): Promise<Number> {
    try {
      const numberOfNodes = await this.NodeRepo.getNumberOfDocuments();
      return numberOfNodes;
    } catch (err) {
      throw err;
    }
  }

  public async getNodeByCode(nodeCode: string): Promise<Result<INodeDTO>> {
    let nodeCodeDomain = NodeID.create(nodeCode);
    if (nodeCodeDomain.isFailure) {
        return Result.fail<INodeDTO>("Invalid nodeCode");
    }
    try {
        const node = await this.NodeRepo.findByCode(nodeCodeDomain.getValue());
        const nodeDTO = NodeMap.toDTO(node) as INodeDTO;
        return Result.ok<INodeDTO>(nodeDTO);
    } catch (err) {
        throw err;
    }
}

}