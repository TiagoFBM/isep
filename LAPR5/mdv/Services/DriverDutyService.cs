using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.DriverDutys;
using mdv.Domain.Shared;
using mdv.Domain.WorkBlocks;
using mdv.DTO.DriverDutys;
using mdv.DTO.Nodes;
using mdv.Repository.DriverDutys;
using mdv.Repository.WorkBlocks;
using mdv.Repository.VehicleDutys;
using mdv.Service.HttpRequests;
using mdv.Mappers;
using System.Linq;

namespace mdv.Services
{
    public class DriverDutyService : IDriverDutyService
    {
        private readonly IUnitOfWork _unitOfWork;

        private readonly IDriverDutyRepository _driverDutyRepository;

        private readonly IWorkBlockRepository _workBlockRepository;

        private readonly IVehicleDutyRepository _repoVehicleDutyRepository;

        private readonly DriverDutyMapper _driverDutyMapper;

        public DriverDutyService(IDriverDutyRepository _driverDutyRepository, IWorkBlockRepository _workBlockRepository, IVehicleDutyRepository repoVehicleDutyRepository, IUnitOfWork unitOfWork)
        {
            this._driverDutyRepository = _driverDutyRepository;
            this._workBlockRepository = _workBlockRepository;
            this._repoVehicleDutyRepository = repoVehicleDutyRepository;
            this._driverDutyMapper = new DriverDutyMapper();
            this._unitOfWork = unitOfWork;
        }

        public async Task<List<DriverDutyDTO>> GetAll()
        {
            var driverDutyList = await this._driverDutyRepository.GetAllAsync();
            var driverDutyDTOList = new List<DriverDutyDTO>();

            foreach (var driverDuty in driverDutyList)
            {
                var driverDutyDTO = _driverDutyMapper.DomainToDTO(driverDuty);
                driverDutyDTOList.Add(driverDutyDTO);
            }

            return driverDutyDTOList;
        }

        public async Task<List<DriverDutyDTO>> DeleteAllAsync()
        {
            List<DriverDuty> listDriverDutys = await this._driverDutyRepository.GetAllAsync();
            List<DriverDutyDTO> listDriverDutyDTO = new List<DriverDutyDTO>();

            foreach (DriverDuty driverDuty in listDriverDutys)
            {
                this._driverDutyRepository.Remove(driverDuty);
                listDriverDutyDTO.Add(this._driverDutyMapper.DomainToDTO(driverDuty));
            }

            await this._unitOfWork.CommitAsync();

            return listDriverDutyDTO;

        }

        public async Task<DriverDutyDTO> GetById(DriverDutyId id)
        {
            var driverDuty = await this._driverDutyRepository.GetByIdAsync(id);

            if (driverDuty == null)
            {
                return null;
            }

            return _driverDutyMapper.DomainToDTO(driverDuty);
        }

        public async Task<DriverDutyDTO> AddDriverDuty(CreatingDriverDutyDTO driverDutyDTO)
        {

            List<WorkBlock> workBlocks = new List<WorkBlock>();
            List<WorkBlock> workBlocksAux = new List<WorkBlock>();

            foreach (var workBlockId in driverDutyDTO.listWorkBlocks)
            {
                var workBlock = await _workBlockRepository.GetByIdAsync(new WorkBlockID(workBlockId));
                workBlocks.Add(workBlock);
                workBlocksAux.Add(workBlock);
            }

            for (int i = 1; i < workBlocks.Count; i++)
            {
                var firstVehicleDutyId = await this._repoVehicleDutyRepository.getVehicleDutyByWorkblock(workBlocks[i]);
                var secondVehicleDutyId = await this._repoVehicleDutyRepository.getVehicleDutyByWorkblock(workBlocks[i - 1]);
                if (!firstVehicleDutyId.Id.Equals(secondVehicleDutyId.Id))
                {
                    if (workBlocks[i - 1].tripList.Count > 0 && workBlocks[i].tripList.Count > 0) {
                        var lastNodeOfLastWorkblok = workBlocks[i - 1].tripList.Last().nodePassageList.Last().nodeID.id;

                        var firstNodeOfActualWorkblock = workBlocks[i].tripList.First().nodePassageList.First().nodeID.id;
                        // if (lastNodeOfLastWorkblok.Equals(firstNodeOfActualWorkblock))
                        // {
                            NodeDTO firstNode = await HttpRequest<NodeDTO>.GetByID("http://localhost:8080/api/node/" + firstNodeOfActualWorkblock);
                            if (!firstNode.isReliefPoint)
                            {
                                throw new BusinessRuleValidationException(firstNode.code + " invalid: this node is not a relief point.");

                            }
                        // }
                        // else
                        // {
                        //     throw new BusinessRuleValidationException("When two workblocks have a diferent vehicleDuty the last node of the last workblock must be the same as the first of actual workblock");
                        // }
                    }
                }
            }

            var driverDuty = new DriverDuty(
                driverDutyDTO.driverDutyCode,
                workBlocks
            );

            var savedDriverDuty = await this._driverDutyRepository.AddAsync(driverDuty);

            await this._unitOfWork.CommitAsync();

            return this._driverDutyMapper.DomainToDTO(savedDriverDuty);

        }


    }
}