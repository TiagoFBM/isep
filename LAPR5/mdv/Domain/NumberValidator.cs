
namespace mdv.Domain.Validators
{
    public static class NumberValidator
    {
        public static bool isNegative(long number)
        {
            return number < 0;
        }

        public static bool isValidCitizenCardNumber(long number)
        {
            return number.ToString().Length == 8;
        }

        public static bool isValidNIF(long number)
        {
            return number.ToString().Length == 9;
        }
    }

}