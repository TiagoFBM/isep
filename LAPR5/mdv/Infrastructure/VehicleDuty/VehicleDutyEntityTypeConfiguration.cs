using mdv.Domain.VehicleDutys;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace mdv.Infrastructure.VehicleDutys {
    internal class VehicleDutyEntityTypeConfiguration : IEntityTypeConfiguration<VehicleDuty> {
        public void Configure (EntityTypeBuilder<VehicleDuty> vehicleDutyConfiguration) {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            vehicleDutyConfiguration.ToTable ("VehicleDuties");

            vehicleDutyConfiguration.HasKey (t => t.Id);
            vehicleDutyConfiguration.OwnsOne (t => t.vehicleDutyCode);
            vehicleDutyConfiguration.HasMany (t => t.tripsList).WithOne();
            vehicleDutyConfiguration.HasMany (t => t.workBlockList).WithOne();
        }
    }
}