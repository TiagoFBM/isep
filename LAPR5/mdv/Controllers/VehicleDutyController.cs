using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.VehicleDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.Services;
using mdv.DTO.VehicleDutys;
using Microsoft.AspNetCore.Mvc;
using mdv.Utils.UtilsTime;
using mdv.Domain.Times;
using mdv.Domain.WorkBlocks;

namespace mdv.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class VehicleDutyController : ControllerBase
    {
        private readonly IVehicleDutyService vehicleDutyService;
        private readonly IWorkBlockService workblockService;

        public VehicleDutyController (IVehicleDutyService service, IWorkBlockService workblockService) {
            this.vehicleDutyService = service;
            this.workblockService = workblockService;
        }

        [HttpPost]
        public async Task<ActionResult<VehicleDutyDTO>> Create(CreatingVehicleDutyDTO dto)
        {
            try
            {
                var vehicleDuty = await vehicleDutyService.AddVehicleDuty(dto);

                return CreatedAtAction(
                    nameof(GetById),
                    new { id = vehicleDuty.Id },
                    vehicleDuty
                );

            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }

        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<VehicleDutyDTO>>> GetAll()
        {
            return await this.vehicleDutyService.GetAll();
        }

        [HttpGet("pages")]
        public async Task<ActionResult<IEnumerable<VehicleDutyDTO>>> GetAll(int skip, int limit)
        {
            var list = await this.vehicleDutyService.GetAll();

            return list.Skip(skip).Take(limit).ToList();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<VehicleDutyDTO>> GetById(Guid id)
        {
            var vehicleDuty = await vehicleDutyService.GetById(new VehicleDutyId(id));

            if (vehicleDuty == null)
            {
                return NotFound();
            }

            return vehicleDuty;
        }

        [HttpGet("workblock/{id}")]
        public async Task<ActionResult<WorkBlockDTO>> GetWorkBlockById(Guid id)
        {
            var workBlock = await vehicleDutyService.GetWorkBlockById(new WorkBlockID(id));
            if (workBlock == null)
            {
                return NotFound();
            }

            return workBlock;
        }

        [HttpPost("workblock")]
        public async Task<ActionResult<VehicleDutyDTO>> createWorkblocks(PostWorkBlockDTO dto)
        {
            try
            {
                var vehicleDuty = await vehicleDutyService.GetById(new VehicleDutyId(dto.vehicleDutyId));
                if (vehicleDuty == null)
                {
                    throw new Exception("cannot be null");
                }
                List<TripDTO> tripList = vehicleDuty.tripList;

                tripList.Sort(delegate (TripDTO x, TripDTO y)
                {
                    int a = TimeUtils.fromStringToSec(x.tripDepartureTime);
                    int b = TimeUtils.fromStringToSec(y.tripDepartureTime);
                    int c = a.CompareTo(b);
                    return c;
                });

                List<int> possibleWorkBlocks = new List<int>();
                foreach (var config in dto.workblockConfigList)
                {
                    for (int i = 0; i < config.maximumNumber; i++)
                    {
                        possibleWorkBlocks.Add(config.workBlockMaxDuration);
                    }
                }

                List<WorkBlockAux> workBlocksToAdd = new List<WorkBlockAux>();
                foreach (var trip in tripList)
                {
                    var nodePassages = trip.nodePassageListDTO;
                    nodePassages.Sort(delegate (NodePassageDTO x, NodePassageDTO y)
                    {
                        int a = TimeUtils.fromStringToSec(x.passageTime);
                        int b = TimeUtils.fromStringToSec(y.passageTime);
                        int c = a.CompareTo(b);
                        return c;
                    });

                    var lastNodePassage = nodePassages[nodePassages.Count - 1].passageTime;
                    var dif = TimeUtils.timeStringDifferenceInSeconds(lastNodePassage, trip.tripDepartureTime);
                    for (int i = 0; i < possibleWorkBlocks.Count; i++)
                    {
                        if (dif <= possibleWorkBlocks[i] * 60)
                        {
                            var wbAux = getWorkBlockAuxWithPos(workBlocksToAdd, i);
                            if (wbAux == null)
                            {
                                workBlocksToAdd.Add(new WorkBlockAux(i, dif, trip));
                                break;
                            }
                            else
                            {
                                if (wbAux.sum + dif <= possibleWorkBlocks[i] * 60)
                                {
                                    wbAux.addTrip(dif, trip);
                                    break;
                                }
                            }
                        }
                    }
                }

                List<WorkBlockDTO> list = new List<WorkBlockDTO>();
                foreach (var item in workBlocksToAdd) {
                    List<string> tripIDs = new List<string>();
                    foreach (var trip in item.tripList)
                    {
                        tripIDs.Add(trip.Id.ToString());
                    }
                    var lastTrip = item.tripList[item.tripList.Count - 1];
                    var lastNodePassage = lastTrip.nodePassageListDTO[lastTrip.nodePassageListDTO.Count - 1].passageTime;
                    var creatingWorkblockDTO = new CreatingWorkBlockDTO(tripIDs, item.tripList[0].tripDepartureTime, lastNodePassage);
                    var workBlockDTO = await this.workblockService.AddWorkBlock(creatingWorkblockDTO);
                    list.Add(workBlockDTO);
                }

                var createdVDDTO = await this.vehicleDutyService.AddWorkBlocksToVehicleDuty(list, new VehicleDutyId(dto.vehicleDutyId));

                return CreatedAtAction(
                    nameof(GetById),
                    new { id = vehicleDuty.Id },
                    createdVDDTO
                );
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        private WorkBlockAux getWorkBlockAuxWithPos(List<WorkBlockAux> workBlocksToAdd, int pos)
        {
            foreach (var item in workBlocksToAdd)
            {
                if (item.pos == pos)
                {
                    return item;
                }
            }
            return null;
        }
    }
}