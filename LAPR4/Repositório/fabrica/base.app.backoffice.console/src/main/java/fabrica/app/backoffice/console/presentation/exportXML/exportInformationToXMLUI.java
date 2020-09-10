package fabrica.app.backoffice.console.presentation.exportXML;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import fabrica.exportToXML.ConceptsToExport;
import fabrica.exportToXML.exporterXMLController;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.production.domain.Extensions;
import fabrica.production.domain.Product;
import fabrica.production.domain.RawMaterial;
import fabrica.production.domain.RawMaterialCategory;
import fabrica.productordersmanagement.domain.ProductionOrder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

public class exportInformationToXMLUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        
        exporterXMLController controller = new exporterXMLController();
        List<Class> listExporters = new LinkedList<>();
        List<ConceptsToExport> list;
        SelectWidget<ConceptsToExport> selector;
        list = new ArrayList<>(Arrays.asList(ConceptsToExport.values()));

        Calendar beginDate = null;
        Calendar finishDate = null;
        Calendar instalationDate = null;
        String nameFile;
        do{
            nameFile = Console.readNonEmptyLine("Name of the xml file. (Without Extension - Name Only): ", "Name of the file can´t be empty");
        }while (nameFile.split("\\.").length>1);

        do{
            selector = new SelectWidget<>("Objects to export", list);
            selector.show();
            if (selector.selectedElement()!=null){
                switch (selector.selectedElement()) {
                    case RAW_MATERIAL:
                        listExporters.add(RawMaterial.class);
                        list.remove(ConceptsToExport.RAW_MATERIAL);
                        break;
                    case RAW_MATERIAL_CATEGORY:
                        listExporters.add(RawMaterialCategory.class);
                        list.remove(ConceptsToExport.RAW_MATERIAL_CATEGORY);
                        break;
                    case PRODUCT:
                        listExporters.add(Product.class);

                        list.remove(ConceptsToExport.PRODUCT);
                        break;

                    case PRODUCTION_LINE:
                        listExporters.add(ProductionLine.class);
                        list.remove(ConceptsToExport.PRODUCTION_LINE);
                        instalationDate = Console.readCalendar("Machine installation date: (DD-MM-YYYY)");

                        break;

                    case PRODUCTION_ORDERS:
                        listExporters.add(ProductionOrder.class);
                        list.remove(ConceptsToExport.PRODUCTION_ORDERS);
                        do{
                            beginDate = Console.readCalendar("Start date: (DD-MM-YYYY)");
                            finishDate = Console.readCalendar("End date: (DD-MM-YYYY");
                        }while (finishDate.before(beginDate));

                        break;
                    case DEPOSIT:
                        listExporters.add(Deposit.class);
                        list.remove(ConceptsToExport.DEPOSIT);
                        break;
                }
            }

        }while(selector.selectedOption()!=0);


        try {
            controller.export(listExporters, nameFile, beginDate, finishDate, instalationDate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        Extensions extension = null;


        SelectWidget<Extensions> extensions = new SelectWidget<>("--- Possible Transformation Extensions ---", Arrays.asList(Extensions.values()));
        extensions.show();
        extension = extensions.selectedElement();


        try {
            if (extension != null) {
                controller.transform(nameFile, extension);
            }
        }catch (final IntegrityViolationException e){
            System.out.println("Erro na exportação");
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public String headline() {
        return "--- Export Information To XML ---";
    }
}
