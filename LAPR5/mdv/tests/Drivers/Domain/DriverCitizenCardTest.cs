using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Drivers;
using Xunit;


namespace Tests.Drivers
{
    public class DriverCitizenCardTest
    {

        [Fact]
        public void CreateValidCC()
        {
            string driverName = "DTeste1";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            var cc = new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF);

            Assert.True(cc.GetType().Equals(new CitizenCard().GetType()));

        }

        [Fact]
        public void CreateCCWithNullDriverName()
        {
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(null, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithEmptyDriverName()
        {
            string driverName = "";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithNullBirthDate()
        {
            string driverName = "DTeste1";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, null, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithEmptyBirthDate()
        {
            string driverName = "DTeste1";
            string birthDate = "";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithInvalidFormatBirthDate()
        {
            string driverName = "DTeste1";
            string birthDate = "12-12-1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCUnderEighteenBirthDate()
        {
            string driverName = "DTeste1";
            string birthDate = "12/12/2003";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithNegativeCCNumber()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = -11144477;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithLessThanEightNumbersCCNumber()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = 11144;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));
        }

        [Fact]
        public void CreateCCWithMoreThanEightNumbersCCNumber()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = 11144123121;
            long driverNIF = 159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));
        }

        [Fact]
        public void CreateCCWithNegativeDriverNIF()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = 11144477;
            long driverNIF = -159951159;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));

        }

        [Fact]
        public void CreateCCWithLessThanNineNumbersNIF()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = 11144477;
            long driverNIF = 15995;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));
        }

        [Fact]
        public void CreateCCWithMoreThanNineNumbersNIF()
        {
            string driverName = "DTeste1";
            string birthDate = "14/02/2000";
            long citizenCardNumber = 11144477;
            long driverNIF = 15995115912313;

            Assert.Throws<BusinessRuleValidationException>(() => new CitizenCard(driverName, birthDate, citizenCardNumber, driverNIF));
        }

    }
}

