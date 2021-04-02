using mdv.Domain.WorkBlocks;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace mdv.Infrastructure.WorkBlocks {
    internal class WorkBlockEntityTypeConfiguration : IEntityTypeConfiguration<WorkBlock> {
        public void Configure (EntityTypeBuilder<WorkBlock> workBlockConfiguration) {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            workBlockConfiguration.ToTable ("WorkBlocks");

            workBlockConfiguration.HasKey (t => t.Id);
            workBlockConfiguration.OwnsOne (t => t.startingTime);
            workBlockConfiguration.OwnsOne (t => t.endingTime);
            workBlockConfiguration.HasMany (t => t.tripList).WithOne();
        }
    }
}