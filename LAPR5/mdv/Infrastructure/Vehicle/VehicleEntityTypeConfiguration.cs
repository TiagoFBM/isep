using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using mdv.Domain.Vehicles;
using mdv.Domain.VehicleTypes;

namespace mdv.Infrastructure.Vehicles
{
    internal class VehicleEntityTypeConfiguration : IEntityTypeConfiguration<Vehicle>
    {
        public void Configure(EntityTypeBuilder<Vehicle> vehicleConfiguration)
        {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            vehicleConfiguration.ToTable("Vehicles");

            vehicleConfiguration.HasKey(t => t.Id);
            vehicleConfiguration.OwnsOne(t => t.registration);
            vehicleConfiguration.OwnsOne(t => t.vin);
            vehicleConfiguration.OwnsOne(t => t.entranceDate);
            vehicleConfiguration.OwnsOne(t => t.vehicleType);
        }
    }
}