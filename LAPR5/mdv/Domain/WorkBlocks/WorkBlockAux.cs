using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Domain.Times;
using mdv.DTO.Trips;
using mdv.Utils.UtilsTime;

namespace mdv.Domain.WorkBlocks {
    public class WorkBlockAux {        
        public int pos { get; private set; }

        public int sum { get; private set; }

        public List<TripDTO> tripList { get; private set; }

        public WorkBlockAux () { }
        public WorkBlockAux (int pos, int sum, TripDTO trip) {
            this.pos = pos;
            this.sum = sum;
            this.tripList = new List<TripDTO>();
            this.tripList.Add(trip);
        }

        public void addTrip(int cost, TripDTO trip) {
            this.sum += cost;
            this.tripList.Add(trip);
        }
    }
}