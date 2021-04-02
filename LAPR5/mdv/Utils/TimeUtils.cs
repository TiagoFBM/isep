using System;
using mdv.Domain.Times;

namespace mdv.Utils.UtilsTime {

    public static class TimeUtils {

        public static string fromSecToString (int timeInSeconds) {
            int hours = timeInSeconds / 3600;
            int min = (timeInSeconds % 3600) / 60;
            int sec = (timeInSeconds % 3600) % 60;

            if (hours >= 24) {
                hours = hours - 24;
            }

            return hours + ":" + min + ":" + sec;
        }

        public static int fromTimeToSec (Time time) {

            string[] timeArray = time.ToString ().Split (":");

            int sec = Int32.Parse (timeArray[2]);
            int min = Int32.Parse (timeArray[1]) * 60;
            int hour = Int32.Parse (timeArray[0]) * 3600;

            if (hour >= 86400) {
                hour = hour - 86400;
            }

            return hour + min + sec;
        }

        public static int fromStringToSec (string time) {
            string[] timeArray = time.Split (":");

            int sec = Int32.Parse (timeArray[2]);
            int min = Int32.Parse (timeArray[1]) * 60;
            int hour = Int32.Parse (timeArray[0]) * 3600;

            if (hour >= 86400) {
                hour = hour - 86400;
            }

            return hour + min + sec;
        }

        public static int timeStringDifferenceInSeconds (string t1, string t2) {
            return TimeUtils.fromStringToSec(t1) - TimeUtils.fromStringToSec(t2);
        }

    }
}