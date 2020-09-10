package fabrica.exportToXML;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExportToXMLService {

    private Element root;
    private Element rootElementProducao;
    private Element rootElementChaoDeFabrica;


    public Document init() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        doc.setXmlVersion("1.0");
        this.root = buildRoot(doc);
        buildAreas(doc, this.root);
        return doc;
    }

    private void buildAreas(Document doc, Element root) {
        this.rootElementProducao = doc.createElement("producao");
        root.appendChild(this.rootElementProducao);
        this.rootElementChaoDeFabrica = doc.createElement("chaoDeFabrica");
        root.appendChild(this.rootElementChaoDeFabrica);
    }

    private Element buildRoot(Document doc) {
        Element rootElement = doc.createElement("fabrica");
        rootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", "diagramaXSD.xsd");
        doc.appendChild(rootElement);
        return rootElement;
    }

    public void exportInformation(List<CanBeExportedToXML> objects, Document doc, Class type, String area) throws IOException {
        Element begin;
        ExporterXML exporterXML = new ExporterXML(type);
        if (area.equalsIgnoreCase("producao")){
            begin = this.rootElementProducao;
        }else{
            begin = this.rootElementChaoDeFabrica;
        }
        Element node = exporterXML.createNode(doc, begin, objects);
        begin.appendChild(node);
    }

    public void finish(Document doc, String nameFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // TODO: mudar o caminho do ficheiro

        StreamResult result = new StreamResult(new File("Files/" + nameFile +  ".xml"));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        System.out.println("File saved!");

    }



}
