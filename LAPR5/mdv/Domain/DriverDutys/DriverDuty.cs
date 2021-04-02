using System;
using System.Collections.Generic;
using mdv.Domain.WorkBlocks;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.DriverDutys
{

    public class DriverDuty : Entity<DriverDutyId>, IAggregateRoot
    {
        public DriverDutyCode driverDutyCode;

        public List<WorkBlock> listWorkBlocks;

        public DriverDuty()
        {

        }

        public DriverDuty(string driverDutyCode, List<WorkBlock> listWorkBlocks)
        {
            if (StringValidator.isStringEmptyOrNull(driverDutyCode))
            {
                throw new BusinessRuleValidationException(driverDutyCode + " invalid: The driver duty code can't be null or empty");
            }


            if (listWorkBlocks.Count == 0)
            {
                throw new BusinessRuleValidationException(listWorkBlocks + " invalid: The workblocks list can't be null or empty");
            }

            // list of workblocks are greather than 4 hours
            if (!checkIfWorkBlockIsGreatherThan8Hours(listWorkBlocks))
            {
                throw new BusinessRuleValidationException(listWorkBlocks + " invalid: The workblocks list doesn't respect all the rules ");
            }


            this.Id = new DriverDutyId(Guid.NewGuid());
            this.driverDutyCode = new DriverDutyCode(driverDutyCode);
            this.listWorkBlocks = listWorkBlocks;

        }

        public int calculateHours(String[] initialHours, String[] finalHours)
        {
            var initialTime = Int32.Parse(initialHours[0]) * 3600 + Int32.Parse(initialHours[1]) * 60 + Int32.Parse(initialHours[2]);
            var finalTime = Int32.Parse(finalHours[0]) * 3600 + Int32.Parse(finalHours[1]) * 60 + Int32.Parse(finalHours[2]);

            var duration = (finalTime - initialTime);

            return duration;
        }

        public int getDurationOfWorkIndividualy(WorkBlock workBlock)
        {
            var initialHours = workBlock.startingTime.time.Split(':');
            var finalHours = workBlock.endingTime.time.Split(':');

            var duration = calculateHours(initialHours, finalHours);

            return duration;
        }

        public bool checkHoursStraight(List<string> startingTimes, List<string> endingTimes)
        {
            var initialHours = startingTimes[0].Split(':');
            var finalHours = endingTimes[0].Split(':');
            var numberMaxStraightHours = 4 * 3600;

            var duration = calculateHours(initialHours, finalHours);


            if (duration > numberMaxStraightHours)
            {
                return false;
            }

            if (duration == numberMaxStraightHours && startingTimes[1] == endingTimes[0])
            {
                return false;
            }

            var n = startingTimes.Count;
            for (int i = 1; i < n; i++)
            {

                if (startingTimes[i] == endingTimes[i - 1])
                {
                    initialHours = startingTimes[i].Split(':');
                    finalHours = endingTimes[i].Split(':');

                    duration += calculateHours(initialHours, finalHours);

                    if ((duration == numberMaxStraightHours && (i + 1) <= startingTimes.Count && startingTimes[i + 1] == endingTimes[i]))
                    {
                        return false;
                    }

                }
                else
                {
                    initialHours = startingTimes[i].Split(':');
                    finalHours = endingTimes[i].Split(':');

                    duration = calculateHours(initialHours, finalHours);

                    if (duration > numberMaxStraightHours)
                    {
                        return false;
                    }


                }
            }
            return true;

        }

        public bool checkIfWorkBlockIsGreatherThan8Hours(List<WorkBlock> workBlocks)
        {
            List<string> startingTimes = new List<string>();
            List<string> endingTimes = new List<string>();
            var duration = 0;
            var DURACAO_MAXIMA = 8 * 3600;
            foreach (var workBlock in workBlocks)
            {
                duration += getDurationOfWorkIndividualy(workBlock);
                startingTimes.Add(workBlock.startingTime.time);
                endingTimes.Add(workBlock.endingTime.time);
            }

            if (!checkHoursStraight(startingTimes, endingTimes))
            {
                return false;
            }

            return duration <= DURACAO_MAXIMA;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverDuty driverDuty = (DriverDuty)obj;
                return (this.driverDutyCode.Equals(driverDuty.driverDutyCode));
            }

        }

        public override int GetHashCode()
        {
            return HashCode.Combine(driverDutyCode, listWorkBlocks);
        }



    }

}