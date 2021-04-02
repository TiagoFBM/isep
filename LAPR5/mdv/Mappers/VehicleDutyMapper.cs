using System.Collections.Generic;
using mdv.Domain.VehicleDutys;
using mdv.DTO.VehicleDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.Mappers;

namespace mdv.Mappers {
    public class VehicleDutyMapper {

        private readonly TripMapper _tripMapper;
        private readonly VehicleMapper _vehicleMapper;
        private readonly WorkBlockMapper _workBlockMapper;

        public VehicleDutyMapper() {
            _tripMapper = new TripMapper();
            _vehicleMapper = new VehicleMapper();
            _workBlockMapper = new WorkBlockMapper();
        }

        public VehicleDutyDTO DomainToDTO (VehicleDuty vehicleDuty) {
            var tripDTOList = new List<TripDTO> ();

            foreach (var trip in vehicleDuty.tripsList) {
                var tripDTO = _tripMapper.DomainToDTO(trip);
                tripDTOList.Add(tripDTO);
            }

            var workBlockDTOList = new List<WorkBlockDTO> ();
            foreach (var wb in vehicleDuty.workBlockList) {
                var wbDTO = _workBlockMapper.DomainToDTO(wb);
                workBlockDTOList.Add(wbDTO);
            }

            var vehicleDutyDTO = new VehicleDutyDTO (
                vehicleDuty.Id.AsGuid (),
                vehicleDuty.vehicleDutyCode.ToString(),
                tripDTOList,
                workBlockDTOList
            );

            return vehicleDutyDTO;
        }

    }
}