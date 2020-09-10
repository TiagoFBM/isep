package fabrica.app.backoffice.console.presentation.product;

import eapli.framework.presentation.console.AbstractUI;
import fabrica.production.application.SearchProductsWithoutProductionSheetController;
import fabrica.production.domain.Product;

public class SearchProductsWithoutProductionSheetUI extends AbstractUI {
    SearchProductsWithoutProductionSheetController controller = new SearchProductsWithoutProductionSheetController();


    @Override
    protected boolean doShow() {

        int i = 1;
        for (Product p : controller.allProductsWithoutSheetOfProduction()){
            System.out.printf("-- Product %d: --\n" , i);
            i++;
            System.out.println(p.toString());
        }

        return true;
    }

    @Override
    public String headline() {
        return "----- Products Without Production Sheet -----";
    }
}
