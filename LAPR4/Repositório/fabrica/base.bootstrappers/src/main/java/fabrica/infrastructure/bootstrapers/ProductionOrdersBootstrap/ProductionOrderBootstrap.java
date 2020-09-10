package fabrica.infrastructure.bootstrapers.ProductionOrdersBootstrap;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.time.util.Calendars;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.production.repositories.ProductRepository;
import fabrica.productordersmanagement.aplication.AddProductionOrderController;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.LinkedList;

public class ProductionOrderBootstrap implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ProductionOrderBootstrap.class);
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionsOrders();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();


    @Override
    public boolean execute() {

        AlfanumericCode fabricationCode1 = AlfanumericCode.valueOf("001");
        Product p1 = null;
        if (productRepository.ofIdentity(fabricationCode1).isPresent()){
            p1 = productRepository.ofIdentity(fabricationCode1).get();
        }
        String idCode = "100003365";
        Calendar emissionDate = Calendars.of(2020,05,16);
        Calendar previsionDate = Calendars.of(2020, 05,16);

        Quantity quantity = new Quantity(52, "UN");

        LinkedList orders = new LinkedList();

        orders.add("EC2020/00034");
        orders.add("EC2020/00035");
        orders.add("EC2020/00036");

        register(idCode,  emissionDate,  previsionDate, p1, quantity.obtainValor(), quantity.obtainUnity().toString(), orders);

        return true;

    }

    private void register(String idCode, Calendar emissionDate, Calendar previsionDate, Product p, int quant, String unity, LinkedList orders){
        final AddProductionOrderController controller = new AddProductionOrderController();
        try {
            controller.registerProductionOrder(idCode, emissionDate, previsionDate, p, quant, unity, orders);
        }catch (final IntegrityViolationException | ConcurrencyException e){
            LOGGER.warn("Assuming {} already exits (activate trace log for details", idCode);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
