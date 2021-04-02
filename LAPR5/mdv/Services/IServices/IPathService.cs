using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Paths;
using mdv.DTO.Paths;

namespace mdv.Services {

    public interface IPathService {

        Task<List<PathDTO>> GetAll ();

        Task<PathDTO> GetById (PathId id);

        Task<int> GetPathDurationById (PathId id);
    }
}