import { Service, Inject } from 'typedi';
import config from "../../config";
import IPathService from './IServices/IPathService';
import { Result } from "../core/logic/Result";
import IPathRepo from './IRepos/IPathRepo';
import IPathDTO from '../dto/IPathDTO';
import { Path } from '../domain/path/Path';
import PathMapper from '../mappers/PathMap';
import Logger from './../loaders/logger';
import INodeRepo from './IRepos/INodeRepo';
import { Guard } from '../core/logic/Guard';
import { PathSegment } from '../domain/path/PathSegment';
import { PathCode } from '../domain/path/PathCode';

@Service()
export default class PathService implements IPathService {
    constructor(
        @Inject(config.repos.path.name) private PathRepo : IPathRepo,
        @Inject(config.repos.node.name) private NodeRepo : INodeRepo
    ) {}

    public async createPath(pathDTO: IPathDTO): Promise<Result<IPathDTO>> {

        try {
            
            var PathOrError = Path.create(pathDTO);

            PathOrError = await this.addSegments(pathDTO, PathOrError);
            Logger.debug("Successfully created the Path domain class.");
            //console.log(pathDTO);
            
            if (PathOrError.isFailure) {
                Logger.error(PathOrError.error);
                return Result.fail<IPathDTO>(PathOrError.error);
            }

            const PathResult = PathOrError.getValue();
            
            
            const PathDomain = await this.PathRepo.save(PathResult);
            Logger.debug("Successfully saved the path to the database.");
            const PathDTOResult = PathMapper.toDTO(PathDomain) as IPathDTO;
            return Result.ok<IPathDTO>(PathDTOResult);
        } catch(err) {
            throw(err);
        }
    }

    private async addSegments(pathDTO: IPathDTO, PathOrError: Result<Path>): Promise<Result<Path>> {
       
        for (const elem of pathDTO.segments) {
            var nodeTemp1 = await this.NodeRepo.findByCodeDB(elem.firstNodeID as string);
            var nodeTemp2 = await this.NodeRepo.findByCodeDB(elem.secondNodeID as string);
            if (Guard.isNull(nodeTemp1, "node1").succeeded || Guard.isNull(nodeTemp2, "node2").succeeded) {
                return Result.fail<Path>("erro");
            }
            var segm = PathSegment.create(elem);
            
            segm.getValue().addFirstNodeID(nodeTemp1._id);
            segm.getValue().addSecondNodeID(nodeTemp2._id);
            PathOrError.getValue().addSegment(segm.getValue());
            
        }   
        
        
        return PathOrError;
          
    }

    public async findAllPaths(): Promise<Array<IPathDTO>> {
        try {
            const recs = await this.PathRepo.findAll();
            var arr = [];
            recs.forEach(elem => {
                arr.push(PathMapper.toDTO(elem));
            });
            return arr;
        } catch (err) {
            throw err;
        }
    }

    public async findPathsPaginated({ skip, limit }): Promise<Array<IPathDTO>> {
        try {
            const recs = await this.PathRepo.findPaginated({ skip, limit });
            var arr = [];
            recs.forEach(elem => {
                arr.push(PathMapper.toDTO(elem));
            });
            return arr;
        } catch (err) {
            throw err;
        }
    }

    public async getNumberOfPaths(): Promise<Number> {
        try {
          const numberOfDT = await this.PathRepo.getNumberOfDocuments();
          return numberOfDT;
        } catch (err) {
          throw err;
        }
    }

    public async getPathByCode(pathCode: string): Promise<Result<IPathDTO>> {
        let pathCodeDomain = PathCode.create(pathCode);
        if (pathCodeDomain.isFailure) {
            return Result.fail<IPathDTO>("Invalid pathCode");
        }
        try {
            const Path = await this.PathRepo.findByCode(pathCodeDomain.getValue());
            const PathDTO = PathMapper.toDTO(Path) as IPathDTO;
            return Result.ok<IPathDTO>(PathDTO);
        } catch (err) {
            throw err;
        }
    }
}