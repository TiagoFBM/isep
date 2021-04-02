using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace mdv.Service.HttpRequests {
    public static class HttpRequest<T> {

        static HttpClient client = new HttpClient ();

        public static async Task<List<T>> GetAll (string path) {

            var lista = new List<T>();

            HttpResponseMessage response = await client.GetAsync(path);

            if (response.IsSuccessStatusCode) {
                string data = await response.Content.ReadAsStringAsync ();
                lista = JsonConvert.DeserializeObject<List<T>>(data);
            }

            return lista;
        }

        public static async Task<T> GetByID (string path) {

            T newObject = default(T);

            HttpResponseMessage response = await client.GetAsync(path);

            if (response.IsSuccessStatusCode) {
                string data = await response.Content.ReadAsStringAsync();
                newObject = JsonConvert.DeserializeObject<T>(data);
            }

            return newObject;
        }

    }
}