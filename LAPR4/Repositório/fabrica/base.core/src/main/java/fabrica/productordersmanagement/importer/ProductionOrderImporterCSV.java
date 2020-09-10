package fabrica.productordersmanagement.importer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ProductionOrderImporterCSV implements ProductionOrderImporter {

    private File fileImportation;
    private Scanner reader;

    @Override
    public void begin(String filename) throws IOException {
        fileImportation = new File("Files/"+filename);
        reader = new Scanner(fileImportation);
    }

    @Override
    public void end() {
        reader.close();
    }

    @Override
    public String readLine() {
        return reader.nextLine();
    }

    @Override
    public boolean verifyLine(String line) {
        String[] productInfo = line.split(separator());
        return productInfo.length == 7;
    }

    @Override
    public boolean hasNextLine() {
        return reader.hasNextLine();
    }

    @Override
    public String separator() {
        return ";";
    }

}
