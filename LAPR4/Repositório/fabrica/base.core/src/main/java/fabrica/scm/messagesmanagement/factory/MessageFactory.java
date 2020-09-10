package fabrica.scm.messagesmanagement.factory;

import fabrica.scm.messagesmanagement.domain.*;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.Product;
import fabrica.production.domain.RawMaterial;
import fabrica.productordersmanagement.domain.ProductionOrder;

public class MessageFactory {


    //Activity End Message
    public Message buildActivityEndMessage(String sendDate, Machine machine) {
        return new ActivityEndMessage(sendDate, machine);
    }

    public Message buildActivityEndMessage(String sendDate, Machine machine, ProductionOrder productionOrder) {
        return new ActivityEndMessage(sendDate, machine, productionOrder);
    }

    //Activity Resumption Message
    public Message buildActivityResumptionMessage(String sendDate, Machine machine) {
        return new ActivityResumptionMessage(sendDate, machine);
    }

    //Activity Start Message
    public Message buidActivityStartMessage(String sendDate, Machine machine) {
        return new ActivityStartMessage(sendDate, machine);
    }

    public Message buidActivityStartMessage(String sendDate, Machine machine, ProductionOrder productionOrder) {
        return new ActivityStartMessage(sendDate, machine, productionOrder);
    }

    //Chargeback Message
    public Message buildChargebackMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity, Deposit deposit) {
        return new ChargebackMessage(sendDate, machine, rawMaterial, quantity, deposit);
    }

    public Message buildChargebackMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        return new ChargebackMessage(sendDate, machine, product, quantity, deposit);
    }

    //Consumption Message
    public Message buildConsumptionMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity, Deposit deposit) {
        return new ConsumptionMessage(sendDate, machine, rawMaterial, quantity, deposit);
    }

    public Message buildConsumptionMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        return new ConsumptionMessage(sendDate, machine, product, quantity, deposit);
    }

    public Message buildConsumptionMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity) {
        return new ConsumptionMessage(sendDate, machine, rawMaterial, quantity);
    }

    public Message buildConsumptionMessage(String sendDate, Machine machine, Product product, int quantity) {
        return new ConsumptionMessage(sendDate, machine, product, quantity);
    }

    //Forced Stop Message
    public Message buildForcedStopMessage(String sendDate, Machine machine, String codErro) {
        return new ForcedStopMessage(sendDate, machine, codErro);
    }

    //Production Delivery Message
    public Message buildProductionDeliveryMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit, String lotID) {
        return new ProductionDeliveryMessage(sendDate, machine, product, quantity, deposit, lotID);
    }

    public Message buildProductionDeliveryMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        return new ProductionDeliveryMessage(sendDate, machine, product, quantity, deposit);
    }

    //Production Message
    public Message buildProductionMessage(String sendDate, Machine machine, Product product, int quantity, String lotID) {
        return new ProductionMessage(sendDate, machine, product, quantity, lotID);
    }

    public Message buildProductionMessage(String sendDate, Machine machine, Product product, int quantity) {
        return new ProductionMessage(sendDate, machine, product, quantity);
    }
}