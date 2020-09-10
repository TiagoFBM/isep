package fabrica.app.backoffice.console.presentation.errornotificationmanagement;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import fabrica.errornotificationmanagement.application.ListProcessorErrorNotificationsController;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListProcessorErrorNotificationsUI extends AbstractUI {
    private ListProcessorErrorNotificationsController controller = new ListProcessorErrorNotificationsController();


    @Override
    protected boolean doShow() {
        String filter = "";
        System.out.println("1 - Consult by Production Lines");
        System.out.println("2 - Consult by Error Type");
        System.out.println("0 - Exit");
        int op;
        op = Console.readInteger("Choose an option: ");

        do{
            switch (op){
                case 1:
                    ProductionLine po;
                    Iterable <ProductionLine> productionLines = controller.getAllProductionLines();
                    SelectWidget<ProductionLine> selectWidget2 = new SelectWidget("Production Lines", productionLines);

                    do{
                        selectWidget2.show();
                        po = selectWidget2.selectedElement();
                    }while (po==null);

                    List<ErrorNotification> list = controller.consultErrorNotificationsUntreatedByProductionLine(po);
                    for (ErrorNotification en : list){
                        System.out.println(en.toString());
                    }

                    break;
                case 2 :
                    List<ErrorType> listErrors = new ArrayList<>(Arrays.asList(ErrorType.values()));
                    SelectWidget<ErrorType> select = new SelectWidget<>("Type of errors", listErrors);
                    select.show();
                    if (select.selectedOption() == 0){
                        break;
                    }
                    List<ErrorNotification> list1 = controller.consultErrorNotificationsUntreatedByErrorType(select.selectedElement());

                    for (ErrorNotification en : list1){
                        System.out.println(en.toString());
                    }

                case 0:
                    return false;
                default:
                    break;
            }
        }while (op>2);




        return true;
    }

    @Override
    public String headline() {
        return "List Processor Error Notifications";
    }
}
