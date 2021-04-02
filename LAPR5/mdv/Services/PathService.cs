using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Paths;
using mdv.DTO.Paths;
using mdv.Service.HttpRequests;

namespace mdv.Services {

    public class PathService : IPathService {

        public PathService () { }
        public async Task<List<PathDTO>> GetAll () {
            return await HttpRequest<PathDTO>.GetAll ("http://localhost:8080/api/path");
        }

        public async Task<PathDTO> GetById (PathId id) {
            PathDTO path = await HttpRequest<PathDTO>.GetByID ("http://localhost:8080/api/path/" + id.ToString ());

            if (path == null) {
                throw new Exception ("Invalid path for code " + id.ToString ());
            }

            return path;
        }

        public async Task<int> GetPathDurationById (PathId id) {
            PathDTO path = await HttpRequest<PathDTO>.GetByID ("http://localhost:8080/api/path/" + id.ToString ());

            if (path == null) {
                throw new Exception ("Invalid path for code " + id.ToString ());
            }

            int duration = 0;

            foreach (var item in path.segments) {
                duration += item.travelTimeBetweenNodes;
            }

            return duration;
        }

    }
}