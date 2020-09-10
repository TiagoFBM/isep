package fabrica.productordersmanagement.importer;

import java.util.Arrays;
import java.util.Calendar;

import eapli.framework.time.util.Calendars;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.errorexporter.ErrorExporter;
import fabrica.production.repositories.ProductRepository;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductionOrderImporterService {

    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionsOrders();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    private int imported = 0;
    private int withErrors = 0;

    public List<ProductionOrder> importer(String filename, ProductionOrderImporter importer, ErrorExporter error) throws IOException {
        List<ProductionOrder> orders = new ArrayList<>();
        List<String> requests = new ArrayList<>();
        try {
            importer.begin(filename);
            importer.readLine();
            Product p = null;
            while (importer.hasNextLine()) {
                String line = importer.readLine();
                if (!importer.verifyLine(line)) {
                    System.out.println("Invalid Line: " + line);
                    withErrors++;
                    error.exportError(line);
                } else {
                    String[] args = line.split(importer.separator());

                    String idOrder = args[0].trim();
                    Calendar emissionDate = Calendars.of(Integer.parseInt(args[1].trim().substring(0, 4)), Integer.parseInt(args[1].trim().substring(4, 6)), Integer.parseInt(args[1].trim().substring(6)));
                    Calendar previsionDate = Calendars.of(Integer.parseInt(args[2].trim().substring(0, 4)), Integer.parseInt(args[2].trim().substring(4, 6)), Integer.parseInt(args[2].trim().substring(6)));

                    if (productRepository.ofIdentity(AlfanumericCode.valueOf(args[3].trim())).isPresent()) {
                        p = productRepository.ofIdentity(AlfanumericCode.valueOf(args[3].trim())).get();
                    }

                    int quantidade = Integer.parseInt(args[4].trim());
                    String unit = args[5].trim();

                    if (args[6].trim().split(",").length > 1) {
                        requests = new ArrayList<>(Arrays.asList(args[6].trim().split(",")));
                    } else {
                        requests.add(args[6].trim());
                    }

                    try {
                        ProductionOrder newProductionOrder = new ProductionOrder(idOrder, emissionDate, previsionDate, p, quantidade, unit, requests);

                        if (productionOrderRepository.ofIdentity(newProductionOrder.identity()).isPresent()) {
                            orders.add(newProductionOrder);
                        } else {
                            imported++;
                            productionOrderRepository.save(newProductionOrder);
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                        withErrors++;
                        error.exportError(line);
                    }

                }
            }

            importer.end();
            error.closeExporter();
            return orders;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    public int getNumberImported() {
        return imported;
    }

    public int getNumberWithErrors() {
        return withErrors;
    }

}
