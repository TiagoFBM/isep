using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.DriverTypes
{
    public class DriverTypeId
    {
        public string id { get; set; }

        public DriverTypeId() { }

        [JsonConstructor]

        public DriverTypeId(string id)
        {
            this.id = id;
        }

        public override string ToString()
        {
            return id;
        }

    }
}