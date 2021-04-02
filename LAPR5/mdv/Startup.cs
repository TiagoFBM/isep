using mdv.Domain.Shared;
using mdv.Infrastructure;
using mdv.Infrastructure.Drivers;
using mdv.Infrastructure.Shared;
using mdv.Infrastructure.Trips;
using mdv.Infrastructure.VehicleDutys;
using mdv.Infrastructure.Vehicles;
using mdv.Infrastructure.WorkBlocks;
using mdv.Infrastructure.DriverDutys;
using mdv.Repository.Drivers;
using mdv.Repository.Trips;
using mdv.Repository.VehicleDutys;
using mdv.Repository.Vehicles;
using mdv.Repository.WorkBlocks;
using mdv.Repository.DriverDutys;
using mdv.Services;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace mdv
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            services.AddCors();

            services.AddDbContext<DDDmdvDBContext>(opt =>
               //opt.UseInMemoryDatabase("MDVLocalDB")
               opt.UseSqlServer(Configuration.GetConnectionString("dbUrl"))
               .ReplaceService<IValueConverterSelector, StronglyEntityIdValueConverterSelector>());

            ConfigureMyServices(services);

            services.AddControllers().AddNewtonsoftJson();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseCors(builder => builder
               .AllowAnyOrigin()
               .AllowAnyMethod()
               .AllowAnyHeader());

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });

            using (var serviceScope = app.ApplicationServices.GetService<IServiceScopeFactory>().CreateScope())
            {
                var context = serviceScope.ServiceProvider.GetRequiredService<DDDmdvDBContext>();
                context.Database.Migrate();
            }

        }

        public void ConfigureMyServices(IServiceCollection services)
        {
            services.AddTransient<IUnitOfWork, UnitOfWork>();

            services.AddTransient<ITripRepository, TripRepository>();
            services.AddTransient<ITripService, TripService>();
            services.AddTransient<IPathService, PathService>();
            services.AddTransient<ILineService, LineService>();
            services.AddTransient<IDriverRepository, DriverRepository>();
            services.AddTransient<IDriverService, DriverService>();
            services.AddTransient<IDriverTypeService, DriverTypeService>();
            services.AddTransient<IVehicleTypeService, VehicleTypeService>();
            services.AddTransient<IVehicleRepository, VehicleRepository>();
            services.AddTransient<IVehicleService, VehicleService>();
            services.AddTransient<IVehicleDutyRepository, VehicleDutyRepository>();
            services.AddTransient<IVehicleDutyService, VehicleDutyService>();
            services.AddTransient<IWorkBlockRepository, WorkBlockRepository>();
            services.AddTransient<IWorkBlockService, WorkBlockService>();
            services.AddTransient<IDriverDutyRepository, DriverDutyRepository>();
            services.AddTransient<IDriverDutyService, DriverDutyService>();
            //services.AddTransient<ICategoryRepository,CategoryRepository>();
            //services.AddTransient<CategoryService>();

        }
    }
}