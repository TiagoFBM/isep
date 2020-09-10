package fabrica.production.importer;

import java.io.IOException;

public interface ProductImporter {

    /**
     * Initiate the import process. The implementation should open the underlying resource (e.g., file) and create the
     * "document start"/"header" for the respective format.
     *
     * @param filename
     */
    void begin(String filename) throws IOException;

    /**
     * Indicates that there are no more elements to import. The implementation should create any "document closing"
     * element it might need and close the underlying resource.
     */
    void end();

    /**
     * Reads line from the file to import.
     * @return
     */
    String readLine();

    /**
     * Verifies if that line has all the needed parameters.
     * @param line
     * @return
     */
    boolean verifyLine(String line);

    /**
     * True if the file has more lines.
     * @return
     */
    boolean hasNextLine();

    /**
     * Separator that indicates there will be a new parameter.
     * @return
     */
    String separator();
}
