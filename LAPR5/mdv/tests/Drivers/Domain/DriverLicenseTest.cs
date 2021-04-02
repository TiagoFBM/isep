using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Drivers;
using Xunit;

namespace Tests.Drivers
{

    public class DriverLicenseTest
    {
        [Fact]
        public void CreateDriverLicense()
        {
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var license = new DriverLicense(numberDriverLicense, dateDriverLicense);

            Assert.True(license.GetType().Equals(new DriverLicense().GetType()));
        }

        [Fact]
        public void CreateNumberDriverLicenseWithInvalidFormat()
        {
            string numberDriverLicense = "X-X1231231231 10";
            string dateDriverLicense = "12/10/2020";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(numberDriverLicense, dateDriverLicense));
        }

        [Fact]
        public void CreateWithNullNumberDriverLicense()
        {
            string dateDriverLicense = "12/10/2020";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(null, dateDriverLicense));
        }

        [Fact]
        public void CreateWithEmptyNumberDriverLicense()
        {
            string numberDriverLicense = "";
            string dateDriverLicense = "12/10/2020";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(numberDriverLicense, dateDriverLicense));
        }

        [Fact]
        public void CreateWithNullDateDriverLicense()
        {
            string numberDriverLicense = "P-1111111 1";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(numberDriverLicense, null));
        }

        [Fact]
        public void CreateWithEmptyDateDriverLicense()
        {
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(numberDriverLicense, dateDriverLicense));
        }

        [Fact]
        public void CreateWithInvalidFormatDateDriverLicense()
        {
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12-10-2020";

            Assert.Throws<BusinessRuleValidationException>(() => new DriverLicense(numberDriverLicense, dateDriverLicense));
        }

    }
}