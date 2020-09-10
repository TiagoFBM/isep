package fabrica.errorexporter;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fabrica.production.importer.FileFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorExporter {
    PrintWriter stream;

    /**
     * Creates a file to export the errors.
     *
     * @param filename - name of the error file.
     * @throws IOException
     */
    public ErrorExporter(String filename, FileFormat format) throws IOException {
        stream = new PrintWriter(new FileWriter("Files/" + filename + "." + format.toString().toLowerCase()));
        stream.print("Lines with errors - The System can't import them;");
        stream.println();
    }

    public ErrorExporter(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter("Files/" + filename));
        stream.print("Lines with errors - The System can't import them;");
        stream.println();
    }

    public ErrorExporter(File file) throws IOException {
        stream = new PrintWriter(new FileWriter(file));
        stream.print("Lines with errors - The System can't import them;");
        stream.println();
    }

    /**
     * Exports line with error to the error file.
     *
     * @param line
     */
    public void exportError(String line) {
        stream.print(line);
        stream.println();
    }

    public void closeExporter() {
        stream.close();
    }
}
