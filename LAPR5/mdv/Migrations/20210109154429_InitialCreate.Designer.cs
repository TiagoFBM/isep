﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using mdv.Infrastructure;

namespace mdv.Migrations
{
    [DbContext(typeof(DDDmdvDBContext))]
    [Migration("20210109154429_InitialCreate")]
    partial class InitialCreate
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .UseIdentityColumns()
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("ProductVersion", "5.0.0");

            modelBuilder.Entity("mdv.Domain.Drivers.Driver", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.ToTable("Drivers");
                });

            modelBuilder.Entity("mdv.Domain.Trips.Trip", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("VehicleDutyId")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("WorkBlockId")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.HasIndex("VehicleDutyId");

                    b.HasIndex("WorkBlockId");

                    b.ToTable("Trips");
                });

            modelBuilder.Entity("mdv.Domain.VehicleDutys.VehicleDuty", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("vehicleID")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.HasIndex("vehicleID")
                        .IsUnique()
                        .HasFilter("[vehicleID] IS NOT NULL");

                    b.ToTable("VehicleDuties");
                });

            modelBuilder.Entity("mdv.Domain.Vehicles.Vehicle", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.ToTable("Vehicles");
                });

            modelBuilder.Entity("mdv.Domain.WorkBlocks.WorkBlock", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("VehicleDutyId")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.HasIndex("VehicleDutyId");

                    b.ToTable("WorkBlocks");
                });

            modelBuilder.Entity("mdv.Domain.Drivers.Driver", b =>
                {
                    b.OwnsMany("mdv.Domain.DriverTypes.DriverTypeId", "driverTypes", b1 =>
                        {
                            b1.Property<string>("DriverId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("id")
                                .HasColumnType("nvarchar(450)");

                            b1.HasKey("DriverId", "id");

                            b1.ToTable("DriverTypeId");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");
                        });

                    b.OwnsOne("mdv.Domain.Drivers.CitizenCard", "citizenCard", b1 =>
                        {
                            b1.Property<string>("Id")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("DriverId")
                                .IsRequired()
                                .HasColumnType("nvarchar(450)");

                            b1.HasKey("Id");

                            b1.HasIndex("DriverId")
                                .IsUnique();

                            b1.ToTable("CitizenCard");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");

                            b1.OwnsOne("mdv.Domain.Drivers.BirthDate", "birthDate", b2 =>
                                {
                                    b2.Property<string>("CitizenCardId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("date")
                                        .HasColumnType("nvarchar(max)");

                                    b2.HasKey("CitizenCardId");

                                    b2.ToTable("CitizenCard");

                                    b2.WithOwner()
                                        .HasForeignKey("CitizenCardId");
                                });

                            b1.OwnsOne("mdv.Domain.Drivers.CitizenCardNumber", "citizenCardNumber", b2 =>
                                {
                                    b2.Property<string>("CitizenCardId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<long>("citizenCardNumber")
                                        .HasColumnType("bigint");

                                    b2.HasKey("CitizenCardId");

                                    b2.HasIndex("citizenCardNumber")
                                        .IsUnique();

                                    b2.ToTable("CitizenCard");

                                    b2.WithOwner()
                                        .HasForeignKey("CitizenCardId");
                                });

                            b1.OwnsOne("mdv.Domain.Drivers.DriverNIF", "driverNIF", b2 =>
                                {
                                    b2.Property<string>("CitizenCardId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<long>("nif")
                                        .HasColumnType("bigint");

                                    b2.HasKey("CitizenCardId");

                                    b2.ToTable("CitizenCard");

                                    b2.WithOwner()
                                        .HasForeignKey("CitizenCardId");
                                });

                            b1.OwnsOne("mdv.Domain.Drivers.Name", "driverName", b2 =>
                                {
                                    b2.Property<string>("CitizenCardId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("driverName")
                                        .HasColumnType("nvarchar(max)");

                                    b2.HasKey("CitizenCardId");

                                    b2.ToTable("CitizenCard");

                                    b2.WithOwner()
                                        .HasForeignKey("CitizenCardId");
                                });

                            b1.Navigation("birthDate");

                            b1.Navigation("citizenCardNumber");

                            b1.Navigation("driverName");

                            b1.Navigation("driverNIF");
                        });

                    b.OwnsOne("mdv.Domain.Drivers.DepartureDate", "departureDate", b1 =>
                        {
                            b1.Property<string>("DriverId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("date")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("DriverId");

                            b1.ToTable("Drivers");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");
                        });

                    b.OwnsOne("mdv.Domain.Drivers.DriverLicense", "driverLicense", b1 =>
                        {
                            b1.Property<string>("Id")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("DriverId")
                                .IsRequired()
                                .HasColumnType("nvarchar(450)");

                            b1.HasKey("Id");

                            b1.HasIndex("DriverId")
                                .IsUnique();

                            b1.ToTable("DriverLicence");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");

                            b1.OwnsOne("mdv.Domain.Drivers.DriverLicenseDate", "driverLicenseDate", b2 =>
                                {
                                    b2.Property<string>("DriverLicenseId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("date")
                                        .HasColumnType("nvarchar(max)");

                                    b2.HasKey("DriverLicenseId");

                                    b2.ToTable("DriverLicence");

                                    b2.WithOwner()
                                        .HasForeignKey("DriverLicenseId");
                                });

                            b1.OwnsOne("mdv.Domain.NumberDriverLicense.NumberLicense", "numberDriverLicense", b2 =>
                                {
                                    b2.Property<string>("DriverLicenseId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("numberDriverLicense")
                                        .HasColumnType("nvarchar(450)");

                                    b2.HasKey("DriverLicenseId");

                                    b2.HasIndex("numberDriverLicense")
                                        .IsUnique()
                                        .HasFilter("[numberDriverLicense_numberDriverLicense] IS NOT NULL");

                                    b2.ToTable("DriverLicence");

                                    b2.WithOwner()
                                        .HasForeignKey("DriverLicenseId");
                                });

                            b1.Navigation("driverLicenseDate");

                            b1.Navigation("numberDriverLicense");
                        });

                    b.OwnsOne("mdv.Domain.Drivers.EntranceDate", "entranceDate", b1 =>
                        {
                            b1.Property<string>("DriverId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("date")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("DriverId");

                            b1.ToTable("Drivers");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");
                        });

                    b.OwnsOne("mdv.Domain.Drivers.MecanographicNumber", "mecanographicNumber", b1 =>
                        {
                            b1.Property<string>("DriverId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("mecanographicNumber")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("DriverId");

                            b1.ToTable("Drivers");

                            b1.WithOwner()
                                .HasForeignKey("DriverId");
                        });

                    b.Navigation("citizenCard");

                    b.Navigation("departureDate");

                    b.Navigation("driverLicense");

                    b.Navigation("driverTypes");

                    b.Navigation("entranceDate");

                    b.Navigation("mecanographicNumber");
                });

            modelBuilder.Entity("mdv.Domain.Trips.Trip", b =>
                {
                    b.HasOne("mdv.Domain.VehicleDutys.VehicleDuty", null)
                        .WithMany("tripsList")
                        .HasForeignKey("VehicleDutyId");

                    b.HasOne("mdv.Domain.WorkBlocks.WorkBlock", null)
                        .WithMany("tripList")
                        .HasForeignKey("WorkBlockId");

                    b.OwnsOne("mdv.Domain.Lines.LineId", "lineID", b1 =>
                        {
                            b1.Property<string>("TripId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("id")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("TripId");

                            b1.ToTable("Trips");

                            b1.WithOwner()
                                .HasForeignKey("TripId");
                        });

                    b.OwnsOne("mdv.Domain.Paths.PathId", "pathID", b1 =>
                        {
                            b1.Property<string>("TripId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("id")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("TripId");

                            b1.ToTable("Trips");

                            b1.WithOwner()
                                .HasForeignKey("TripId");
                        });

                    b.OwnsOne("mdv.Domain.Times.Time", "tripDepartureTime", b1 =>
                        {
                            b1.Property<string>("TripId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("time")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("TripId");

                            b1.ToTable("Trips");

                            b1.WithOwner()
                                .HasForeignKey("TripId");
                        });

                    b.OwnsMany("mdv.Domain.Trips.NodePassage", "nodePassageList", b1 =>
                        {
                            b1.Property<string>("Id")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("TripId")
                                .IsRequired()
                                .HasColumnType("nvarchar(450)");

                            b1.HasKey("Id");

                            b1.HasIndex("TripId");

                            b1.ToTable("NodePassages");

                            b1.WithOwner()
                                .HasForeignKey("TripId");

                            b1.OwnsOne("mdv.Domain.Nodes.NodeId", "nodeID", b2 =>
                                {
                                    b2.Property<string>("NodePassageId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("id")
                                        .HasColumnType("nvarchar(max)");

                                    b2.HasKey("NodePassageId");

                                    b2.ToTable("NodePassages");

                                    b2.WithOwner()
                                        .HasForeignKey("NodePassageId");
                                });

                            b1.OwnsOne("mdv.Domain.Times.Time", "passageTime", b2 =>
                                {
                                    b2.Property<string>("NodePassageId")
                                        .HasColumnType("nvarchar(450)");

                                    b2.Property<string>("time")
                                        .HasColumnType("nvarchar(max)");

                                    b2.HasKey("NodePassageId");

                                    b2.ToTable("NodePassages");

                                    b2.WithOwner()
                                        .HasForeignKey("NodePassageId");
                                });

                            b1.Navigation("nodeID");

                            b1.Navigation("passageTime");
                        });

                    b.Navigation("lineID");

                    b.Navigation("nodePassageList");

                    b.Navigation("pathID");

                    b.Navigation("tripDepartureTime");
                });

            modelBuilder.Entity("mdv.Domain.VehicleDutys.VehicleDuty", b =>
                {
                    b.HasOne("mdv.Domain.Vehicles.Vehicle", "vehicle")
                        .WithOne()
                        .HasForeignKey("mdv.Domain.VehicleDutys.VehicleDuty", "vehicleID");

                    b.OwnsOne("mdv.Domain.VehicleDutys.VehicleDutyCode", "vehicleDutyCode", b1 =>
                        {
                            b1.Property<string>("VehicleDutyId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("vehicleDutyCode")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("VehicleDutyId");

                            b1.ToTable("VehicleDuties");

                            b1.WithOwner()
                                .HasForeignKey("VehicleDutyId");
                        });

                    b.Navigation("vehicle");

                    b.Navigation("vehicleDutyCode");
                });

            modelBuilder.Entity("mdv.Domain.Vehicles.Vehicle", b =>
                {
                    b.OwnsOne("mdv.Domain.VehicleTypes.VehicleTypeID", "vehicleType", b1 =>
                        {
                            b1.Property<string>("VehicleId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("id")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("VehicleId");

                            b1.ToTable("Vehicles");

                            b1.WithOwner()
                                .HasForeignKey("VehicleId");
                        });

                    b.OwnsOne("mdv.Domain.Vehicles.Registration", "registration", b1 =>
                        {
                            b1.Property<string>("VehicleId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("vehicleRegistration")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("VehicleId");

                            b1.ToTable("Vehicles");

                            b1.WithOwner()
                                .HasForeignKey("VehicleId");
                        });

                    b.OwnsOne("mdv.Domain.Vehicles.VIN", "vin", b1 =>
                        {
                            b1.Property<string>("VehicleId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("vin")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("VehicleId");

                            b1.ToTable("Vehicles");

                            b1.WithOwner()
                                .HasForeignKey("VehicleId");
                        });

                    b.OwnsOne("mdv.Domain.Vehicles.VehicleEntranceDate", "entranceDate", b1 =>
                        {
                            b1.Property<string>("VehicleId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("vehicleEntranceDate")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("VehicleId");

                            b1.ToTable("Vehicles");

                            b1.WithOwner()
                                .HasForeignKey("VehicleId");
                        });

                    b.Navigation("entranceDate");

                    b.Navigation("registration");

                    b.Navigation("vehicleType");

                    b.Navigation("vin");
                });

            modelBuilder.Entity("mdv.Domain.WorkBlocks.WorkBlock", b =>
                {
                    b.HasOne("mdv.Domain.VehicleDutys.VehicleDuty", null)
                        .WithMany("workBlockList")
                        .HasForeignKey("VehicleDutyId");

                    b.OwnsOne("mdv.Domain.Times.Time", "endingTime", b1 =>
                        {
                            b1.Property<string>("WorkBlockId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("time")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("WorkBlockId");

                            b1.ToTable("WorkBlocks");

                            b1.WithOwner()
                                .HasForeignKey("WorkBlockId");
                        });

                    b.OwnsOne("mdv.Domain.Times.Time", "startingTime", b1 =>
                        {
                            b1.Property<string>("WorkBlockId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("time")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("WorkBlockId");

                            b1.ToTable("WorkBlocks");

                            b1.WithOwner()
                                .HasForeignKey("WorkBlockId");
                        });

                    b.Navigation("endingTime");

                    b.Navigation("startingTime");
                });

            modelBuilder.Entity("mdv.Domain.VehicleDutys.VehicleDuty", b =>
                {
                    b.Navigation("tripsList");

                    b.Navigation("workBlockList");
                });

            modelBuilder.Entity("mdv.Domain.WorkBlocks.WorkBlock", b =>
                {
                    b.Navigation("tripList");
                });
#pragma warning restore 612, 618
        }
    }
}
