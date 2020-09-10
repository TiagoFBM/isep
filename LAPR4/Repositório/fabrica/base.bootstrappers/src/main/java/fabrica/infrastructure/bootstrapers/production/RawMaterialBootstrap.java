package fabrica.infrastructure.bootstrapers.production;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.application.AddNewRawMaterialController;
import fabrica.production.domain.*;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RawMaterialBootstrap implements Action{
    private static final Logger LOGGER = LogManager.getLogger(RawMaterialBootstrap.class);
    private final RawMaterialCategoryRepository rawMaterialCategoryRepo = PersistenceContext.repositories().rawMaterialCategories();

    public RawMaterialCategory getRawMaterialCategory(ShortAlfanumericCode code){
        return rawMaterialCategoryRepo.ofIdentity(code).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        final ShortAlfanumericCode codeCategory1 = ShortAlfanumericCode.valueOf("M01");
        final ShortAlfanumericCode codeCategory2 = ShortAlfanumericCode.valueOf("FE01");
        final ShortAlfanumericCode codeCategory3 = ShortAlfanumericCode.valueOf("ACO1");
        final ShortAlfanumericCode codeCategory4 = ShortAlfanumericCode.valueOf("PL01");

        final RawMaterialCategory category1 = getRawMaterialCategory(codeCategory1);
        final RawMaterialCategory category2 = getRawMaterialCategory(codeCategory2);
        final RawMaterialCategory category3 = getRawMaterialCategory(codeCategory3);
        final RawMaterialCategory category4 = getRawMaterialCategory(codeCategory4);

        String code1 = "100";
        String description1 = "Madeira seca que poderá ser utilizada para a produção de cadeiras";
        register(code1, description1, category1,"fichTecR1.pdf", "Files");

        String code2 = "101";
        String description2 = "Ferro que permite distribuir e reter o calor uniformemente";
        register(code2, description2, category2, "fichTecR2.pdf", "Files");

        String code3 = "102";
        String description3 = "Ferragens com ângulo regulável para maior flexibilidade";
        register(code3,description3, category3,"fichTecR3.pdf", "Files");

        String code4 = "103";
        String description4 = "O verso impermeável protege o colchão.";
        register(code4,description4, category4,"fichTecR4.pdf", "Files");
        ;
        return true;
    }

    private void register(String code, String description, RawMaterialCategory category, String nameFile, String pathFile) {
        final AddNewRawMaterialController controller = new AddNewRawMaterialController();
        try {
            controller.registerRawMaterial(code,description,category,nameFile,pathFile);

        } catch(final IntegrityViolationException | ConcurrencyException | IOException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", code);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
