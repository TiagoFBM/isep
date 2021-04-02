using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using System.Xml;
using mdv.DTO.Trips;
using mdv.DTO.WorkBlocks;
using mdv.DTO.VehicleDutys;
using mdv.DTO.DriverDutys;
using mdv.Domain.VehicleDutys;
using mdv.Services;
using mdv.Utils.UtilsTime;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace mdv.Controllers {

    [Route ("api/[controller]")]
    [ApiController]

    public class ImportController : ControllerBase {
        private ITripService _tripService;
        private ILineService _lineService;
        private IVehicleDutyService _vehicleDutyService;
        private IWorkBlockService _workBlockService;
        private IDriverDutyService _driverDutyService;

        public ImportController (ITripService tripService, ILineService lineService, IVehicleDutyService vehicleDutyService, IWorkBlockService workBlockService, IDriverDutyService driverDutyService) {
            this._tripService = tripService;
            this._lineService = lineService;
            this._vehicleDutyService = vehicleDutyService;
            this._workBlockService = workBlockService;
            this._driverDutyService = driverDutyService;
        }

        [HttpPost]
        public async Task<Boolean> Import (IFormFile file) {
            var content = new StreamReader (file.OpenReadStream ()).ReadToEnd ();

            XmlDocument doc = new XmlDocument ();
            doc.LoadXml (content);

            var lista = await ImportTrips (doc);
            var workblockList = await ImportVehicleDutiesAndWorkblocks(doc, lista);
            await ImportDriverDuties(doc, workblockList);
            return true;
        }

        private async Task<List<ImportedTripDTO>> ImportTrips (XmlDocument doc) {

            var importedTripsList = new List<ImportedTripDTO>();

            XmlNodeList trips = doc.GetElementsByTagName ("Trip");

            foreach (XmlNode trip in trips) {
                List<CreatingNodePassageDTO> nodePassages = new List<CreatingNodePassageDTO> ();
                
                string tripKey = trip.Attributes["key"].InnerText;

                string tripPath = trip.Attributes["Path"].InnerText;

                string tripLine;
                if(trip.Attributes["Line"] == null){
                    tripLine = await this._lineService.GetLineOfPath(tripPath);
                } else {
                    tripLine = trip.Attributes["Line"].InnerText;
                }

                string tripDepartureTime = null;
                XmlNodeList passingTimes = trip.FirstChild.ChildNodes;

                for (int i = 0; i < passingTimes.Count; i++) {
                    XmlNode passingTime = passingTimes[i];

                    int nodePassageTimeSec = Int32.Parse (passingTime.Attributes["Time"].InnerText);
                    string nodePassageNode = passingTime.Attributes["Node"].InnerText;

                    string nodePassageTime = TimeUtils.fromSecToString (nodePassageTimeSec);

                    if (i == 0) {
                        tripDepartureTime = nodePassageTime;
                    }

                    CreatingNodePassageDTO nodePassageDTO = new CreatingNodePassageDTO (nodePassageNode, nodePassageTime);
                    nodePassages.Add (nodePassageDTO);
                }

                CreatingTripDTO creatingTrip = new CreatingTripDTO (tripLine, tripPath, tripDepartureTime);
                var tripDTO = await _tripService.AddTrip (creatingTrip, nodePassages);

                ImportedTripDTO importedTrip = new ImportedTripDTO(tripKey,tripDTO.Id,tripDTO.lineID,tripDTO.pathID,tripDTO.tripDepartureTime,tripDTO.nodePassageListDTO);
                importedTripsList.Add(importedTrip);
            }
            return importedTripsList;

        }

        private async Task<List<ImportedWorkBlockDTO>> ImportVehicleDutiesAndWorkblocks (XmlDocument doc, List<ImportedTripDTO> importedTrips) {
            string xmlns = doc.DocumentElement.Attributes["xmlns"].Value;
            XmlNamespaceManager nsmgr = new XmlNamespaceManager(doc.NameTable);
            nsmgr.AddNamespace("file", xmlns);

            var importedWorkBlockList = new List<ImportedWorkBlockDTO>();
            
            XmlNodeList vehicleDuties = doc.GetElementsByTagName("VehicleDuty");


            foreach (XmlNode vehicleDuty in vehicleDuties) {
                List<string> tripList = new List<string>();

                foreach (XmlNode refKey in vehicleDuty.ChildNodes[1].ChildNodes) {
                    string keyString = refKey.Attributes["key"].Value;
                    ImportedTripDTO tripDTO = getImportedTripFromKey(importedTrips, keyString);
                    if (tripDTO != null) {
                        tripList.Add(tripDTO.Id.ToString());
                    }
                }

                string vdCode = vehicleDuty.Attributes["Name"].Value;

                CreatingVehicleDutyDTO cVDDTO = new CreatingVehicleDutyDTO(vdCode, tripList);

                var createdVDDTO = await this._vehicleDutyService.AddVehicleDuty(cVDDTO);

                XmlNodeList workblocksKeys = vehicleDuty.FirstChild.ChildNodes;

                List<WorkBlockDTO> cWBList = new List<WorkBlockDTO>();

                foreach (XmlNode workblockKey in workblocksKeys) {
                    string key = workblockKey.Attributes["key"].Value;
                    XmlNode workblock = doc.SelectSingleNode($"//file:WorkBlock[@key='{key}']", nsmgr);
                    
                    List<string> wbTripList = new List<string>();
                    foreach (XmlNode refKey in workblock.FirstChild.ChildNodes) {
                        string keyString = refKey.Attributes["key"].Value;
                        ImportedTripDTO tripDTO = getImportedTripFromKey(importedTrips, keyString);
                        if (tripDTO != null) {
                            wbTripList.Add(tripDTO.Id.ToString());
                        }
                    }

                    string startingTime = TimeUtils.fromSecToString (Int32.Parse(workblock.Attributes["StartTime"].Value));
                    string endingTime = TimeUtils.fromSecToString (Int32.Parse(workblock.Attributes["EndTime"].Value));

                    CreatingWorkBlockDTO cWBDTO = new CreatingWorkBlockDTO(wbTripList, startingTime, endingTime);
                    var wbDTO = await this._workBlockService.AddWorkBlock(cWBDTO);
                    cWBList.Add(wbDTO);
                    importedWorkBlockList.Add(new ImportedWorkBlockDTO(key, wbDTO));
                }

                VehicleDutyDTO createdVDDTOWithWorkBlocks =  await this._vehicleDutyService.AddWorkBlocksToVehicleDuty(cWBList, new VehicleDutyId(createdVDDTO.Id));
                    
            }
            return importedWorkBlockList;

        }

        private async Task<bool> ImportDriverDuties (XmlDocument doc, List<ImportedWorkBlockDTO> importedWorkBlocks) {
            string xmlns = doc.DocumentElement.Attributes["xmlns"].Value;
            XmlNamespaceManager nsmgr = new XmlNamespaceManager(doc.NameTable);
            nsmgr.AddNamespace("file", xmlns);
            
            XmlNodeList driverDuties = doc.GetElementsByTagName("DriverDuty");

            foreach (XmlNode driverDuty in driverDuties) {
                List<string> workBlockIDs = new List<string>();
                XmlNodeList workBlockRefs = driverDuty.FirstChild.ChildNodes;

                foreach (XmlNode workBlockRef in workBlockRefs) {
                    string keyString = workBlockRef.Attributes["key"].Value;
                    ImportedWorkBlockDTO importedWorkBlockDTO = getImportedWorkBlockDTOFromKey(importedWorkBlocks, keyString);
                    if (importedWorkBlockDTO != null) {
                        workBlockIDs.Add(importedWorkBlockDTO.workBlockDTO.Id.ToString());
                    }
                }

                string code = driverDuty.Attributes["Name"].Value;

                var creatingDriverDutyDTO = new CreatingDriverDutyDTO(code, workBlockIDs);
                var createdDD = await this._driverDutyService.AddDriverDuty(creatingDriverDutyDTO);
            }
            return true;
        }

        private ImportedTripDTO getImportedTripFromKey(List<ImportedTripDTO> importedTrips, String key) {
            foreach (var importedTrip in importedTrips) {
                if (importedTrip.key == key) {
                    return importedTrip;
                }
            }
            return null;
        }

        private ImportedWorkBlockDTO getImportedWorkBlockDTOFromKey(List<ImportedWorkBlockDTO> importedWorkBlocks, String key) {
            foreach (var importedWB in importedWorkBlocks) {
                if (importedWB.key == key) {
                    return importedWB;
                }
            }
            return null;
        }

    }
}