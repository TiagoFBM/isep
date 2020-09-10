package lapr.project.data;

import lapr.project.controller.GenerateInvoiceController;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class Generator implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        GenerateInvoiceController controller = new GenerateInvoiceController();
        controller.generateInvoices();
    }
}
