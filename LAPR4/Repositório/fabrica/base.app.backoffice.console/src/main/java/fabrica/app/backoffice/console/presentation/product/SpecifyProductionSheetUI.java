package fabrica.app.backoffice.console.presentation.product;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.production.application.SpecifyProductionSheetController;
import fabrica.production.domain.*;
import fabrica.production.dto.ProductionSheetLineDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecifyProductionSheetUI extends AbstractUI {

    private SpecifyProductionSheetController controller = new SpecifyProductionSheetController();

    @Override
    protected boolean doShow() {
        final List<Product> products = this.controller.allProducts();
        final List<RawMaterial> rawMaterials = this.controller.allRawMaterials();
        List<ProductionSheetLineDTO> dtoList = new ArrayList<>();
        Product selected;

        if (!controller.listProductsWithoutProductionSheet().isEmpty()) {
            SelectWidget<Product> productSelectWidget = new SelectWidget<>("--- Products Without Production Sheet ---", controller.listProductsWithoutProductionSheet());
            productSelectWidget.show();
            selected = productSelectWidget.selectedElement();
        } else {
            System.out.println("There are no Product Without Production Sheet in the system.");
            return false;
        }

        boolean continuar = true;
        int choice;
        String code;
        Units unit;
        int quantity;
        SelectWidget<Units> unidades;

        do {
            System.out.println("------------- Menu -------------");
            System.out.println("1 - Insert Product");
            System.out.println("2 - Insert Raw Material");
            System.out.println("3 - Consult all Products");
            System.out.println("4 - Consult all Raw Materials");
            System.out.println("5 - Consult Selected");
            System.out.println("6 - Remove Selected");
            System.out.println("7 - Confirm Production Sheet");
            System.out.println("0 - Exit");
            choice = Console.readOption(1, 7, 0);

            if (choice == 0) {
                continuar = false;
            }

            switch (choice) {
                case 1:
                    Product p;
                    code = Console.readNonEmptyLine("Insert Fabrication Code of Product: ", "Please insert a valid code.");
                    quantity = Console.readInteger("Insert the Quantity needed to produce: ");
                    unidades = new SelectWidget<>("Possible Units", Arrays.asList(Units.values()));
                    unidades.show();
                    unit = unidades.selectedElement();

                    try {
                        if ((p = controller.getProductWithCode(AlfanumericCode.valueOf(code))) != null) {
                            if (p.equals(selected)) {
                                System.out.println("You can't add the product to its own production sheet.");
                                break;
                            }
                            if (unit!=null){
                                if(!controller.verifyProductIsUnique(p,dtoList)){
                                    System.out.println("The selected product is already added.");
                                    break;
                                }
                                ProductionSheetLineDTO dto = new ProductionSheetLineDTO(p, quantity, unit.toString());
                                dtoList.add(dto);
                                System.out.println("The product was successfully added.");
                            } else {
                                System.out.println("The product was not added.");
                            }
                        } else {
                            System.out.println("The selected Product doesn't exist in the system.");
                            System.out.println("To consult the products that exist in the system select the option 4.");
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Invalid data : " + ex.getMessage());
                    }

                    break;
                case 2:
                    RawMaterial rm;
                    code = Console.readNonEmptyLine("Insert Raw Material Code: ", "Please insert a valid code.");
                    quantity = Console.readInteger("Insert the Quantity needed to produce: ");
                    unidades = new SelectWidget<>("Possible Units", Arrays.asList(Units.values()));
                    unidades.show();
                    unit = unidades.selectedElement();

                    try {
                        if ((rm = controller.getRawMaterialWithCode(AlfanumericCode.valueOf(code))) != null) {
                            if (unit!=null){
                                if(!controller.verifyRawMaterialIsUnique(rm,dtoList)){
                                    System.out.println("The selected raw material is already added.");
                                    break;
                                }
                                ProductionSheetLineDTO dto = new ProductionSheetLineDTO(rm, quantity, unit.toString());
                                dtoList.add(dto);
                                System.out.println("The raw material was successfully added.");
                            } else {
                                System.out.println("The raw material was not added.");
                            }
                        } else {
                            System.out.println("The selected Raw Material doesn't exist in the system.");
                            System.out.println("To consult the raw materials that exist in the system select the option 5.");
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Invalid data : " + ex.getMessage());
                    }
                    break;
                case 3:
                    if (!products.isEmpty()){
                        for (Product prod : products) {
                            System.out.println(prod.toString());
                        }
                    } else {
                        System.out.println("There are no products to be listed.");
                    }
                    break;
                case 4:
                    if(!rawMaterials.isEmpty()){
                        for (RawMaterial raw : rawMaterials) {
                            System.out.println(raw);
                        }
                    } else {
                        System.out.println("There are no raw materials to be listed.");
                    }
                    break;
                case 5:
                    if (!dtoList.isEmpty()) {
                        for (ProductionSheetLineDTO line : dtoList) {
                            if (line.product != null) {
                                System.out.println(line.product);
                            } else {
                                System.out.println(line.rawMaterial);
                            }
                        }
                    } else {
                        System.out.println("You have not selected any Product/Raw Material.");
                    }
                    break;
                case 6:
                    if (!dtoList.isEmpty()) {

                        SelectWidget<ProductionSheetLineDTO> selectedItems = new SelectWidget<>("Selected Items", dtoList);
                        selectedItems.show();
                        ProductionSheetLineDTO selectedElement = selectedItems.selectedElement();

                        if (selectedElement!=null){
                            if (dtoList.remove(selectedElement)) {
                                System.out.println("Item removed sucessfuly.");
                            } else {
                                System.out.println("That item couldn't be removed.");
                            }
                        } else {
                            System.out.println("No item was selected.");
                        }

                    } else {
                        System.out.println("You have not selected any Product/Raw Material.");
                    }
                    break;
                case 7:
                    if (!dtoList.isEmpty()) {
                        int standard = Console.readInteger("Standard Quantity:");
                        unidades = new SelectWidget<>("Possible Units", Arrays.asList(Units.values()));
                        unidades.show();
                        unit = unidades.selectedElement();

                        if (unit!=null){
                            if(!controller.specifyProductionSheet(selected, dtoList, standard, unit.toString())){
                                System.out.println(String.format("The product [%s] does not have a production sheet so it can only be consumed.\n",selected.toString()));
                            }
                            continuar = false;
                        } else {
                            System.out.println("The Production Sheet was not registed.");
                        }
                    } else {
                        System.out.println("You can't create an empty Production Sheet.");
                    }
                    break;
            }
        } while (continuar);
        return true;
    }

    @Override
    public String headline() {
        return "--- Production Sheet ---";
    }
}