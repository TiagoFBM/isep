using System;
using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Domain.VehicleTypes;

namespace mdv.Domain.Vehicles
{

    public class Vehicle : Entity<VehicleID>, IAggregateRoot
    {
        public Registration registration { get; private set; }

        public VIN vin { get; private set; }

        public VehicleEntranceDate entranceDate { get; private set; }

        public VehicleTypeID vehicleType { get; private set; }

        public Vehicle() { }

        public Vehicle(string registration, string vin, string entranceDate, string vehicleType)
        {
            if (StringValidator.isStringEmptyOrNull(registration))
            {
                throw new BusinessRuleValidationException(registration + " invalid: Vehicle Registration: can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull(vin))
            {
                throw new BusinessRuleValidationException(vin + " invalid: Vehicle Identification Number: can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull(entranceDate))
            {
                throw new BusinessRuleValidationException(entranceDate + " invalid: Entrance Date: can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull(vehicleType))
            {
                throw new BusinessRuleValidationException(entranceDate + " invalid: Vehicle Type: can't be null or empty.");
            }

            this.Id = new VehicleID(Guid.NewGuid());
            this.registration = new Registration(registration);
            this.vin = new VIN(vin);
            this.entranceDate = new VehicleEntranceDate(entranceDate);
            this.vehicleType = new VehicleTypeID(vehicleType);
        }
    }

}