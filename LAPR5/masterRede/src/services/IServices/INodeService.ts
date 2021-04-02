import { Result } from "../../core/logic/Result";
import INodeDTO from "../../dto/INodeDTO";

export default interface INodeService  {
  createNode(NodeDTO: INodeDTO): Promise<Result<INodeDTO>>;
  findAllNodes(): Promise<Array<INodeDTO>>;
  findNodesPaginated({skip, limit}): Promise<Array<INodeDTO>>;
  getNumberOfNodes(): Promise<Number>;
  getNodeByCode(NodeCode: string): Promise<Result<INodeDTO>>;
}