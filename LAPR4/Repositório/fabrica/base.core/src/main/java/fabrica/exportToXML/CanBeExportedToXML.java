package fabrica.exportToXML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface CanBeExportedToXML {
    
    Element exportToXML(Document doc);
}
