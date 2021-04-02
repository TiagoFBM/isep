using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Shared;
using mdv.Domain.Times;
using mdv.Domain.Trips;
using mdv.DTO.Paths;
using mdv.DTO.Trips;
using mdv.Services;
using mdv.Utils.UtilsTime;
using Microsoft.AspNetCore.Mvc;

namespace mdv.Controllers {

    [Route ("api/[controller]")]
    [ApiController]
    public class TripController : ControllerBase {
        private readonly ITripService tripService;
        private readonly IPathService pathService;

        public TripController (ITripService tripService, IPathService pathService) {
            this.tripService = tripService;
            this.pathService = pathService;
        }

        [HttpPost]
        public async Task<ActionResult<TripDTO>> Create (CreatingTripDTO dto) {
            try {

                PathDTO path = await this.pathService.GetById (new PathId (dto.pathID));

                var nodePassageList = new List<CreatingNodePassageDTO> ();

                int departTime = TimeUtils.fromTimeToSec (new Time (dto.tripDepartureTime));

                int passageTime = 0;

                for (int i = 0; i < path.segments.Count; i++) {

                    var segment = path.segments[i];
                    CreatingNodePassageDTO nodePassage;

                    if (i == 0) {
                        nodePassage = new CreatingNodePassageDTO (segment.firstNodeID.code, dto.tripDepartureTime);
                        passageTime += TimeUtils.fromTimeToSec (new Time (dto.tripDepartureTime));

                        nodePassageList.Add (nodePassage);
                    } else {
                        var previousSegment = path.segments[i - 1];

                        int nodePassageSec = passageTime + previousSegment.travelTimeBetweenNodes;

                        passageTime = nodePassageSec;

                        string nodePassageString = TimeUtils.fromSecToString (nodePassageSec);

                        nodePassage = new CreatingNodePassageDTO (segment.firstNodeID.code, nodePassageString);

                        nodePassageList.Add (nodePassage);

                        if (i == path.segments.Count - 1) {
                            nodePassageSec = passageTime + segment.travelTimeBetweenNodes;
                            nodePassageString = TimeUtils.fromSecToString (nodePassageSec);
                            CreatingNodePassageDTO lastNodePassage = new CreatingNodePassageDTO (segment.secondNodeID.code, nodePassageString);

                            nodePassageList.Add (lastNodePassage);
                        }
                    }
                }

                var trip = await tripService.AddTrip (dto, nodePassageList);

                var a = CreatedAtAction (
                    nameof (GetById),
                    new { id = trip.Id },
                    trip
                );
                return a;
            } catch (BusinessRuleValidationException ex) {
                return BadRequest (new { Message = ex.Message });
            }
        }

        [HttpPost ("complex")]
        public async Task<List<ActionResult<TripDTO>>> CreateComplex (CreatingComplexTripDTO dto) {

            List<ActionResult<TripDTO>> trips = new List<ActionResult<TripDTO>> ();

            int outwardTripDepartTimeSec = 0;

            for (int i = 0; i < dto.nTrips; i++) {

                if (i % 2 == 0) {

                    int frequencySec = (dto.frequency * 60) * (i / 2);

                    int departTimeSec = TimeUtils.fromStringToSec (dto.tripDepartureTime);

                    // ========================================================================================

                    outwardTripDepartTimeSec = departTimeSec + frequencySec;
                    Time outwardTripDepartTime = new Time (outwardTripDepartTimeSec);

                    CreatingTripDTO creatingOutwardTripDTO = new CreatingTripDTO (dto.lineID, dto.outwardPathID, outwardTripDepartTime.ToString ());
                    Task<ActionResult<TripDTO>> outwardTripTask = Create (creatingOutwardTripDTO);

                    if (!outwardTripTask.IsCompletedSuccessfully) {
                        new Exception ("Something went wrong during the creation of a trip.");
                    }

                    ActionResult<TripDTO> outwardTripResult = outwardTripTask.Result;

                    trips.Add (outwardTripResult);
                } else {

                    // ========================================================================================

                    int outwardTripDurationSec = await this.pathService.GetPathDurationById (new PathId (dto.outwardPathID));
                    int returnTripDepartTimeSec = outwardTripDepartTimeSec + outwardTripDurationSec;
                    Time returnTripDepartTime = new Time (returnTripDepartTimeSec);

                    CreatingTripDTO creatingReturnTripDTO = new CreatingTripDTO (dto.lineID, dto.returnPathID, returnTripDepartTime.ToString ());
                    Task<ActionResult<TripDTO>> returnTripTask = Create (creatingReturnTripDTO);

                    if (!returnTripTask.IsCompletedSuccessfully) {
                        new Exception ("Something went wrong during the creation of a trip.");
                    }

                    ActionResult<TripDTO> returnTripResult = returnTripTask.Result;

                    trips.Add (returnTripResult);
                }
            }

            return trips;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TripDTO>>> GetAll () {
            return await this.tripService.GetAll ();
        }

        [HttpGet ("pages")]
        public async Task<ActionResult<IEnumerable<TripDTO>>> GetAll (int skip, int limit) {
            var list = await this.tripService.GetAll ();

            return list.Skip (skip).Take (limit).ToList ();
        }

        [HttpGet ("count")]
        public async Task<ActionResult<int>> GetNumber () {
            return await this.tripService.GetNumber ();
        }

        [HttpGet ("{id}")]
        public async Task<ActionResult<TripDTO>> GetById (Guid id) {
            var trip = await tripService.GetById (new TripId (id));

            if (trip == null) {
                return NotFound ();
            }

            return trip;
        }

        [HttpGet ("line/{lineId}")]
        public async Task<ActionResult<IEnumerable<TripDTO>>> GetByLineId (string lineId) {
            return await tripService.GetTripsByLineID (new LineId (lineId));
        }

    }
}