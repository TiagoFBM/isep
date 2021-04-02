using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Paths;
using mdv.DTO.Paths;

namespace mdv.Services {

    public interface ILineService {

        Task<string> GetLineOfPath (string id);

    }
}