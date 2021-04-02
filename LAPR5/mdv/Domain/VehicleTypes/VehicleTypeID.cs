using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.VehicleTypes
{
    public class VehicleTypeID
    {
        public string id { get; set; }

        public VehicleTypeID() { }

        [JsonConstructor]

        public VehicleTypeID(string id)
        {
            this.id = id;
        }

        public override string ToString()
        {
            return id;
        }

    }
}