using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.DriverDutys;
using mdv.DTO.DriverDutys;
using mdv.Services;
using Microsoft.AspNetCore.Mvc;


namespace mdv.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class DriverDutyController : ControllerBase
    {

        private readonly IDriverDutyService driverDutyService;

        public DriverDutyController(IDriverDutyService service)
        {
            this.driverDutyService = service;
        }

        [HttpPost]
        public async Task<ActionResult<DriverDutyDTO>> Create(CreatingDriverDutyDTO driverDutyDTO)
        {
            try
            {

                var driverDuty = await driverDutyService.AddDriverDuty(driverDutyDTO);

                return CreatedAtAction(
                    nameof(GetById),
                    new { id = driverDuty.Id },
                    driverDuty
                );

            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<DriverDutyDTO>> GetById(Guid id)
        {
            var driverDuty = await driverDutyService.GetById(new DriverDutyId(id));

            if (driverDuty == null)
            {
                return NotFound();
            }

            return driverDuty;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<DriverDutyDTO>>> GetAll()
        {
            return await this.driverDutyService.GetAll();
        }

        [HttpDelete("all")]
        public async Task<List<DriverDutyDTO>> DeleteAll()
        {
            List<DriverDutyDTO> listDriverDuties = await this.driverDutyService.DeleteAllAsync();

            return listDriverDuties;

        }


        [HttpGet("pages")]
        public async Task<ActionResult<IEnumerable<DriverDutyDTO>>> GetAll(int skip, int limit)
        {
            var list = await this.driverDutyService.GetAll();

            return list.Skip(skip).Take(limit).ToList();
        }


    }

}