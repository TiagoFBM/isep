package fabrica.exportToXML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.List;

public class ExporterXML {

    private final Class type;


    public ExporterXML(Class type) {
        this.type = type;
    }

    public Element createNode(Document doc, Element e, List<CanBeExportedToXML> objetos) throws IOException {
        String vec[] = type.getName().split("\\.");
        Element root = doc.createElement(vec[vec.length-1] + "s");
        e.appendChild(root);
        for (CanBeExportedToXML objeto : objetos) {
            root.appendChild(objeto.exportToXML(doc));
        }
        return root;
    }
}
