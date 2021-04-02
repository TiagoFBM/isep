using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Paths;
using mdv.DTO.Paths;
using mdv.Service.HttpRequests;

namespace mdv.Services {

    public class LineService : ILineService {

        public LineService () { }
        public async Task<string> GetLineOfPath (string path) {
            return await HttpRequest<string>.GetByID ("http://localhost:8080/api/line/path/" + path);
        }
    }
}