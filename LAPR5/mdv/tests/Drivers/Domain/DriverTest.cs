using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using Xunit;

namespace Tests.Drivers
{
    public class DriverTest
    {

        [Fact]
        public void CreateValidDriver()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "27/01/1978";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            var driver = new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes);
            Assert.True(driver.GetType().Equals(new Driver().GetType()));
        }

        [Fact]
        public void CreateDriverWithNullMecanographicNumber()
        {
            string mecanographicNumber = null;
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "27/01/1978";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithEmptyMecanographicNumber()
        {
            string mecanographicNumber = "";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "27/01/1978";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithNullEntranceDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, null, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithEmptyEntranceDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithInvalidFormatEntranceDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "12-01-2020";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithNullDepartureDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, null, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithEmptyDepartureDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "31/05/2020";
            string departureDate = "";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));
        }


        [Fact]
        public void CreateDriverWithInvalidFormatDepartureDate()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "12/01/2020";
            string departureDate = "31-05-2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));

        }

        [Fact]
        public void CreateDriverWithNullDriverTypesList()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "31/05/2020";
            string departureDate = "";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, null));
        }

        [Fact]
        public void CreateDriverWithEmptyDriverTypesList()
        {
            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "31/05/2020";
            string departureDate = "";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            Assert.Throws<BusinessRuleValidationException>(() => new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes));
        }
    }

}
