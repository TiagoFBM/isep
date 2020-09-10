package fabrica.infrastructure.bootstrapers.production;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import fabrica.production.application.AddNewRawMaterialCategoryController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialCategoryBootstrap implements Action {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialBootstrap.class);

    @Override
    public boolean execute() {
        String code1 = "M01";
        String code2 = "FE01";
        String code3 = "ACO1";
        String code4 = "PL01";

        String description1 = "Madeira";
        String description2 = "Ferro";
        String description3 = "Aço";
        String description4 = "Plástico";

        register(code1,description1);
        register(code2,description2);
        register(code3,description3);
        register(code4,description4);

        return true;
    }

    private void register(String code, String description) {
        final AddNewRawMaterialCategoryController controller = new AddNewRawMaterialCategoryController();
        try {
            controller.registerRawMaterialCategory(code,description);

        } catch(final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", code);
            LOGGER.trace("Assuming existing record", e);
        }
    }


}
