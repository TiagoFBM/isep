package fabrica.app.backoffice.console.presentation.productionorder;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;
import fabrica.production.importer.FileFormat;
import fabrica.productordersmanagement.aplication.ProductionOrdersImporterController;
import fabrica.productordersmanagement.domain.ProductionOrder;
import java.io.IOException;
import java.util.List;

public class ImportProductionOrdersUI extends AbstractUI {

    private ProductionOrdersImporterController controller = new ProductionOrdersImporterController();

    @Override
    protected boolean doShow() {
        int decision;
        String importfileName;
        String errorfileName;
        FileFormat format;
        List<ProductionOrder> lista;

        do {
            importfileName = Console.readLine("Import file Name (With Extension): ");
            if (importfileName.trim().split("\\.").length<2){
                System.out.println("Please insert a filename with extension.");
            }
        } while(importfileName.trim().split("\\.").length<2);

        do {
            errorfileName = Console.readLine("Error file Name (Without Extension - Name Only): ");
            if (errorfileName.trim().split("\\.").length>=2){
                System.out.println("Please insert a filename without extension.");
            }
        } while (errorfileName.trim().split("\\.").length>=2);


        if(importfileName.trim().split("\\.")[1].equalsIgnoreCase("csv")){
            format = FileFormat.CSV;
        } else {
            System.out.println("Something unexpected happened, please try again.");
            return false;
        }

        try{
            lista = controller.importProductionOrders(importfileName,errorfileName, format);
        } catch(IOException e){
            System.out.println(String.format("The system could not find the file %s, please try another file.",importfileName));
            return false;
        }

        if(!lista.isEmpty()){
            System.out.println("Some of the production orders were not possible to add to the system since they already exist in it.");
            System.out.println("Please select, for each one, if you want to keep the existing production order or update it with the new data.");
        }

        for(ProductionOrder order : lista){
            System.out.println();
            ProductionOrder oldProductionOrder = controller.searchRepeatedProductionOrder(order.identity());
            System.out.println(String.format("-- Production Order in System--\n%s",oldProductionOrder.toString()));
            System.out.println(String.format("--New Production Order--\n%s",order.toString()));

            System.out.println("0 -> Keep\n1 -> Update\n");
            do{
                decision = Console.readInteger("Choose an option: ");
            }while (decision!=0 && decision!=1);

            if (decision==1){
                oldProductionOrder.updateEmissionDate(order.obtainEmissionDate());
                oldProductionOrder.updatePrevisionDate(order.obtainPrevisionDate());
                oldProductionOrder.updateProduct(order.obtainProduct());
                oldProductionOrder.updateQuantity(order.obtainQuantity());
                oldProductionOrder.updateUnit(order.obtainUnit());
                oldProductionOrder.updateRequests(order.obtainOrders());

                controller.updateProductionOrder(oldProductionOrder);
                System.out.println("Production Order Updated.");
            } else {
                controller.keepProductionOrder();
                System.out.println("Production Order Kept.");
            }
        }

        System.out.println("\n --- Import Report --- \n");
        System.out.println(controller.importationReport());

        return true;
    }

    @Override
    public String headline() {
        return "--- Import Products ---";
    }
}
