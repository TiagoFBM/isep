package fabrica.app.backoffice.console.presentation.rawMaterial;

import fabrica.production.domain.RawMaterialCategory;
import eapli.framework.visitor.Visitor;

public class RawMaterialCategoriesPrinter implements Visitor<RawMaterialCategory> {


    @Override
    public void visit(RawMaterialCategory visitee) {
        System.out.printf("%s   -    %s", visitee.identity().toString(), visitee.obtainBriefDescription().toString());
    }
}
