import { Service, Inject } from 'typedi';
import { Result } from '../core/logic/Result';
import IImportService from './IServices/IImportService';
import { File } from "multer";
import { promises as fs } from "fs";
import parser from "xml2json";

@Service()
export default class ImportService implements IImportService {

    public async toJSON(req: File): Promise<JSON> {

        var data = await fs.readFile('uploads/' + req.filename, "utf8");
        var json = JSON.parse(parser.toJson(data));
        fs.unlink('uploads/' + req.filename);
        return json;

    }
}