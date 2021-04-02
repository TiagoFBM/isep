import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import mongooseLoader from './mongoose';
import Logger from './logger';

import config from '../../config';

export default async ({ expressApp }) => {
  const mongoConnection = await mongooseLoader();
  Logger.info('✌️ DB loaded and connected!');

  const moduleSchema = {
    name: config.schemas.module.name,
    schema: config.schemas.module.schema
  }

  const moduleController = {
    name: config.controllers.module.name,
    path: config.controllers.module.path
  }

  const moduleRepo = {
    name: config.repos.module.name,
    path: config.repos.module.path
  }

  const moduleService = {
    name: config.services.module.name,
    path: config.services.module.path
  }

  const userSchema = {
    name: config.schemas.user.name,
    schema: config.schemas.user.schema
  }

  const userController = {
    name: config.controllers.user.name,
    path: config.controllers.user.path
  }

  const userRepo = {
    name: config.repos.user.name,
    path: config.repos.user.path
  }

  const userService = {
    name: config.services.user.name,
    path: config.services.user.path
  }

  const authenticationController = {
    name: config.controllers.authentication.name,
    path: config.controllers.authentication.path
  }

  // const vehicleTypeSchema = {
  //   name: config.schemas.vehicleType.name,
  //   schema: config.schemas.vehicleType.schema
  // }

  // const vehicleTypeController = {
  //   name: config.controllers.vehicleType.name,
  //   path: config.controllers.vehicleType.path
  // }

  // const vehicleTypeRepo = {
  //   name: config.repos.vehicleType.name,
  //   path: config.repos.vehicleType.path
  // }

  // const vehicleTypeService = {
  //   name: config.services.vehicleType.name,
  //   path: config.services.vehicleType.path
  // }

  // const lineSchema = {
  //   name: config.schemas.line.name,
  //   schema: config.schemas.line.schema
  // }

  // const lineController = {
  //   name: config.controllers.line.name,
  //   path: config.controllers.line.path
  // }

  // const lineRepo = {
  //   name: config.repos.line.name,
  //   path: config.repos.line.path
  // }

  // const lineService = {
  //   name: config.services.line.name,
  //   path: config.services.line.path
  // }
  // const nodeSchema = {
  //   name: config.schemas.node.name,
  //   schema: config.schemas.node.schema
  // }
  // const nodeController = {
  //   name: config.controllers.node.name,
  //   path: config.controllers.node.path
  // }
  // const nodeRepo = {
  //   name: config.repos.node.name,
  //   path: config.repos.node.path
  // }

  // const nodeService = {
  //   name: config.services.node.name,
  //   path: config.services.node.path
  // }

  // const pathSchema = {
  //   name: config.schemas.path.name,
  //   schema: config.schemas.path.schema
  // }

  // const pathController = {
  //   name: config.controllers.path.name,
  //   path: config.controllers.path.path
  // }

  // const pathRepo = {
  //   name: config.repos.path.name,
  //   path: config.repos.path.path
  // }

  // const pathService = {
  //   name: config.services.path.name,
  //   path: config.services.path.path
  // }

  // const importService = {
  //   name: config.services.import.name,
  //   path: config.services.import.path
  // }


  // const importController = {
  //   name: config.controllers.import.name,
  //   path: config.controllers.import.path
  // }

  await dependencyInjectorLoader({
    mongoConnection,
    schemas: [
      moduleSchema,
      userSchema
      // vehicleTypeSchema,
      // nodeSchema,
      // lineSchema,
      // pathSchema
    ],
    controllers: [
      moduleController,
      userController,
      authenticationController
      // vehicleTypeController,
      // nodeController,
      // lineController,
      // pathController,
      // importController
    ],
    repos: [
      moduleRepo,
      userRepo
      // vehicleTypeRepo,
      // nodeRepo,
      // lineRepo,
      // pathRepo
    ],
    services: [
      moduleService,
      userService
      // vehicleTypeService,
      // nodeService,
      // lineService,
      // pathService,
      // importService
    ]
  });
  Logger.info('✌️ Schemas, Controllers, Repositories, Services, etc. loaded');

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};