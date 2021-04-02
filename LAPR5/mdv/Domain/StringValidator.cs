using System;
using System.Text.RegularExpressions;

namespace mdv.Domain.Validators
{

    public static class StringValidator
    {

        public static bool isStringEmptyOrNull(string value)
        {
            if (value == null)
            {
                return true;
            }
            return value.CompareTo("") == 0;
        }

        internal static bool isValidLicenseNumber(string value)
        {
            Regex rx = new Regex("P-[0-9]{7} [0-9]");
            Match m = rx.Match(value);

            if (!m.Success) return false;

            return m.Length == value.Length;
        }

        public static bool isValidTimestamp(string value)
        {
            //Formato: HH:MM:SS
            Regex rx = new Regex("([0-1]?[0-9]|2[0-3]):([0-5]?[0-9])?:([0-5]?[0-9])?");
            Match m = rx.Match(value);

            if (!m.Success) return false;

            return m.Length == value.Length;
        }

        public static bool isValidDate(string value)
        {
            //Formato: DD/MM/YYYY
            Regex rx = new Regex("([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/20[0-9][0-9]");
            Match m = rx.Match(value);

            if (!m.Success) return false;

            return m.Length == value.Length;

        }

        public static bool isValidBirthDate(string value)
        {
            Regex rx = new Regex("([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(200[0-2]|19[0-9][0-9])");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;
        }

        public static bool isValidEntranceDate(string value)
        {
            Regex rx = new Regex("([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/([0-9]{4})");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;
        }

        public static bool isValidDepartureDate(string value)
        {
            Regex rx = new Regex("([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/([0-9]{4})");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;
        }

        public static bool hasMinimumSize(string value)
        {
            return value.Length > 3;
        }

        public static bool hasMaximumSize(string value)
        {
            return value.Length <= 10;
        }

        public static bool isValidRegistration(string value)
        {
            Regex rx = new Regex("[A-Z]{2}[0-9]{2}[A-Z]{2}");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;
        }

        public static bool isValidVIN(string value)
        {
            Regex rx = new Regex("[A-Z0-9]{17}");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;
        }

        public static bool isValidMecanographicNumber(string value)
        {
            Regex rx = new Regex("[a-zA-Z0-9:_]{9}");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;

        }

        public static bool isValidAlphanumericString(string value)
        {
            Regex rx = new Regex("[a-zA-Z0-9:_]*");
            Match m = rx.Match(value);

            if (!m.Success) return false;
            return m.Length == value.Length;

        }


    }
}