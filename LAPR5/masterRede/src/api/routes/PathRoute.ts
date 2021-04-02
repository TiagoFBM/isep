import { Router } from "express";
import config from "../../../config";
import IPathController from "../../controllers/IControllers/IPathController";
import { Container } from 'typedi';

const router = Router();

export default(app: Router) => {
    app.use("/path", router);
    
    const ctrl = Container.get(config.controllers.path.name) as IPathController;
    router.post("", (req, res, next) => ctrl.createPath(req, res, next));
    router.get("", (req, res, next) => ctrl.findAllPaths(req, res, next));
    router.get("/count", (req, res, next) => ctrl.getNumberOfPaths(req, res, next));
    router.get("/:code", (req, res, next) => ctrl.getPathByCode(req, res, next));
}