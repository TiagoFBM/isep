using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;


namespace mdv.Infrastructure.DriverDutys
{
    internal class DriverDutyEntityTypeConfiguration : IEntityTypeConfiguration<DriverDuty>
    {
        public void Configure(EntityTypeBuilder<DriverDuty> driverDutyConfiguration)
        {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            driverDutyConfiguration.ToTable("DriverDuties");

            driverDutyConfiguration.HasKey(t => t.Id);
            driverDutyConfiguration.OwnsOne(t => t.driverDutyCode);
            driverDutyConfiguration.HasMany(t => t.listWorkBlocks).WithOne();
        }
    }
}