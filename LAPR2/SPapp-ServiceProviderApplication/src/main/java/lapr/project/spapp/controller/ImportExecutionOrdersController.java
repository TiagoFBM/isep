package lapr.project.spapp.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javafx.scene.control.Alert;
import lapr.project.Utils.Utils;
import lapr.project.spapp.model.*;
import lapr.project.spapp.model.Date;
import lapr.project.spapp.records.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author InêsLopes
 */
public class ImportExecutionOrdersController {

    private Company company;
    private ExecutionOrderRegistration m_oRegistoOE;
    private List<ExecutionOrder> m_lstOE;

    public ImportExecutionOrdersController() {
        this.company = GPSDApplication.getInstance().getCompany();
        m_oRegistoOE = company.getExeOrdRegistration();
        m_lstOE = new ArrayList<>();
    }

    public ExecutionOrderRegistration getExeBOrdRegistation() {
        return this.m_oRegistoOE;
    }

    public List<ExecutionOrder> importExecutionOrders(String fname) {
        String[] ext = fname.split("\\.");
        if (ext[1].equalsIgnoreCase("CSV")) {
            try (Scanner file = new Scanner(new FileReader(fname))) {
                file.nextLine();
                while (file.hasNextLine()) {
                    String linha = file.nextLine();
                    if (!linha.isEmpty()) {
                        String[] eoLine = linha.split(Constants.FILE_SEPARATOR);
                        int num = Integer.parseInt(eoLine[0]);
                        String idNum = eoLine[1];
                        String cliName = eoLine[2];
                        double cliDist = Double.parseDouble(eoLine[3]);
                        String category = eoLine[4];
                        String serviceDescription = eoLine[5];
                        Time serviceDuration = Utils.novoTempo(eoLine[6] + ":00");
                        String tServ = eoLine[7];
                        Date iDate = Utils.novaData(eoLine[8], "/");
                        Time iTime = Utils.novoTempo(eoLine[9] + ":00");
                        String pAdress = eoLine[10];
                        String local = eoLine[11];
                        String pCode = eoLine[12];
                        DescriptionOrderService desc = new DescriptionOrderService(category, serviceDescription, tServ, serviceDuration);
                        Assignment a = new Assignment(desc, idNum, iDate, iTime);
                        PostalAdress pa = new PostalAdress(pAdress, pCode, local);
                        ExecutionOrder eo = m_oRegistoOE.novaExecutionOrder(a, num, cliName, pa, cliDist);
                        m_lstOE.add(eo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error reading file", "There was an error reading the submited file").showAndWait();
            }
        } else if (ext[1].equalsIgnoreCase("xlsx")) {
            try {
                File excelFile = new File(fname);
                FileInputStream fis = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                try {
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIt = sheet.iterator();
                    DataFormatter dt = new DataFormatter();
                    rowIt.next();
                    while (rowIt.hasNext()) {
                        Row row = rowIt.next();
                        int num = Integer.parseInt(dt.formatCellValue(row.getCell(0)));
                        String idNum = row.getCell(1).toString();
                        String cliName = row.getCell(2).toString();
                        double cliDist = Double.parseDouble(dt.formatCellValue(row.getCell(3)).replace(",","."));
                        String category = row.getCell(4).getStringCellValue();
                        String serviceDescription = row.getCell(5).getStringCellValue();
                        Time serviceDuration = Utils.novoTempo(dt.formatCellValue(row.getCell(6)) + ":00");
                        String tServ = row.getCell(7).toString();
                        Date iDate = Utils.novaData(dt.formatCellValue(row.getCell(8)), "/");
                        Time iTime = Utils.novoTempo(dt.formatCellValue(row.getCell(9)));
                        String pAdress = row.getCell(10).toString();
                        String local = row.getCell(11).toString();
                        String pCode = row.getCell(12).toString();

                        DescriptionOrderService desc = new DescriptionOrderService(category, serviceDescription, tServ, serviceDuration);
                        Assignment a = new Assignment(desc, idNum, iDate, iTime);
                        PostalAdress pa = new PostalAdress(pAdress, pCode, local);
                        ExecutionOrder eo = m_oRegistoOE.novaExecutionOrder(a, num, cliName, pa, cliDist);

                        m_lstOE.add(eo);
                    }
                } finally {
                    workbook.close();
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error reading file", "There was an error reading the submited file").showAndWait();
            }

        } else if (ext[1].equalsIgnoreCase("xml")) {
            try {
                File file = new File(fname);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);

                String spID = doc.getDocumentElement().getAttribute("id");

                NodeList executionOrderNodes = doc.getElementsByTagName("ExecutionOrder");
                for (int i = 0; i < executionOrderNodes.getLength(); i++) {
                    Node executionOrderNode =  executionOrderNodes.item(i);
                    if(executionOrderNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element executionOrderElement = (Element) executionOrderNode;
                        int num = Integer.parseInt(executionOrderElement.getAttribute("num"));
                        Element clientElement = (Element) executionOrderElement.getElementsByTagName("Client").item(0);
                        String clientName = clientElement.getAttribute("name");
                        double distanceToClient = Double.parseDouble(((Element)executionOrderElement.getElementsByTagName("DistanceToClient").item(0)).getAttribute("distance"));
                        Element postalAdress = (Element) clientElement.getElementsByTagName("PostalAdress").item(0);
                        String street = postalAdress.getElementsByTagName("Street").item(0).getTextContent();
                        String city = postalAdress.getElementsByTagName("City").item(0).getTextContent();
                        String postalCode = postalAdress.getElementsByTagName("PostalCode").item(0).getTextContent();
                        Element serviceDescriptionElement = (Element) executionOrderElement.getElementsByTagName("ServiceDescription").item(0);
                        String category = serviceDescriptionElement.getElementsByTagName("Category").item(0).getTextContent();
                        String serviceDescription = serviceDescriptionElement.getElementsByTagName("ServiceDescription").item(0).getTextContent();
                        String serviceType = serviceDescriptionElement.getElementsByTagName("ServiceType").item(0).getTextContent();
                        Time serviceDuration = Utils.novoTempo(serviceDescriptionElement.getElementsByTagName("ServiceDuration").item(0).getTextContent());

                        Date eoDate = Utils.novaData(executionOrderElement.getElementsByTagName("Date").item(0).getTextContent(), "/");
                        Time eoSchedule = Utils.novoTempo(executionOrderElement.getElementsByTagName("Schedule").item(0).getTextContent());

                        DescriptionOrderService desc = new DescriptionOrderService(category, serviceDescription, serviceType, serviceDuration);
                        Assignment a = new Assignment(desc, spID, eoDate, eoSchedule);
                        PostalAdress pa = new PostalAdress(street, postalCode, city);
                        ExecutionOrder eo = m_oRegistoOE.novaExecutionOrder(a, num, clientName, pa, distanceToClient);
                        m_lstOE.add(eo);
                    }

                }

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
                Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error reading file", "There was an error reading the submited file").showAndWait();
            }
        }
        return m_lstOE;
    }

    public void setLstProfessionalQualifications(List<ExecutionOrder> leo) {
        for (ExecutionOrder eo : leo) {
            m_oRegistoOE.addExecutionOrder(eo);
        }
    }
}

