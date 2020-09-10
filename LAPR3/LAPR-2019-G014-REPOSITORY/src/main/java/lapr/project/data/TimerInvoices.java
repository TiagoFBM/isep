package lapr.project.data;

import lapr.project.utils.Utils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerInvoices {

    public void init(){

        try {
            JobDetail job = JobBuilder.newJob(Generator.class)
                    .withIdentity("validatorJOB", "group14")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("validatorTRIGGER","group14")
                    .withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute( Utils.DAY_TO_GENERATE_INVOICES,Utils.HOURS_TO_GENERATE_INVOCES, Utils.MINUTES_TO_GENERATE_INVOCES))
                    .build();
            Scheduler s = StdSchedulerFactory.getDefaultScheduler();
            s.start();
            s.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
