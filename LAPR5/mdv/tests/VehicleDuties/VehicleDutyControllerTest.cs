using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using mdv.Controllers;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Trips;
using mdv.DTO.VehicleDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Vehicles;
using mdv.Services;
using mdv.Domain.Shared;
using Moq;
using Xunit;

namespace Tests.VehicleDuties {
    public class VehicleDutiesControllerTest {

        [Fact]
        public async void GetAll() {
            var vehicleDutyServiceMock = new Mock<IVehicleDutyService>();
            var workBlockServiceMock = new Mock<IWorkBlockService>();

            var wbList = new List<WorkBlockDTO>();

            var vdCode = "vd1";

            var vehicleDutyDTO = new VehicleDutyDTO(Guid.NewGuid(), vdCode, wbList);
            var vehicleDutiesDTO = new List<VehicleDutyDTO>() {vehicleDutyDTO};

            vehicleDutyServiceMock.Setup(_ => _.GetAll()).ReturnsAsync(vehicleDutiesDTO);
            var controller = new VehicleDutyController(vehicleDutyServiceMock.Object, workBlockServiceMock.Object);

            var actual = await controller.GetAll();

            Assert.Equal(vehicleDutiesDTO, actual.Value);
        }

    }
}