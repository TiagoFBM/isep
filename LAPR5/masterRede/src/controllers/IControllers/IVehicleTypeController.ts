import { Request, Response, NextFunction } from 'express';

export default interface IVehicleTypeController {
    createVehicleType(req: Request, res: Response, next: NextFunction);
    findAllVehicleTypes(req: Request, res: Response, next: NextFunction);
    getNumberOfVehicleTypes(req: Request, res: Response, next: NextFunction);
    getVehicleTypeByCode(req: Request, res: Response, next: NextFunction);
}