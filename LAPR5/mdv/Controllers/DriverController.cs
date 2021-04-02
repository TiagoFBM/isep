using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Drivers;
using mdv.Domain.Shared;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;
using mdv.Services;
using Microsoft.AspNetCore.Mvc;

namespace mdv.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class DriverController : ControllerBase
    {
        private readonly IDriverService driverService;
        private readonly IDriverTypeService driverTypeService;

        public DriverController(IDriverService service, IDriverTypeService driverTypeService)
        {
            this.driverService = service;
            this.driverTypeService = driverTypeService;
        }

        [HttpPost]
        public async Task<ActionResult<DriverDTO>> Create(CreatingDriverDTO dto)
        {
            try
            {

                List<string> listDriverTypes = new List<string>();

                foreach (var driverType in dto.driverTypes)
                {
                    var driverTypeId = await driverTypeService.GetDriverTypeByID(driverType);
                    listDriverTypes.Add(driverTypeId.code);
                }

                CreatingLicenseDTO licenseDTO = new CreatingLicenseDTO(dto.numberDriverLicense, dto.dateDriverLicense);

                CreatingCitizenCardDTO cardDTO = new CreatingCitizenCardDTO(dto.driverName, dto.birthDate, dto.citizenCardNumber, dto.driverNIF);

                var driver = await driverService.AddDriver(dto, licenseDTO, cardDTO);

                return CreatedAtAction(
                    nameof(GetById),
                    new { id = driver.Id },
                    driver
                );

            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }

        }

        [HttpGet("pages")]
        public async Task<ActionResult<List<FullDriverDTO>>> GetAll(int skip, int limit)
        {
            List<FullDriverDTO> listFullDrivers = new List<FullDriverDTO>();

            List<DriverTypeDTO> listDriverTypes = new List<DriverTypeDTO>();

            List<DriverDTO> listDriverDTO = await this.driverService.GetAll();

            foreach (var driverDTO in listDriverDTO)
            {
                foreach (var driverTypeDTO in driverDTO.driverTypes)
                {
                    DriverTypeDTO driverType = await driverTypeService.GetDriverTypeByID(driverTypeDTO);
                    listDriverTypes.Add(driverType);
                }

                FullDriverDTO fullDriverDTO = new FullDriverDTO(driverDTO.Id, driverDTO.mecanographicNumber, driverDTO.citizenCardDTO, driverDTO.entranceDate, driverDTO.departureDate, driverDTO.driverLicense, listDriverTypes);
                listFullDrivers.Add(fullDriverDTO);
            }
            return listFullDrivers.Skip(skip).Take(limit).ToList();
        }

        [HttpGet]
        public async Task<ActionResult<List<FullDriverDTO>>> GetAll()
        {
            List<FullDriverDTO> listFullDrivers = new List<FullDriverDTO>();

            List<DriverTypeDTO> listDriverTypes = new List<DriverTypeDTO>();

            List<DriverDTO> listDriverDTO = await this.driverService.GetAll();

            foreach (var driverDTO in listDriverDTO)
            {
                foreach (var driverTypeDTO in driverDTO.driverTypes)
                {
                    DriverTypeDTO driverType = await driverTypeService.GetDriverTypeByID(driverTypeDTO);
                    listDriverTypes.Add(driverType);
                }

                FullDriverDTO fullDriverDTO = new FullDriverDTO(driverDTO.Id, driverDTO.mecanographicNumber, driverDTO.citizenCardDTO, driverDTO.entranceDate, driverDTO.departureDate, driverDTO.driverLicense, listDriverTypes);
                listFullDrivers.Add(fullDriverDTO);
            }
            return listFullDrivers;
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<FullDriverDTO>> GetById(Guid id)
        {
            List<DriverTypeDTO> listDriverTypes = new List<DriverTypeDTO>();
            List<FullDriverDTO> listFullDrivers = new List<FullDriverDTO>();


            DriverDTO driverDTO = await driverService.GetById(new DriverID(id));

            foreach (var driverTypeDTO in driverDTO.driverTypes)
            {
                DriverTypeDTO driverType = await driverTypeService.GetDriverTypeByID(driverTypeDTO);
                listDriverTypes.Add(driverType);
            }

            FullDriverDTO fullDriverDTO = new FullDriverDTO(driverDTO.Id, driverDTO.mecanographicNumber, driverDTO.citizenCardDTO, driverDTO.entranceDate, driverDTO.departureDate, driverDTO.driverLicense, listDriverTypes);
            listFullDrivers.Add(fullDriverDTO);

            if (fullDriverDTO == null)
            {
                return NotFound();
            }

            return fullDriverDTO;
        }

        [HttpGet("count")]
        public async Task<ActionResult<int>> GetNumber()
        {
            return await this.driverService.GetNumber();
        }
    }
}