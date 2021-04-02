import { Document } from "mongoose";
import { Repo } from "../../core/infra/Repo";
import INodePersistence from "../../dataschema/INodePersistence";
import { Node } from "../../domain/node/Node";
import { NodeID } from "../../domain/node/NodeID";
import { NodeShortName } from "../../domain/node/NodeShortName";

export default interface INodeRepo extends Repo<Node> {
    save(Node: Node): Promise<Node>;
    findByCode(NodeCode: NodeID | string): Promise<Node>;
    findAll(): Promise<Array<Node>>;
    findPaginated ({skip, limit}): Promise<Array<Node>>;
    findByCodeDB(NodeShortName: NodeShortName | string): Promise<Document & INodePersistence>;
    getNumberOfDocuments(): Promise<Number>;
}