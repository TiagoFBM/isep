package fabrica.production.importer;

import fabrica.errorexporter.ErrorExporter;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.*;
import fabrica.production.repositories.ProductRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductImporterService {

    private final ProductRepository repo = PersistenceContext.repositories().products();
    private int imported = 0;
    private int withErrors = 0;

    public List<Product> importer(String filename, ProductImporter importer, ErrorExporter error) throws IOException {
        List<Product> products = new ArrayList<>();
        try {
            importer.begin(filename);
            importer.readLine();
            while (importer.hasNextLine()) {
                String line = importer.readLine();
                if (!importer.verifyLine(line)) {
                    withErrors++;
                    error.exportError(line);
                } else {
                    String[] args = line.split(importer.separator());

                    try {
                        Product newProduct = new Product(args[0].trim(), args[1].trim(), args[2].trim(), args[3].trim(), args[4].trim(), args[5].trim());

                        if (repo.ofIdentity(newProduct.identity()).isPresent() || repo.findByComercialCode(newProduct.obtainComercialCode()) != null) {
                            products.add(newProduct);
                        } else {
                            imported++;
                            repo.save(newProduct);
                        }
                    } catch (IllegalArgumentException ex) {
                        withErrors++;
                        error.exportError(line);
                    }
                }
            }

            importer.end();
            error.closeExporter();
            return products;
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
