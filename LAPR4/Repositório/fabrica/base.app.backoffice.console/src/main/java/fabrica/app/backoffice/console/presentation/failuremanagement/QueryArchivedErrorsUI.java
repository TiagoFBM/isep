package fabrica.app.backoffice.console.presentation.failuremanagement;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.errornotificationmanagement.application.QueryArchivedErrorsController;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.scm.messagesmanagement.domain.Failure;
import fabrica.scm.messagesmanagement.domain.FailureType;

import java.util.Arrays;
import java.util.Calendar;

public class QueryArchivedErrorsUI extends AbstractUI {

    QueryArchivedErrorsController controller  = new QueryArchivedErrorsController();

    @Override
    protected boolean doShow() {
        boolean continuar = true;
        int choice;
        Calendar beginDate;
        Calendar finishDate;

        SelectWidget<FailureType> types = new SelectWidget<>("-- Possible Filters --", Arrays.asList(FailureType.values()));
        do {
            System.out.println("------------- Menu -------------");
            System.out.println("1 - Consult Archive Errors by Temporal Filter");
            System.out.println("2 - Consult Archive Errors by Errors Filter");
            System.out.println("0 - Exit");
            choice = Console.readOption(1, 2, 0);

            if (choice == 0) {
                continuar = false;
            }

            switch (choice) {
                case 1:
                    do {
                        beginDate = Console.readCalendar("Start date: (DD-MM-YYYY)");
                        finishDate = Console.readCalendar("End date: (DD-MM-YYYY)");

                        if (beginDate.after(finishDate)){
                            System.out.printf("\n" +
                                    "The start date is greater than the end date ");
                        }
                    }while (beginDate.after(finishDate));

                    if (controller.listArchivedErrorsByDate(beginDate, finishDate).isEmpty() ){
                        System.out.println("There are no Archived Errors in the System.");
                        break;
                    }

                    try {
                        int i = 1;
                        for (ErrorNotification f : controller.listArchivedErrorsByDate(beginDate,finishDate)) {
                            System.out.printf("-- Archived Error %d: --\n", i);
                            i++;
                            System.out.println(f.toString());
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Invalid data : " + ex.getMessage());
                    }

                    break;
                case 2:
                    types.show();
                    String t = types.selectedElement().name();
                    if (controller.listArchivedErrorsByWithErrorType(t).isEmpty()){
                        System.out.println("There are no Archived Errors of that type in the System.");
                        break;
                    }
                    try {
                        int i = 1;
                        for (ErrorNotification f : controller.listArchivedErrorsByWithErrorType(t)) {
                            System.out.printf("-- Archived Error  %d: --\n", i);
                            i++;
                            System.out.println(f.toString());
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Invalid data : " + ex.getMessage());
                    }
                    break;

            }
        } while (continuar);
        return true;
    }

    @Override
    public String headline() {
        return "--- Consult Archive Errors ---";
    }
}
