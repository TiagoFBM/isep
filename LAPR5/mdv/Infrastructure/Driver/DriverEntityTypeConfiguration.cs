using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;

namespace mdv.Infrastructure.Drivers
{
    internal class DriverEntityTypeConfiguration : IEntityTypeConfiguration<Driver>
    {
        public void Configure(EntityTypeBuilder<Driver> driverConfiguration)
        {
            // cf. https://www.entityframeworktutorial.net/efcore/fluent-api-in-entity-framework-core.aspx
            driverConfiguration.ToTable("Drivers");

            driverConfiguration.HasKey(t => t.Id);
            driverConfiguration.OwnsOne(t => t.mecanographicNumber);
            driverConfiguration.OwnsOne<CitizenCard>(t => t.citizenCard, c =>
            {
                c.HasKey(c => c.Id);
                c.OwnsOne(c => c.driverName);
                c.OwnsOne(c => c.birthDate);
                c.OwnsOne(c => c.citizenCardNumber, p =>
                {
                    p.HasIndex(z => z.citizenCardNumber).IsUnique();
                });
                c.OwnsOne(c => c.driverNIF);
            });
            driverConfiguration.OwnsOne(t => t.entranceDate);
            driverConfiguration.OwnsOne(t => t.departureDate);

            driverConfiguration.OwnsOne<DriverLicense>(t => t.driverLicense, a =>
            {
                a.HasKey(a => a.Id);
                a.OwnsOne(a => a.numberDriverLicense, p =>
                {
                    p.HasIndex(z => z.numberDriverLicense).IsUnique();
                });
                a.OwnsOne(a => a.driverLicenseDate);
            });

            driverConfiguration.OwnsMany<DriverTypeId>(t => t.driverTypes);
        }
    }
}