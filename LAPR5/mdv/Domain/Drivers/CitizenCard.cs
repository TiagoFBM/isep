using System;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Drivers
{

    public class CitizenCard : Entity<CitizenCardId>
    {

        public Name driverName { get; private set; }

        public BirthDate birthDate { get; private set; }

        public CitizenCardNumber citizenCardNumber { get; private set; }

        public DriverNIF driverNIF { get; private set; }


        public CitizenCard() { }

        public CitizenCard(string driverName, string birthDate, long citizenCardNumber, long driverNIF)
        {


            if (StringValidator.isStringEmptyOrNull(driverName))
            {
                throw new BusinessRuleValidationException(driverName + " invalid: Driver Name: can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull(birthDate))
            {
                throw new BusinessRuleValidationException(birthDate + " invalid: Driver Birth Date: can't be null or empty.");
            }

            this.Id = new CitizenCardId(Guid.NewGuid());
            this.driverName = new Name(driverName);
            this.birthDate = new BirthDate(birthDate);
            this.citizenCardNumber = new CitizenCardNumber(citizenCardNumber);
            this.driverNIF = new DriverNIF(driverNIF);

        }


        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                CitizenCard citizenCard = (CitizenCard)obj;
                return (this.driverName.Equals(citizenCard.driverName)) &&
                (this.birthDate.Equals(citizenCard.birthDate)) &&
                (this.citizenCardNumber.Equals(citizenCard.citizenCardNumber)) &&
                (this.driverNIF.Equals(citizenCard.driverNIF));
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(driverName, birthDate, citizenCardNumber, driverNIF);
        }
    }

}