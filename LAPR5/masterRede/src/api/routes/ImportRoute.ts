import { Router } from 'express';

import { Container } from 'typedi';
import IImportController from '../../controllers/IControllers/IImportController';

import config from "../../../config";
import multer from "multer";
var upload = multer({ dest: 'uploads/' })

const route = Router();

export default (app: Router) => {
    app.use('/import', route);

    const ctrl = Container.get(config.controllers.import.name) as IImportController;

    route.post('',
        upload.single('file'),
        (req, res, next) => ctrl.import(req, res, next));

};