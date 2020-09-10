package fabrica.exportToXML;

import fabrica.factoryfloor.depositmanagement.services.ListDepositService;
import fabrica.factoryfloor.machinemanagement.application.ListMachinesService;
import fabrica.factoryfloor.productionlinemanagement.services.ListProductionLineService;
import fabrica.production.domain.Extensions;
import fabrica.production.services.ListProductService;
import fabrica.production.services.ListProductionOrdersService;
import fabrica.production.services.ListRawMaterialCategoriesService;
import fabrica.production.services.ListRawMaterialService;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class exporterXMLController {

    //private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ExportToXMLService service = new ExportToXMLService();
    private final ListRawMaterialService rmService = new ListRawMaterialService();
    private final ListRawMaterialCategoriesService rmcService = new ListRawMaterialCategoriesService();
    private final ListProductService pService = new ListProductService();
    private final ListMachinesService mService = new ListMachinesService();
    private final ListProductionLineService plService = new ListProductionLineService();
    private final ListProductionOrdersService poService = new ListProductionOrdersService();
    private final ListDepositService depService = new ListDepositService();

    private Document doc;

    public void export(List<Class> classList, String nameFile, Calendar beginDate, Calendar endDate,Calendar instalationDate) throws IOException, ParserConfigurationException, TransformerException {
        //authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.FACTORY_FLOOR_MANAGER, BaseRoles.PRODUCTION_MANAGER, BaseRoles.PRODUCTION_MANAGER);
        String area ="";
        doc = service.init();
        List<CanBeExportedToXML> list;
        for (Class selectedClass : classList) {
            list = new LinkedList<>();
            switch(selectedClass.getName()) {
                case "fabrica.production.domain.RawMaterial":
                    if (rmService.allRawMaterials() != null){
                        list.addAll(rmService.allRawMaterials());
                        area = "producao";
                    }
                    break;
                case "fabrica.production.domain.RawMaterialCategory":
                    if (rmcService.allCategories() != null){
                        list.addAll(rmcService.allCategories());
                        area = "producao";
                    }
                    break;
                case "fabrica.production.domain.Product":
                    if (pService.allProducts()!=null){
                        list.addAll(pService.allProducts());
                        area = "producao";
                    }
                    break;
                case "fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine":

                    if (plService.allProductionLinesByInstalationDate(instalationDate)!=null){
                        list.addAll(plService.allProductionLines());
                        area = "chaoDeFabrica";
                    }
                    break;
                case "fabrica.productordersmanagement.domain.ProductionOrder":
                    if (poService.allProductionOrdersBetweenDates(beginDate,endDate)!=null){
                        list.addAll(poService.allProductionOrders());
                        area = "producao";
                    }
                    break;
                case "fabrica.factoryfloor.depositmanagement.domain.Deposit":
                    if (depService.allDeposits()!=null){
                        list.addAll(depService.allDeposits());
                        area = "chaoDeFabrica";
                    }
                    break;
            }
            service.exportInformation(list, doc, selectedClass, area);
        }
        service.finish(doc, nameFile);


    }

    public void transform(String nameFile, Extensions extension){
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xsltDocument = new StreamSource("../LPROG/Complete/transformationComplete"+extension.toString()+".xslt");
            Source xmlDocument = new StreamSource("Files/"+nameFile+".xml");
            OutputStream file = new FileOutputStream("Files/"+nameFile+"."+extension.toString().toLowerCase());
            Transformer transformer = factory.newTransformer(xsltDocument);
            transformer.transform(xmlDocument, new StreamResult(file));
            File file1 = new File("Files/"+nameFile+"."+extension.toString().toLowerCase());
            Desktop.getDesktop().browse(file1.toURI());
        } catch (FileNotFoundException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
