using Newtonsoft.Json;

namespace mdv.DTO.VehicleTypes
{
    public class VehicleTypeDTO
    {
        [JsonProperty("code")]
        public string code { get; set; }

        [JsonProperty("description")]
        public string description { get; set; }

        [JsonProperty("autonomy")]
        public long autonomy { get; set; }

        [JsonProperty("fuelType")]
        public string fuelType { get; set; }

        [JsonProperty("costPerKilometer")]
        public long costPerKilometer { get; set; }

        [JsonProperty("averageConsuption")]
        public long averageConsuption { get; set; }

        [JsonProperty("averageSpeed")]
        public long averageSpeed { get; set; }

        [JsonConstructor]
        public VehicleTypeDTO(string code, string description, long autonomy, string fuelType, long costPerKilometer, long averageConsuption, long averageSpeed )
        {
            this.code = code;
            this.description = description;
            this.autonomy = autonomy;
            this.fuelType = fuelType;
            this.costPerKilometer = costPerKilometer;
            this.averageConsuption = averageConsuption;
            this.averageSpeed = averageSpeed;
        }
    }
}