import { Document, Model } from "mongoose";
import { Inject, Service } from "typedi";
import { IPathPersistence } from "../dataschema/IPathPersistence";
import { Path } from "../domain/path/Path";
import { PathCode } from "../domain/path/PathCode";
import PathMap from "../mappers/PathMap";
import IPathRepo from "../services/IRepos/IPathRepo";

@Service()
export default class PathRepo implements IPathRepo {
    private models: any;

    constructor(
        @Inject("PathSchema") private PathSchema : Model<IPathPersistence & Document>,
    ) {}

    private createBaseQuery(): any {
        return {
            where: {},
        }
    }

    public async exists(path: Path): Promise<boolean> {
        const codeX = path.pathCode.value;
        const query = { code: codeX };

        const pathFound = await this.PathSchema.findOne(query);

        return !!pathFound === true;
    }

    public async save(path: Path): Promise<Path> {
        const query = { code: path.pathCode.value };
        const PathDocument = await this.PathSchema.findOne(query);

        try {
            if (PathDocument === null) {
                const rawPath: any = PathMap.toPersistence(path);
                let pathCreated = await this.PathSchema.create(rawPath);
                pathCreated = await this.PathSchema.populate(pathCreated, {
                    path: "segments",
                    populate: {
                        path: "firstNodeID secondNodeID"
                    }
                });
                return PathMap.toDomain(pathCreated);
            } else {
                /*PathDocument.isEmpty = path.pathIsEmpty;
                PathDocument.segments = [];
                path.pathSegments.forEach(elem => {

                });*/
            }
        } catch (err) {
            console.log(err);
            
            throw err;
        }
    }

    public async findByCode(pathCode: PathCode | string): Promise<Path> {
        const query = { code: pathCode.toString() };
        const PathRecord = await this.PathSchema.findOne(query).populate({
            path: "segments",
            populate: {
                path: "firstNodeID secondNodeID"
            }
        });

        if (PathRecord != null) {
            return PathMap.toDomain(PathRecord);
        }
        return null;
    }

    public async findByCodeDB(Code: PathCode | string): Promise<Document & IPathPersistence> {
        const query = {code: Code.toString()};
        const pathRecord = await this.PathSchema.findOne(query);
        if (pathRecord != null) {
            return pathRecord;
        } else {
            return null;
        }
    }

    public async findAll(): Promise<Array<Path>> {
        const PathRecords = await this.PathSchema.find({}).populate({
            path: "segments",
            populate: {
                path: "firstNodeID secondNodeID"
            }
        });
        var arr = [];
        PathRecords.forEach(elem => {
            arr.push(PathMap.toDomain(elem));
        })
        return arr;
    }

    public async findPaginated({skip, limit}: any): Promise<Array<Path>> {
        const PathRecords = await this.PathSchema.find({}).populate({
            path: "segments",
            populate: {
                path: "firstNodeID secondNodeID"
            }
        }).skip(parseInt(skip)).limit(parseInt(limit));
        var arr = [];
        PathRecords.forEach(elem => {
            arr.push(PathMap.toDomain(elem));
        })
        return arr;
    }

    public async getNumberOfDocuments(): Promise<Number> {
        const numberOfDocuments = await this.PathSchema.countDocuments({});
    
        return numberOfDocuments;
      }
}