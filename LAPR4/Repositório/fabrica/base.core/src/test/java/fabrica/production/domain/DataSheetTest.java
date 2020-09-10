package fabrica.production.domain;

import fabrica.production.services.ImportFileToBytesService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DataSheetTest{

    private final static String nameFile = "exemplo.pdf";
    private final static String pathFile = "..//Files";

    @Test
    public void createDataSheet() throws IOException {
        ImportFileToBytesService svc = new ImportFileToBytesService();
        new DataSheet(svc.transformToBytes(nameFile,pathFile), svc.obtainFileFormat(nameFile));
        Assert.assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDataSheetErrorNameFileNull() throws IOException {
        ImportFileToBytesService svc = new ImportFileToBytesService();
        new DataSheet(svc.transformToBytes(null,pathFile), svc.obtainFileFormat(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDataSheetErrorPathFileNull() throws IOException {
        ImportFileToBytesService svc = new ImportFileToBytesService();
        new DataSheet(svc.transformToBytes(nameFile,null), svc.obtainFileFormat(nameFile));
    }


    @Test
    public void testObtainFileFormat() throws IOException {
        ImportFileToBytesService svc = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(svc.transformToBytes(nameFile,pathFile), svc.obtainFileFormat(nameFile));
        Assert.assertEquals(ds.obtainFileFormat(), svc.obtainFileFormat(nameFile));

    }

    @Test
    public void testCompareTo() throws IOException {
        ImportFileToBytesService svc = new ImportFileToBytesService();
        DataSheet ds1 = new DataSheet(svc.transformToBytes(nameFile,pathFile), svc.obtainFileFormat(nameFile));
        Assert.assertTrue(ds1.compareTo(ds1) == 0);
    }
}