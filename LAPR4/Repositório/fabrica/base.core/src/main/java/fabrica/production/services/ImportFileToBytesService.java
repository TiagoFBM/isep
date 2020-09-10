package fabrica.production.services;

import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImportFileToBytesService {
    public byte[] transformToBytes(String nameFile, String pathFile) throws IOException {
        if (!validateFiles(nameFile, pathFile)){
            throw new IllegalArgumentException("Invalid file. Put the name or the path correctly\n");
        }
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(pathFile + "/" + nameFile);
            dis = new DataInputStream(fis);
            if (fis.getChannel().size() ==0){
                throw new IllegalArgumentException("The file doesn´t have any information");
            }
            byte[] bFile = new byte[(int) fis.getChannel().size()];
            dis.read(bFile);
            return bFile;
        }
        catch (FileNotFoundException fe) {
            throw new FileNotFoundException();
        }
        catch (IOException ioe) {
            throw new IOException();
        }
        finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
            catch (Exception e) {
                System.out.println("Error while closing streams" + e);
            }
        }
    }

    // Obtain the file format
    public String obtainFileFormat(String nameFile){
        Preconditions.nonNull(nameFile);
        Preconditions.nonEmpty(nameFile);
        if (nameFile.split("\\.").length!=2){
            throw new IllegalArgumentException();
        }
        String vec[] = nameFile.split("\\.");
        return vec[1];
    }

    public boolean validateFiles(String nameFile, String pathFile){
        return !StringPredicates.isNullOrEmpty(nameFile) &&
                !StringPredicates.isNullOrWhiteSpace(nameFile) &&
                !StringPredicates.isNullOrEmpty(pathFile) &&
                !StringPredicates.isNullOrWhiteSpace(pathFile) &&
                nameFile.split("\\.").length==2
                ;
    }
}