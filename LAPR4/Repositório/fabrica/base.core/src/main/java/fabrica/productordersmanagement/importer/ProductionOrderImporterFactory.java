package fabrica.productordersmanagement.importer;

import fabrica.production.importer.FileFormat;

public class ProductionOrderImporterFactory {

    public ProductionOrderImporter build(FileFormat format) {
        switch (format) {
            case CSV:
                return new ProductionOrderImporterCSV();
        }
        throw new IllegalStateException("Unknown format");
    }


}
