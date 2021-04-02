import dotenv from 'dotenv';
import { stringify } from 'querystring';

// Set the NODE_ENV to 'development' by default
process.env.NODE_ENV = process.env.NODE_ENV || 'development';

const envFound = dotenv.config();
if (!envFound) {
  // This error should crash whole process

  throw new Error("Couldn't find .env file.");
}

export default {
  /**
   * Your favorite port
   */
  port: parseInt(process.env.PORT, 10),

  /**
   * That long string from mlab
   */
  databaseURL: process.env.MONGODB_URI,

  /**
   * Your secret sauce
   */
  jwtSecret: process.env.JWT_SECRET,

  /**
   * Used by winston logger
   */
  logs: {
    level: process.env.LOG_LEVEL || 'silly',
  },

  /**
   * API configs
   */
  api: {
    prefix: '/api',
  },

  schemas: {
    module: {
      name: "ModuleSchema",
      schema: "../persistence/schemas/ModuleSchema"
    },

    user: {
      name: "UserSchema",
      schema: "../persistence/schemas/UserSchema"
    },

    // vehicleType: {
    //   name: "VehicleTypeSchema",
    //   schema: "../persistence/schemas/VehicleTypeSchema"
    // },

    // node: {
    //   name: "NodeSchema",
    //   schema: "../persistence/schemas/NodeSchema"
    // },

    // line: {
    //   name: "LineSchema",
    //   schema: "../persistence/schemas/LineSchema"
    // },
    // path: {
    //   name: "PathSchema",
    //   schema: "../persistence/schemas/PathSchema"
    // }
  },
  
  controllers: {
    module: {
      name: "ModuleController",
      path: "../controllers/ModuleController"
    },

    user: {
      name: "UserController",
      path: "../controllers/UserController"
    },

    authentication: {
      name: "AuthenticationController",
      path: "../controllers/AuthenticationController"
    },

    // vehicleType: {
    //   name: "VehicleTypeController",
    //   path: "../controllers/VehicleTypeController"
    // },

    // line: {
    //   name: "LineController",
    //   path: "../controllers/LineController"
    // },

    // import: {
    //   name: "ImportController",
    //   path: "../controllers/ImportController"
    // },
    // node: {
    //   name: "NodeController",
    //   path: "../controllers/NodeController"
    // },
    // line: {
    //   name: "LineController",
    //   path: "../controllers/LineController"
    // },
    // path: {
    //   name: "PathController",
    //   path: "../controllers/PathController"
    // }
    
  },

  repos: {
    module: {
      name: "ModuleRepo",
      path: "../repos/ModuleRepo"
    },

    user: {
      name: "UserRepo",
      path: "../repos/UserRepo"
    },

    // vehicleType: {
    //   name: "VehicleTypeRepo",
    //   path: "../repos/VehicleTypeRepo"
    // },
    // node: {
    //   name: "NodeRepo",
    //   path: "../repos/NodeRepo"
    // },
    // line: {
    //   name: "LineRepo",
    //   path: "../repos/LineRepo"
    // },
    

    // path: {
    //   name: "PathRepo",
    //   path: "../repos/PathRepo"
    // }
  },

  services: {
    module: {
      name: "ModuleService",
      path: "../services/ModuleService"
    },

    user: {
      name: "UserService",
      path: "../services/UserService"
    },

    // vehicleType: {
    //   name: "VehicleTypeService",
    //   path: "../services/VehicleTypeService"
    // },
    // node: {
    //   name: "NodeService",
    //   path: "../services/NodeService"
    // },

    // line: {
    //   name: "LineService",
    //   path: "../services/LineService"
    // },

    // import: {
    //   name: "ImportService",
    //   path: "../services/ImportService"
    // },

    // node: {
    //   name: "NodeService",
    //   path: "../services/NodeService"
    // },
    // path: {
    //   name: "PathService",
    //   path: "../services/PathService"
    // }
  },
};
