import { Service, Inject } from 'typedi';
import config from "../../config";
import ILineRepo from "../services/IRepos/ILineRepo";
import { Line } from "../domain/line/Line";
import { LineCode } from "../domain/line/LineCode";
import LineMap from "../mappers/LineMap";
import { Document, Model } from 'mongoose';
import { ILinePersistence } from '../dataschema/ILinePersistence';
import LineSchema from '../persistence/schemas/LineSchema';
import { log } from 'console';
import { Path } from '../domain/path/Path';

@Service()
export default class LineRepo implements ILineRepo {
  private models: any;

  constructor(
    @Inject(config.schemas.line.name) private LineSchema: Model<ILinePersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  public async exists(Line: Line): Promise<boolean> {

    const codeX = Line.props.code.value;

    const query = { code: codeX };
    const LineFound = await this.LineSchema.findOne(query);

    return !!LineFound === true;
  }

  public async save(Line: Line): Promise<Line> {
    const query = { code: Line.lineCode.value };

    const LineDocument = await this.LineSchema.findOne(query);

    try {
      if (LineDocument === null) {
        const rawLine: any = await LineMap.toPersistence(Line);

        let LineCreated = await this.LineSchema.create(rawLine);
        LineCreated = await this.LineSchema.populate(LineCreated, {
          path: "linePaths",
          populate: {
            path: "path",
            populate: {
              path: "segments",
              populate: { path: "firstNodeID secondNodeID" }
            }
          }
        });

        LineCreated = await this.LineSchema.populate(LineCreated, {
          path: 'allowedVehicles deniedVehicles',
          model: 'VehicleType'
        })

        LineCreated = await this.LineSchema.populate(LineCreated, {
          path: 'allowedDrivers deniedDrivers',
          model: 'DriverType'
        })

        let lineDomain = LineMap.toDomain(LineCreated);

        return lineDomain;
      } else {
        //TODO
        LineDocument.description = Line.props.description.value;
        await LineDocument.save();

        return Line;
      }
    } catch (err) {
      throw err;
    }
  }

  public async findByCode(LineCode: LineCode | string): Promise<Line> {
    const query = { code: LineCode.toString() };
    let LineRecord = await this.LineSchema.findOne(query).populate({
      path: "linePaths",
      populate: {
        path: "path",
        populate: {
          path: "segments",
          populate: { path: "firstNodeID secondNodeID" }
        }
      }
    });

    LineRecord = await this.LineSchema.populate(LineRecord, {
      path: 'allowedVehicles deniedVehicles',
      model: 'VehicleType'
    })

    LineRecord = await this.LineSchema.populate(LineRecord, {
      path: 'allowedDrivers deniedDrivers',
      model: 'DriverType'
    })

    if (LineRecord != null) {
      return LineMap.toDomain(LineRecord);
    }
    else
      return null;
  }

  public async findAll({ skip = 0, limit = 10 }: any): Promise<Array<Line>> {
    let records = await this.LineSchema.find({}).populate({
      path: "linePaths",
      populate: {
        path: "path",
        populate: {
          path: "segments",
          populate: { path: "firstNodeID secondNodeID" }
        }
      }
    }).skip(parseInt(skip)).limit(parseInt(limit));

    records = await this.LineSchema.populate(records, {
      path: 'allowedVehicles deniedVehicles',
      model: 'VehicleType'
    })

    records = await this.LineSchema.populate(records, {
      path: 'allowedDrivers deniedDrivers',
      model: 'DriverType'
    })

    var arr = [];
    records.forEach(elem => {
      arr.push(LineMap.toDomain(elem));
    });
    return arr
  }

  public async findAllLines(): Promise<Array<Line>> {
    let records = await this.LineSchema.find({}).populate({
      path: "linePaths",
      populate: {
        path: "path",
        populate: {
          path: "segments",
          populate: { path: "firstNodeID secondNodeID" }
        }
      }
    });

    records = await this.LineSchema.populate(records, {
      path: 'allowedVehicles deniedVehicles',
      model: 'VehicleType'
    })

    records = await this.LineSchema.populate(records, {
      path: 'allowedDrivers deniedDrivers',
      model: 'DriverType'
    })

    var arr = [];
    records.forEach(elem => {
      arr.push(LineMap.toDomain(elem));
    });
    return arr
  }

  public async getNumberOfDocuments(): Promise<Number> {
    const numberOfDocuments = await this.LineSchema.count({});

    return numberOfDocuments;
  }

  public async findLineByPath(PathId: string): Promise<string> {
    let allLines = await this.findAllLines();
    
    for (let i = 0; i < allLines.length; i++) {
      const line = allLines[i];
      const linePaths = line.linePaths;

      for (let k = 0; k < linePaths.length; k++) {
        let path = linePaths[k].path as Path;
        let pathCode = path.pathCode.toString();
        if (pathCode == PathId){
          return line.lineCode.toString();
        }
      }   
    }
    return "";
  }
}