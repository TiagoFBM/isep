using mdv.Domain.Vehicles;
using mdv.Domain.VehicleTypes;
using mdv.DTO.Vehicles;
using mdv.DTO.VehicleTypes;
using mdv.Mappers;
using System.Collections.Generic;
using Xunit;

namespace Tests.Drivers
{
    public class VehicleMapperTest
    {

        [Fact]
        public void DTOtoDomain()
        {
            var mapper = new VehicleMapper();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);


            var expected = new VehicleDTO(
                vehicle.Id.AsGuid(),
                vehicle.registration.vehicleRegistration,
                vehicle.vin.vin,
                vehicle.entranceDate.vehicleEntranceDate,
                vehicle.vehicleType.id);

            var actual = mapper.DomainToDTO(vehicle);


        }
    }
}