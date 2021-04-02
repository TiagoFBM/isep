using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Domain.Times;
using mdv.Utils.UtilsTime;

namespace mdv.Domain.WorkBlocks {
    public class WorkBlock : Entity<WorkBlockID>, IAggregateRoot {        
        public List<Trip> tripList { get; private set; }

        public Time startingTime { get; private set; }

        public Time endingTime { get; private set; }

        public WorkBlock () { }
        public WorkBlock (string startingTime, string endingTime, List<Trip> tripList) {
            if (StringValidator.isStringEmptyOrNull (startingTime)) {
                throw new BusinessRuleValidationException (startingTime + " invalid: Work Block starting time can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (endingTime)) {
                throw new BusinessRuleValidationException (endingTime + " invalid: Work Block ending time can't be null or empty.");
            }

            this.Id = new WorkBlockID(Guid.NewGuid());
            this.startingTime = new Time(startingTime);
            this.endingTime = new Time(endingTime);
            this.tripList = new List<Trip>(tripList);
        }

        public WorkBlock (string startingTime, string endingTime) {
            if (StringValidator.isStringEmptyOrNull (startingTime)) {
                throw new BusinessRuleValidationException (startingTime + " invalid: Work Block starting time can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (endingTime)) {
                throw new BusinessRuleValidationException (endingTime + " invalid: Work Block ending time can't be null or empty.");
            }

            this.Id = new WorkBlockID(Guid.NewGuid());
            this.startingTime = new Time(startingTime);
            this.endingTime = new Time(endingTime);
            this.tripList = new List<Trip>();
        }
    }
}