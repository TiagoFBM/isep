using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.Vehicles;
using mdv.DTO.Vehicles;
using mdv.Services;
using Microsoft.AspNetCore.Mvc;

namespace mdv.Controllers {
    [Route ("api/[controller]")]
    [ApiController]

    public class VehicleController : ControllerBase
    {
        private readonly IVehicleService vehicleService;
        private readonly IVehicleTypeService vehicleTypeService;

        public VehicleController(IVehicleService service, IVehicleTypeService vehicleTypeService)
        {
            this.vehicleService = service;
            this.vehicleTypeService = vehicleTypeService;
        }

        [HttpPost]
        public async Task<ActionResult<VehicleDTO>> Create (VehicleDTO dto) {
            try {

                var vehicleType = await vehicleTypeService.GetVehicleTypeByID (dto.vehicleType.ToString ());

                var vehicle = await vehicleService.AddVehicle (dto);

                return CreatedAtAction (
                    nameof (GetById),
                    new { id = vehicle.Id },
                    vehicle
                );

            } catch (BusinessRuleValidationException ex) {
                return BadRequest (new { Message = ex.Message });
            }

        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<VehicleDTO>>> GetAll () {
            return await this.vehicleService.GetAll ();
        }

        [HttpGet ("pages")]
        public async Task<ActionResult<IEnumerable<VehicleDTO>>> GetAll (int skip, int limit) {
            var list = await this.vehicleService.GetAll ();

            return list.Skip (skip).Take (limit).ToList ();
        }

        [HttpGet ("count")]
        public async Task<ActionResult<int>> GetNumber () {
            return await this.vehicleService.GetNumber ();
        }

        [HttpGet ("{id}")]
        public async Task<ActionResult<VehicleDTO>> GetById (Guid id) {
            var vehicle = await vehicleService.GetById (new VehicleID (id));

            if (vehicle == null) {
                return NotFound ();
            }

            return vehicle;
        }

    }
}