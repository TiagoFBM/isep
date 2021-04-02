using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Utils.UtilsTime;
using mdv.Domain.Vehicles;
using mdv.Domain.WorkBlocks;

namespace mdv.Domain.VehicleDutys {
    public class VehicleDuty : Entity<VehicleDutyId>, IAggregateRoot {
        public VehicleDutyCode vehicleDutyCode { get; private set; }
        
        public List<Trip> tripsList { get; private set; }

        public List<WorkBlock> workBlockList { get; private set; }

        public VehicleDuty () { }
        public VehicleDuty (string vehicleDutyCode) {

            if (StringValidator.isStringEmptyOrNull (vehicleDutyCode)) {
                throw new BusinessRuleValidationException (vehicleDutyCode + " invalid: Vehicle Duty Code can't be null or empty.");
            }

            this.Id = new VehicleDutyId(Guid.NewGuid());
            this.vehicleDutyCode = new VehicleDutyCode(vehicleDutyCode);
            this.tripsList = new List<Trip> ();
            this.tripsList.Sort(delegate(Trip x, Trip y)
            {
                int a = TimeUtils.fromTimeToSec(x.tripDepartureTime);
                int b = TimeUtils.fromTimeToSec(y.tripDepartureTime);
                int c = a.CompareTo(b);
                return c;
            });
            this.workBlockList = new List<WorkBlock>();
        }

        public VehicleDuty (string vehicleDutyCode, List<Trip> tripsList) {

            if (StringValidator.isStringEmptyOrNull (vehicleDutyCode)) {
                throw new BusinessRuleValidationException (vehicleDutyCode + " invalid: Vehicle Duty Code can't be null or empty.");
            }

            /*if(!checkIfVehiclePerformsLessThan16H(tripsList)){
                
                throw new BusinessRuleValidationException (tripsList + " invalid: Vehicle Duty exceeds 16h of service.");

            }*/
            
            this.Id = new VehicleDutyId(Guid.NewGuid());
            this.vehicleDutyCode = new VehicleDutyCode(vehicleDutyCode);
            this.tripsList = new List<Trip> (tripsList);
            this.tripsList.Sort(delegate(Trip x, Trip y)
            {
                int a = TimeUtils.fromTimeToSec(x.tripDepartureTime);
                int b = TimeUtils.fromTimeToSec(y.tripDepartureTime);
                int c = a.CompareTo(b);
                return c;
            });
            this.workBlockList = new List<WorkBlock>();
        }

        public void AddTrip (Trip trip) {
            this.tripsList.Add (trip);
        }

        public void addWorkBlocks (List<WorkBlock> wbList) {
            this.workBlockList.AddRange(wbList);
        }


        /*public static bool checkIfVehiclePerformsLessThan16H(List<Trip> tripsList)
        {
            int i = 0;
            int itemCount = tripsList.Count;
            Trip firstTrip = null;
            Trip lastTrip = null;

            foreach( Trip t in tripsList){
                i++;
                if( i ==1){
                    firstTrip = t;
                }
                if(i == itemCount){
                    lastTrip = t;
                }
            }

            long lastTripTime = TimeUtils.fromStringToSec(lastTrip.tripDepartureTime.ToString());
            long firstTripTime = TimeUtils.fromStringToSec(firstTrip.tripDepartureTime.ToString());


            if (lastTripTime - firstTripTime <= 57600)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static bool checkIfVehicleStartsAndEndsInCollectionStation(List<Trip> tripsList)
        {
            int i = 0;
            int itemCountT = tripsList.Count;
            Trip firstTrip = null;
            Trip lastTrip = null;

            foreach( Trip t in tripsList){
                i++;
                if( i ==1){
                    firstTrip = t;
                }
                if(i == itemCountT){
                    lastTrip = t;
                }
            }

            NodePassage firstNode = null;
            NodePassage lastNode = null;
            int a = 0;
            int b = 0;
            int itemCountN = lastTrip.nodePassageList.Count;

            foreach( NodePassage n in firstTrip.nodePassageList){
                a++;
                if( a ==1){
                    firstNode = n;
                }
            }

            foreach( NodePassage n in lastTrip.nodePassageList){
                b++;
                if(i == itemCountN){
                    lastNode = n;
                }
            }

            if (firstNode.nodeID.)
            {
                return true;
            }
            else
            {
                return false;
            }
        }*/
    }
}