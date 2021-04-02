using Microsoft.EntityFrameworkCore.Migrations;

namespace mdv.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Drivers",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    mecanographicNumber_mecanographicNumber = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    entranceDate_date = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    departureDate_date = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Drivers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Vehicles",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    registration_vehicleRegistration = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    vin_vin = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    entranceDate_vehicleEntranceDate = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    vehicleType_id = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Vehicles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "CitizenCard",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    driverName_driverName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    birthDate_date = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    citizenCardNumber_citizenCardNumber = table.Column<long>(type: "bigint", nullable: true),
                    driverNIF_nif = table.Column<long>(type: "bigint", nullable: true),
                    DriverId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CitizenCard", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CitizenCard_Drivers_DriverId",
                        column: x => x.DriverId,
                        principalTable: "Drivers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "DriverLicence",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DriverId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    driverLicenseDate_date = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    numberDriverLicense_numberDriverLicense = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DriverLicence", x => x.Id);
                    table.ForeignKey(
                        name: "FK_DriverLicence_Drivers_DriverId",
                        column: x => x.DriverId,
                        principalTable: "Drivers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "DriverTypeId",
                columns: table => new
                {
                    id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DriverId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DriverTypeId", x => new { x.DriverId, x.id });
                    table.ForeignKey(
                        name: "FK_DriverTypeId_Drivers_DriverId",
                        column: x => x.DriverId,
                        principalTable: "Drivers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "VehicleDuties",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    vehicleDutyCode_vehicleDutyCode = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    vehicleID = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_VehicleDuties", x => x.Id);
                    table.ForeignKey(
                        name: "FK_VehicleDuties_Vehicles_vehicleID",
                        column: x => x.vehicleID,
                        principalTable: "Vehicles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "WorkBlocks",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    startingTime_time = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    endingTime_time = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    VehicleDutyId = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WorkBlocks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_WorkBlocks_VehicleDuties_VehicleDutyId",
                        column: x => x.VehicleDutyId,
                        principalTable: "VehicleDuties",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Trips",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    pathID_id = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    lineID_id = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    tripDepartureTime_time = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    VehicleDutyId = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    WorkBlockId = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Trips", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Trips_VehicleDuties_VehicleDutyId",
                        column: x => x.VehicleDutyId,
                        principalTable: "VehicleDuties",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Trips_WorkBlocks_WorkBlockId",
                        column: x => x.WorkBlockId,
                        principalTable: "WorkBlocks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "NodePassages",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    nodeID_id = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    passageTime_time = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    TripId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_NodePassages", x => x.Id);
                    table.ForeignKey(
                        name: "FK_NodePassages_Trips_TripId",
                        column: x => x.TripId,
                        principalTable: "Trips",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_CitizenCard_citizenCardNumber_citizenCardNumber",
                table: "CitizenCard",
                column: "citizenCardNumber_citizenCardNumber",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_CitizenCard_DriverId",
                table: "CitizenCard",
                column: "DriverId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_DriverLicence_DriverId",
                table: "DriverLicence",
                column: "DriverId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_DriverLicence_numberDriverLicense_numberDriverLicense",
                table: "DriverLicence",
                column: "numberDriverLicense_numberDriverLicense",
                unique: true,
                filter: "[numberDriverLicense_numberDriverLicense] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_NodePassages_TripId",
                table: "NodePassages",
                column: "TripId");

            migrationBuilder.CreateIndex(
                name: "IX_Trips_VehicleDutyId",
                table: "Trips",
                column: "VehicleDutyId");

            migrationBuilder.CreateIndex(
                name: "IX_Trips_WorkBlockId",
                table: "Trips",
                column: "WorkBlockId");

            migrationBuilder.CreateIndex(
                name: "IX_VehicleDuties_vehicleID",
                table: "VehicleDuties",
                column: "vehicleID",
                unique: true,
                filter: "[vehicleID] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_WorkBlocks_VehicleDutyId",
                table: "WorkBlocks",
                column: "VehicleDutyId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CitizenCard");

            migrationBuilder.DropTable(
                name: "DriverLicence");

            migrationBuilder.DropTable(
                name: "DriverTypeId");

            migrationBuilder.DropTable(
                name: "NodePassages");

            migrationBuilder.DropTable(
                name: "Drivers");

            migrationBuilder.DropTable(
                name: "Trips");

            migrationBuilder.DropTable(
                name: "WorkBlocks");

            migrationBuilder.DropTable(
                name: "VehicleDuties");

            migrationBuilder.DropTable(
                name: "Vehicles");
        }
    }
}
