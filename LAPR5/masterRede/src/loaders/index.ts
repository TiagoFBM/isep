import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import mongooseLoader from './mongoose';
import Logger from './logger';

import config from '../../config';

export default async ({ expressApp }) => {
  const mongoConnection = await mongooseLoader();
  Logger.info('✌️ DB loaded and connected!');

  const driverTypeSchema = {
    name: config.schemas.driverType.name,
    schema: config.schemas.driverType.schema
  }

  const driverTypeController = {
    name: config.controllers.driverType.name,
    path: config.controllers.driverType.path
  }

  const driverTypeRepo = {
    name: config.repos.driverType.name,
    path: config.repos.driverType.path
  }

  const driverTypeService = {
    name: config.services.driverType.name,
    path: config.services.driverType.path
  }

  const vehicleTypeSchema = {
    name: config.schemas.vehicleType.name,
    schema: config.schemas.vehicleType.schema
  }

  const vehicleTypeController = {
    name: config.controllers.vehicleType.name,
    path: config.controllers.vehicleType.path
  }

  const vehicleTypeRepo = {
    name: config.repos.vehicleType.name,
    path: config.repos.vehicleType.path
  }

  const vehicleTypeService = {
    name: config.services.vehicleType.name,
    path: config.services.vehicleType.path
  }

  const lineSchema = {
    name: config.schemas.line.name,
    schema: config.schemas.line.schema
  }

  const lineController = {
    name: config.controllers.line.name,
    path: config.controllers.line.path
  }

  const lineRepo = {
    name: config.repos.line.name,
    path: config.repos.line.path
  }

  const lineService = {
    name: config.services.line.name,
    path: config.services.line.path
  }
  const nodeSchema = {
    name: config.schemas.node.name,
    schema: config.schemas.node.schema
  }
  const nodeController = {
    name: config.controllers.node.name,
    path: config.controllers.node.path
  }
  const nodeRepo = {
    name: config.repos.node.name,
    path: config.repos.node.path
  }

  const nodeService = {
    name: config.services.node.name,
    path: config.services.node.path
  }

  const pathSchema = {
    name: config.schemas.path.name,
    schema: config.schemas.path.schema
  }

  const pathController = {
    name: config.controllers.path.name,
    path: config.controllers.path.path
  }

  const pathRepo = {
    name: config.repos.path.name,
    path: config.repos.path.path
  }

  const pathService = {
    name: config.services.path.name,
    path: config.services.path.path
  }

  const importService = {
    name: config.services.import.name,
    path: config.services.import.path
  }


  const importController = {
    name: config.controllers.import.name,
    path: config.controllers.import.path
  }

  await dependencyInjectorLoader({
    mongoConnection,
    schemas: [
      driverTypeSchema,
      vehicleTypeSchema,
      nodeSchema,
      lineSchema,
      pathSchema
    ],
    controllers: [
      driverTypeController,
      vehicleTypeController,
      nodeController,
      lineController,
      pathController,
      importController
    ],
    repos: [
      driverTypeRepo,
      vehicleTypeRepo,
      nodeRepo,
      lineRepo,
      pathRepo
    ],
    services: [
      driverTypeService,
      vehicleTypeService,
      nodeService,
      lineService,
      pathService,
      importService
    ]
  });
  Logger.info('✌️ Schemas, Controllers, Repositories, Services, etc. loaded');

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};