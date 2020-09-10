package fabrica.app.backoffice.console.presentation.product;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;
import fabrica.production.application.ProductImporterController;
import fabrica.production.domain.Product;
import fabrica.production.importer.FileFormat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImportProductsUI extends AbstractUI {

    private ProductImporterController controller = new ProductImporterController();

    @Override
    protected boolean doShow() {
        int decision;
        String importfileName;
        String errorfileName;
        FileFormat format;
        List<Product> lista;

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
                lista = controller.importProducts(importfileName,errorfileName, format);
            } catch(IOException e){
                System.out.println(String.format("The system could not find the file %s, please try another file.",importfileName));
                return false;
            }

            if(!lista.isEmpty()){
                System.out.println("Some of the products were not possible to add to the system since they already exist in it.");
                System.out.println("Please select, for each one, if you want to keep the existing product or update it with the new data.");
            }

            for(Product p : lista){
                System.out.println();
                Product newProduct = controller.searchRepeatedProduct(p.identity(),p.obtainComercialCode());
                System.out.println(String.format("Product in Data Base: %s;%s;%s;%s;%s;%s",newProduct.identity(),newProduct.obtainComercialCode(),
                        newProduct.obtainBriefDescription(),newProduct.obtainFullDescription(),newProduct.obtainCategory(),newProduct.obtainUnit()));
                System.out.println(String.format("New Product: %s;%s;%s;%s;%s;%s",p.identity(),p.obtainComercialCode(),
                        p.obtainBriefDescription(),p.obtainFullDescription(),p.obtainCategory(),p.obtainUnit()));

                System.out.println("0 -> Keep\n1 -> Update\n");
                do{
                    decision = Console.readInteger("Choose an option: ");
                }while (decision!=0 && decision!=1);

                if (decision==1){
                    newProduct.updateBriefDescription(p.obtainBriefDescription().toString());
                    newProduct.updateFullDescription(p.obtainFullDescription().toString());
                    newProduct.updateCategory(p.obtainCategory().toString());
                    newProduct.updateUnit(p.obtainUnit().toString());
                    controller.updateProduct(newProduct);
                    System.out.println("Product Updated.");
                } else {
                    controller.keepProduct();
                    System.out.println("Product Kept.");
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
