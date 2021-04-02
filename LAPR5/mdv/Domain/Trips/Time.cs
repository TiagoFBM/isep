using System;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Times {
    public class Time : IValueObject {
        public string time { get; private set; }

        public Time () { }
        public Time (string time) {

            if (StringValidator.isStringEmptyOrNull (time)) {
                throw new BusinessRuleValidationException (time + " invalid: Trip Departure Time can't be null or empty.");
            }

            if (!StringValidator.isValidTimestamp (time)) {
                throw new BusinessRuleValidationException (time + " invalid: Trip Departure Time must be in the format HH:MM:SS.");
            }

            var vecTime = time.Split (":");
            if (Int32.Parse (vecTime[0]) >= 24) {
                vecTime[0] = "0";
            }

            this.time = vecTime[0] + ":" + vecTime[1] + ":" + vecTime[2];
        }

        public Time (int timeInSeconds) {
            if (timeInSeconds >= 86400) {
                timeInSeconds = timeInSeconds - 86400;
            }
            int hours = timeInSeconds / 3600;
            int min = (timeInSeconds % 3600) / 60;
            int sec = (timeInSeconds % 3600) % 60;

            this.time = hours + ":" + min + ":" + sec;
        }

        public override string ToString () {
            return time;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                Time Time = (Time) obj;
                return (this.time.Equals (Time.time));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (time);
        }
    }
}