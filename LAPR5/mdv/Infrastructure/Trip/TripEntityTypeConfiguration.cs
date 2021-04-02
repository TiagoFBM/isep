using mdv.Domain.Trips;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace mdv.Infrastructure.Trips {
    internal class TripEntityTypeConfiguration : IEntityTypeConfiguration<Trip> {
        public void Configure (EntityTypeBuilder<Trip> tripConfiguration) {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            tripConfiguration.ToTable ("Trips");

            tripConfiguration.HasKey (t => t.Id);
            tripConfiguration.OwnsOne (t => t.lineID);
            tripConfiguration.OwnsOne (t => t.pathID);
            tripConfiguration.OwnsOne (t => t.tripDepartureTime);
            tripConfiguration.OwnsMany<NodePassage> (t => t.nodePassageList, a => {
                a.HasKey (a => a.Id);
                a.OwnsOne (a => a.nodeID);
                a.OwnsOne (a => a.passageTime);
            });
        }
    }
}