using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Vehicles {

    public class VehicleEntranceDate : IValueObject {
        public string vehicleEntranceDate { get; private set; }

        public VehicleEntranceDate () { }
        public VehicleEntranceDate (string vehicleEntranceDate) {

            

            if (!StringValidator.isValidEntranceDate (vehicleEntranceDate)) {
                throw new BusinessRuleValidationException (vehicleEntranceDate + " invalid: Vehicle Entrance Date must be in the format DD/MM/YYYY.");
            }

            this.vehicleEntranceDate = vehicleEntranceDate;

        }

        public override string ToString () {
            return vehicleEntranceDate;
        }

    }
}