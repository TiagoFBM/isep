using Microsoft.EntityFrameworkCore.Migrations;

namespace mdv.Migrations
{
    public partial class VDWithoutVehicle : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_VehicleDuties_Vehicles_vehicleID",
                table: "VehicleDuties");

            migrationBuilder.DropIndex(
                name: "IX_VehicleDuties_vehicleID",
                table: "VehicleDuties");

            migrationBuilder.DropColumn(
                name: "vehicleID",
                table: "VehicleDuties");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "vehicleID",
                table: "VehicleDuties",
                type: "nvarchar(450)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_VehicleDuties_vehicleID",
                table: "VehicleDuties",
                column: "vehicleID",
                unique: true,
                filter: "[vehicleID] IS NOT NULL");

            migrationBuilder.AddForeignKey(
                name: "FK_VehicleDuties_Vehicles_vehicleID",
                table: "VehicleDuties",
                column: "vehicleID",
                principalTable: "Vehicles",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
