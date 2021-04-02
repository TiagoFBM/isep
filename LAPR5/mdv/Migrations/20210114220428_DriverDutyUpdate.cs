using Microsoft.EntityFrameworkCore.Migrations;

namespace mdv.Migrations
{
    public partial class DriverDutyUpdate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "DriverDutyId",
                table: "WorkBlocks",
                type: "nvarchar(450)",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "DriverDuties",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    driverDutyCode_driverDutyCode = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DriverDuties", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_WorkBlocks_DriverDutyId",
                table: "WorkBlocks",
                column: "DriverDutyId");

            migrationBuilder.AddForeignKey(
                name: "FK_WorkBlocks_DriverDuties_DriverDutyId",
                table: "WorkBlocks",
                column: "DriverDutyId",
                principalTable: "DriverDuties",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_WorkBlocks_DriverDuties_DriverDutyId",
                table: "WorkBlocks");

            migrationBuilder.DropTable(
                name: "DriverDuties");

            migrationBuilder.DropIndex(
                name: "IX_WorkBlocks_DriverDutyId",
                table: "WorkBlocks");

            migrationBuilder.DropColumn(
                name: "DriverDutyId",
                table: "WorkBlocks");
        }
    }
}
