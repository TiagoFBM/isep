using System.Collections.Generic;
using mdv.Domain.Vehicles;
using mdv.DTO.Vehicles;

namespace mdv.Mappers
{
    public class VehicleMapper
    {

        public VehicleDTO DomainToDTO(Vehicle vehicle)
        {
            var vehicleDTO = new VehicleDTO(
                    vehicle.Id.AsGuid(),
                    vehicle.registration.vehicleRegistration,
                    vehicle.vin.vin,
                    vehicle.entranceDate.vehicleEntranceDate,
                    vehicle.vehicleType.id
                );

            return vehicleDTO;
        }

    }
}