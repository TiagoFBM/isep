package fabrica.infrastructure.bootstrapers.productBootstrap;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.application.AddNewProductController;
import fabrica.production.application.SpecifyProductionSheetController;
import fabrica.production.domain.*;
import fabrica.production.dto.ProductionSheetLineDTO;
import fabrica.production.repositories.ProductRepository;
import fabrica.production.repositories.RawMaterialRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ProductBootstrap implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProductBootstrap.class);
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final RawMaterialRepository rmRepository = PersistenceContext.repositories().rawMaterials();
    private final SpecifyProductionSheetController sheetController = new SpecifyProductionSheetController();



    @Override
    public boolean execute() {

        String fabricationCode1 = ("001");
        String comercialCode1 = ("Cimento01");
        String briefDescription1 = ("Cimento");
        String fullDescription1 = ("Cimento indicado para cerâmica de médio formato.");
        String productCategory1 = ("Cimento Cola Flex Médio");
        String unit1 = Units.KILOGRAMS.toString();
        Product p1 = register(fabricationCode1,comercialCode1,briefDescription1,fullDescription1,unit1, productCategory1);

        List<ProductionSheetLineDTO> linesDTO = new LinkedList<>();
        linesDTO.add(new ProductionSheetLineDTO(rmRepository.ofIdentity(AlfanumericCode.valueOf("100")).get(), 15, Units.UN.toString()));
        sheetController.specifyProductionSheet(p1,linesDTO,1000,Units.UN.toString());

        String fabricationCode2 = ("002");
        String comercialCode2 = ("Areia02");
        String briefDescription2 = ("Areia.");
        String fullDescription2 = ("Areia de rio amarela.");
        String productCategory2 = ("Areia Rio");
        String unit2 = Units.KILOGRAMS.toString();
        register(fabricationCode2,comercialCode2,briefDescription2,fullDescription2, unit2, productCategory2);

        String fabricationCode3 = ("003");
        String comercialCode3 = ("Pasta03");
        String briefDescription3 = ("Pasta niveladora");
        String fullDescription3 = ("Pasta niveladora para reparar e preparar solos");
        String productCategory3 = ("AXTON PLUS 15MM");
        String unit3 = Units.TONNE.toString();
        register(fabricationCode3,comercialCode3,briefDescription3,fullDescription3, unit3, productCategory3);

        String fabricationCode4 = ("004");
        String comercialCode4 = ("Placa04");
        String briefDescription4 = ("Placa de gesso ");
        String fullDescription4 = ("Placa de gesso cartonado FIRE anti-fogo de tipo F");
        String productCategory4 = ("FIBROPLAC FIRE BA13");
        String unit4 = Units.TONNE.toString();
        register(fabricationCode4,comercialCode4,briefDescription4,fullDescription4,unit4,productCategory4);

        String fabricationCode5 = ("005");
        String comercialCode5 = ("AreiaSuja01");
        String briefDescription5 = ("Areia suja");
        String fullDescription5 = ("Areia indecente para uso");
        String productCategory5 = ("Areia");
        String unit5 = Units.TONNE.toString();
        register(fabricationCode5,comercialCode5,briefDescription5,fullDescription5,unit5,productCategory5);



        return true;

    }

    private Product register(String fabricationCode, String comercialCode, String briefDescription, String fullDescription, String productCategory, String units){
        final AddNewProductController controller = new AddNewProductController();
        try {
            return controller.registerProduct(fabricationCode,comercialCode,briefDescription,fullDescription,productCategory, units);
        }catch (final IntegrityViolationException | ConcurrencyException e){
            LOGGER.warn("Assuming {} already exits (activate trace log for details", fabricationCode);
            LOGGER.trace("Assuming existing record", e);
        }
        return null;
    }
}
