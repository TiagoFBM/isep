using System;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Trips {
    public class TripDepartureDate : IValueObject {
        public string tripDepartureDate { get; private set; }

        public TripDepartureDate () { }
        public TripDepartureDate (string tripDepartureDate) {

            if (StringValidator.isStringEmptyOrNull (tripDepartureDate)) {
                throw new BusinessRuleValidationException (tripDepartureDate + " invalid: Trip Departure Date can't be null or empty.");
            }

            if (!StringValidator.isValidDate (tripDepartureDate)) {
                throw new BusinessRuleValidationException (tripDepartureDate + " invalid: Trip Departure Date must be in the format DD/MM/YYYY.");
            }

            this.tripDepartureDate = tripDepartureDate;

        }

        public override string ToString () {
            return tripDepartureDate;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                TripDepartureDate date = (TripDepartureDate) obj;
                return (this.tripDepartureDate.Equals (date.tripDepartureDate));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (tripDepartureDate);
        }
    }
}