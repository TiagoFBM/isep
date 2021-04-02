using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using mdv.Controllers;
using mdv.Mappers;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;
using mdv.Domain.Trips;
using mdv.Domain.Shared;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.VehicleDutys;
using mdv.Domain.Vehicles;
using mdv.DTO.DriverDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.DTO.Nodes;
using mdv.DTO.VehicleDutys;
using mdv.Services;
using mdv.Service.HttpRequests;
using mdv.Repository.DriverDutys;
using mdv.Repository.WorkBlocks;
using mdv.Repository.VehicleDutys;
using Moq;
using Xunit;
using System;

namespace Tests.DriverDuties
{
    public class DriverDutyServiceTest
    {

        [Fact]
        public async void GetAll()
        {
            var repo = new Mock<IDriverDutyRepository>();
            var repoWorkBlocks = new Mock<IWorkBlockRepository>();
            var repoVehicleDutyRepository = new Mock<IVehicleDutyRepository>();
            var uow = new Mock<IUnitOfWork>();

            string driverDutyCode = "dDutycode1";
            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            var listDriverDTOs = new List<DriverDutyDTO>() { driverDutyDTO };

            var listDrivers = new List<DriverDuty>() { driverDuty };

            repo.Setup(_ => _.GetAllAsync()).ReturnsAsync(listDrivers);

            var service = new DriverDutyService(repo.Object, repoWorkBlocks.Object, repoVehicleDutyRepository.Object, uow.Object);

            var actual = await service.GetAll();

            Assert.Equal(listDriverDTOs, actual);


        }

        [Fact]
        public async void GetById()
        {
            var repo = new Mock<IDriverDutyRepository>();
            var repoWorkBlocks = new Mock<IWorkBlockRepository>();
            var repoVehicleDutyRepository = new Mock<IVehicleDutyRepository>();
            var uow = new Mock<IUnitOfWork>();

            string driverDutyCode = "dDutycode1";

            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            repo.Setup(_ => _.GetByIdAsync(driverDuty.Id)).ReturnsAsync(driverDuty);

            var service = new DriverDutyService(repo.Object, repoWorkBlocks.Object, repoVehicleDutyRepository.Object, uow.Object);

            var actual = await service.GetById(driverDuty.Id);

            Assert.Equal(driverDutyDTO, actual);


        }

        [Fact]
        public async void AddDriverDuty()
        {
            var repo = new Mock<IDriverDutyRepository>();
            var repoWorkBlocks = new Mock<IWorkBlockRepository>();
            var repoVehicleDutyRepository = new Mock<IVehicleDutyRepository>();

            var uow = new Mock<IUnitOfWork>();

            string driverDutyCode = "dDutycode1";
            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };

            var tripsDTOString = new List<string>() { trip.Id.ToString() };


            var cworkBlockDTO1 = new CreatingWorkBlockDTO(tripsDTOString, workBlock1.startingTime.time, workBlock1.endingTime.time);
            var cworkBlockDTO2 = new CreatingWorkBlockDTO(tripsDTOString, workBlock2.startingTime.time, workBlock2.endingTime.time);
            var cworkBlockDTO3 = new CreatingWorkBlockDTO(tripsDTOString, workBlock3.startingTime.time, workBlock3.endingTime.time);


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            var creatingDriverDutyDTO = new CreatingDriverDutyDTO(driverDutyCode, listWorkBlocksString);

            var workBlockID1 = new WorkBlockID(workBlockDTO1.Id);
            var workBlockID2 = new WorkBlockID(workBlockDTO2.Id);
            var workBlockID3 = new WorkBlockID(workBlockDTO3.Id);

            string vehicleDutyCode = "code:1";

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";

            Vehicle vehicle = new Vehicle(registration, vin, entranceDate, vehicleType);


            VehicleDuty vehicleDuty = new VehicleDuty(vehicleDutyCode, tripList);

            repoWorkBlocks.Setup(_ => _.GetByIdAsync(workBlockID1)).ReturnsAsync(workBlock1);

            repoWorkBlocks.Setup(_ => _.GetByIdAsync(workBlockID2)).ReturnsAsync(workBlock2);

            repoWorkBlocks.Setup(_ => _.GetByIdAsync(workBlockID3)).ReturnsAsync(workBlock3);

            repoVehicleDutyRepository.Setup(_ => _.getVehicleDutyByWorkblock(workBlock1)).ReturnsAsync(vehicleDuty);

            repoVehicleDutyRepository.Setup(_ => _.getVehicleDutyByWorkblock(workBlock2)).ReturnsAsync(vehicleDuty);

            repoVehicleDutyRepository.Setup(_ => _.getVehicleDutyByWorkblock(workBlock3)).ReturnsAsync(vehicleDuty);

            repo.Setup(_ => _.AddAsync(driverDuty)).ReturnsAsync(driverDuty);

            var service = new DriverDutyService(repo.Object, repoWorkBlocks.Object, repoVehicleDutyRepository.Object, uow.Object);

            var actual = await service.AddDriverDuty(creatingDriverDutyDTO);

            Assert.Equal(driverDutyDTO, actual);

        }



    }
}