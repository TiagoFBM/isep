using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Vehicles;
using mdv.Domain.VehicleTypes;
using Xunit;

namespace Tests.Vehicles
{
    public class VehicleTest
    {

        [Fact]
        public void CreateValidVehicle()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";

            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);

            Assert.True(vehicle.GetType().Equals(new Vehicle().GetType()));
        }

        [Fact]
        public void CreateVehicleWithNullRegistration()
        {
            string registration = null;
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }

        [Fact]
        public void CreateVehicleWithInvalidRegistration()
        {
            string registration = "a-1a-1";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));
        }

        [Fact]
        public void CreateVehicleWithEmptyRegistration()
        {
            string registration = "";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));
        }

        [Fact]
        public void CreateVehicleWithNullVIN()
        {
            string registration = "AA11AA";
            string vin = null;
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }

        [Fact]
        public void CreateVehicleWithInvalidVIN()
        {
            string registration = "AA11AA";
            string vin = "123";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));
        }

        [Fact]
        public void CreateVehicleWithEmptyVIN()
        {
            string registration = "AA11AA";
            string vin = "";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));
        }



        [Fact]
        public void CreateVehicleWithNullEntranceDate()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = null;
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }

        [Fact]
        public void CreateVehicleWithEmptyEntranceDate()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "";
            string vehicleType = "vehicleType1";


            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }

        [Fact]
        public void CreateVehicleWithInvalidFormatEntranceDate()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27-01-1978";
            string vehicleType = "vehicleType1";

            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));


        }

        
        [Fact]
        public void CreateVehicleWithNullVehicleType()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = null;

            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }

        [Fact]
        public void CreateDriverWithEmptyDriverTypesList()
        {
            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "";

            Assert.Throws<BusinessRuleValidationException>(() => new Vehicle(registration, vin, entranceDate, vehicleType));

        }
    }

}
