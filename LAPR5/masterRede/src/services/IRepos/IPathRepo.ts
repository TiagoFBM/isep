import { Document } from "mongoose";
import { Repo } from "../../core/infra/Repo";
import { IPathPersistence } from "../../dataschema/IPathPersistence";
import { Path } from "../../domain/path/Path";
import { PathCode } from "../../domain/path/PathCode";

export default interface IPathRepo extends Repo<Path> {
    save(Path: Path): Promise<Path>;
    findByCode (PathCode: PathCode | string): Promise<Path>;
    findByCodeDB (PathCode: PathCode | string): Promise<Document & IPathPersistence>;
    findAll (): Promise<Array<Path>>;
    findPaginated ({skip, limit}): Promise<Array<Path>>;
    getNumberOfDocuments(): Promise<Number>;
  }