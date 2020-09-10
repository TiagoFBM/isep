package fabrica.production.importer;

public class ProductImporterFactory {

    public ProductImporter build(FileFormat format) {
        switch (format) {
            case CSV:
                return new ProductImporterCSV();
        }
        throw new IllegalStateException("Unknown format");
    }

}
