package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.*;
import fabrica.production.services.ImportFileToBytesService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ConsumptionMessageTest {
    private static final String internalCode = "DD4";
    private static final short identificationNumber = 123;
    private static final String serialNumber = "ABC1234";
    private static final String manufacturer = "Manuf123";
    private static final String model = "model321";
    private static final Calendar installationDate = Calendars.of(2012,12, 12);
    private static final String operation = "operation987";
    private final static String nameFile = "exemplo.pdf";
    private final static String pathFile = "..//Files";
    private final static String rawMaterialCode = "1000";
    private final static String descriptionRawMaterial = "Produção de camisolas";
    private static final int quantity = 10;
    private static final String depositCode="1";
    private static final String depositDescription="1st Deposit";
    private static final String fabricationCode = "12345";
    private static final String comercialCode = "54321";
    private static final String briefDescription = "briefDescription";
    private static final String fullDescription = "fullDescription";
    private static final String productCategory = "productCategory";
    private static final String unit = "TONNE";


    @Test
    public void testCreateConsumptionMessageWithRawMaterialAndDeposit() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1,rawMaterial,quantity, deposit);
    }

    @Test
    public void testCreateConsumptionMessageWithProductAndDeposit(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1,product,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndDepositAndNullSendDate() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage(null, m1,rawMaterial,quantity, deposit);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndDepositAndNullSendDate(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage(null, m1,product,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndDepositAndNullMachine() throws IOException {

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", null,rawMaterial,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndDepositAndNullMachine(){
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", null,product,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithNullRawMaterialAndDeposit(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1, (RawMaterial) null,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithNullProductAndDeposit(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1, (Product) null,quantity, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndDepositAndNegativeQuantity() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1,rawMaterial,-1, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndDepositAndNegativeQuantity(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Deposit deposit = new Deposit(depositCode, depositDescription);

        new ConsumptionMessage("20190202151045", m1,product,-1, deposit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndDepositAndNullDeposit() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        new ConsumptionMessage("20190202151045", m1,rawMaterial,quantity, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndDepositAndNullDeposit(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);

        new ConsumptionMessage("20190202151045", m1,product,quantity, null);
    }

    // sem depósito

    @Test
    public void testCreateConsumptionMessageWithRawMaterial() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        new ConsumptionMessage("20190202151045", m1,rawMaterial,quantity);
    }

    @Test
    public void testCreateConsumptionMessageWithProduct(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);

        new ConsumptionMessage("20190202151045", m1,product,quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndNullSendDate() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);


        new ConsumptionMessage(null, m1,rawMaterial,quantity);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndNullSendDate(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);

        new ConsumptionMessage(null, m1,product,quantity );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndNullMachine() throws IOException {

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);


        new ConsumptionMessage("20190202151045", null,rawMaterial,quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndNullMachine(){
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);

        new ConsumptionMessage("20190202151045", null,product,quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithNullRawMaterial(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);


        new ConsumptionMessage("20190202151045", m1, (RawMaterial) null,quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithNullProduct(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        new ConsumptionMessage("20190202151045", m1, (Product) null,quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithRawMaterialAndNegativeQuantity() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        new ConsumptionMessage("20190202151045", m1,rawMaterial,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConsumptionMessageWithProductAndNegativeQuantity(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Product product = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);

        new ConsumptionMessage("20190202151045", m1,product,-1);
    }

    @Test
    public void obtainQuantity() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        ConsumptionMessage consumptionMessage = new ConsumptionMessage("20190202151045", m1,rawMaterial,quantity, deposit);
        Assert.assertEquals(consumptionMessage.obtainQuantity().toString(), new Quantity(quantity).toString());
    }

    @Test
    public void obtainDeposit() throws IOException {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rawMaterial = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);

        Deposit deposit = new Deposit(depositCode, depositDescription);

        ConsumptionMessage consumptionMessage = new ConsumptionMessage("20190202151045", m1,rawMaterial,quantity, deposit);
        Assert.assertEquals(consumptionMessage.obtainDeposit(), deposit);
    }
}