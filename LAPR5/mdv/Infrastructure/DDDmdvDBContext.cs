using Microsoft.EntityFrameworkCore;
using mdv.Domain.Trips;
using mdv.Domain.Drivers;
using mdv.Domain.Vehicles;
using mdv.Domain.VehicleDutys;
using mdv.Domain.WorkBlocks;
using mdv.Domain.DriverDutys;
using mdv.Infrastructure.Trips;
using mdv.Infrastructure.Drivers;
using mdv.Infrastructure.Vehicles;
using mdv.Infrastructure.VehicleDutys;
using mdv.Infrastructure.WorkBlocks;
using mdv.Infrastructure.DriverDutys;

namespace mdv.Infrastructure
{
    public class DDDmdvDBContext : DbContext
    {
        public DbSet<Trip> Trips { get; set; }
        public DbSet<NodePassage> NodePassages { get; set; }

        public DbSet<Driver> Drivers { get; set; }

        public DbSet<Vehicle> Vehicles { get; set; }

        public DbSet<CitizenCard> CitizenCard { get; set; }

        public DbSet<DriverLicense> DriverLicence { get; set; }

        public DbSet<VehicleDuty> VehicleDutys { get; set; }

        public DbSet<WorkBlock> WorkBlocks { get; set; }

        public DbSet<DriverDuty> DriverDutys { get; set; }


        //public DbSet<Product> Products { get; set; }

        //public DbSet<Family> Families { get; set; }

        public DDDmdvDBContext(DbContextOptions options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.ApplyConfiguration(new TripEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new DriverEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new VehicleEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new VehicleDutyEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new WorkBlockEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new DriverDutyEntityTypeConfiguration());

            // modelBuilder.ApplyConfiguration(new ProductEntityTypeConfiguration());
            // modelBuilder.ApplyConfiguration(new FamilyEntityTypeConfiguration());
        }
    }
}