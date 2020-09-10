package fabrica.production.services;

import fabrica.production.domain.RawMaterial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class exportDataSheet {

    public String readBytes(RawMaterial rawMaterial) throws IOException {
        byte[] bytes = rawMaterial.obtainDataSheet();
        File f = new File ("Files/" + rawMaterial.identity().toString() + ".pdf");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream (f);
            fos.write (bytes);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return f.getAbsolutePath();
    }

}
