package fabrica.scm.messagesmanagement.application;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.RawMaterial;
import fabrica.production.repositories.ProductRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.domain.Message;
import fabrica.scm.messagesmanagement.exceptions.*;
import fabrica.scm.messagesmanagement.factory.MessageFactory;

public class MessageParser {

    private final MachineRepository machineRepository = PersistenceContext.repositories().machines();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final DepositRepository depositRepository = PersistenceContext.repositories().deposit();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionsOrders();
    private final MessageFactory factory = new MessageFactory();

    public Message createMessage(String[] linha) {
        if (linha.length < 3) {
            throw new InvalidMessageParameters();
        }

        String machineID = linha[0].trim();
        String codeMsg = linha[1].trim();
        String sendDate = linha[2].trim();
        int quantity;
        Product product;
        RawMaterial rawMaterial;
        Deposit deposit;
        ProductionOrder productionOrder;

        Machine machine = identifyMachine(machineID);

        if (machine == null) {
            throw new InvalidMachineException("The Inserted Machine Code Doesn't Exist!");
        }

        switch (codeMsg) {
            case "C0":
                rawMaterial = identifyRawMaterial(linha[3].trim());
                quantity = Integer.parseInt(linha[4].trim());

                if (rawMaterial == null) {

                    product = identifyProduct(linha[3].trim());
                    if (product == null) {
                        throw new InvalidProductRawMaterialException("The code entered does not represent a Product or a Raw Material!");
                    } else {

                        if (linha.length == 5) {
                            return factory.buildConsumptionMessage(sendDate, machine, product, quantity);
                        } else if (linha.length == 6) {

                            deposit = identifyDeposit(linha[5].trim());

                            if (deposit == null) {
                                throw new InvalidDepositException("The Inserted Deposit Code Doesn't Exist!");
                            }

                            return factory.buildConsumptionMessage(sendDate, machine, product, quantity, deposit);
                        }
                        throw new InvalidMessageParameters();
                    }

                } else {

                    if (linha.length == 5) {
                        return factory.buildConsumptionMessage(sendDate, machine, rawMaterial, quantity);
                    } else if (linha.length == 6) {

                        deposit = identifyDeposit(linha[5].trim());

                        if (deposit == null) {
                            throw new InvalidDepositException("The Inserted Deposit Code Doesn't Exist!");
                        }

                        return factory.buildConsumptionMessage(sendDate, machine, rawMaterial, quantity, deposit);
                    }

                    throw new InvalidMessageParameters();
                }
            case "C9":
                product = identifyProduct(linha[3].trim());
                if (product == null) {
                    throw new InvalidProductException("The Inserted Product Code Doesn't Exist!");
                }

                quantity = Integer.parseInt(linha[4].trim());

                deposit = identifyDeposit(linha[5].trim());

                if (deposit == null) {
                    throw new InvalidDepositException("The Inserted Deposit Code Doesn't Exist!");
                }
                if (linha.length == 6) {
                    return factory.buildProductionDeliveryMessage(sendDate, machine, product, quantity, deposit);
                } else if (linha.length == 7) {
                    return factory.buildProductionDeliveryMessage(sendDate, machine, product, quantity, deposit, linha[6].trim());
                }

                throw new InvalidMessageParameters();
            case "P1":
                product = identifyProduct(linha[3].trim());
                if (product == null) {
                    throw new InvalidProductException("The Inserted Product Code Doesn't Exist!");
                }

                quantity = Integer.parseInt(linha[4].trim());

                if (linha.length == 5) {
                    return factory.buildProductionMessage(sendDate, machine, product, quantity);
                } else if (linha.length == 6) {
                    return factory.buildProductionMessage(sendDate, machine, product, quantity, linha[5].trim());
                }
                throw new InvalidMessageParameters();
            case "P2":
                if (linha.length < 6) {
                    throw new InvalidMessageParameters();
                }

                rawMaterial = identifyRawMaterial(linha[3].trim());
                quantity = Integer.parseInt(linha[4].trim());

                deposit = identifyDeposit(linha[5].trim());

                if (deposit == null) {
                    throw new InvalidDepositException("The Inserted Deposit Code Doesn't Exist!");
                }

                if (rawMaterial == null) {
                    product = identifyProduct(linha[3].trim());
                    if (product == null) {
                        throw new InvalidProductRawMaterialException("The code entered does not represent a Product or a Raw Material!");
                    } else {
                        return factory.buildChargebackMessage(sendDate, machine, product, quantity, deposit);
                    }

                } else {
                    return factory.buildChargebackMessage(sendDate, machine, rawMaterial, quantity, deposit);
                }
            case "S0":
                if (linha.length == 3) {
                    return factory.buidActivityStartMessage(sendDate, machine);
                } else if (linha.length == 4) {

                    productionOrder = identifyProductionOrder(linha[3].trim());

                    if (productionOrder == null) {
                        throw new InvalidProductionOrderException("The Inserted Production Order Code Doesn't Exist!");
                    }

                    return factory.buidActivityStartMessage(sendDate, machine, productionOrder);
                }
                throw new InvalidMessageParameters();
            case "S1":
                return factory.buildActivityResumptionMessage(sendDate, machine);
            case "S8":
                if (linha.length < 4) {
                    throw new InvalidMessageParameters();
                }
                String erro = linha[3].trim();
                return factory.buildForcedStopMessage(sendDate, machine, erro);
            case "S9":
                if (linha.length == 3) {
                    return factory.buildActivityEndMessage(sendDate, machine);
                } else if (linha.length == 4) {

                    productionOrder = identifyProductionOrder(linha[3].trim());

                    if (productionOrder == null) {
                        throw new InvalidProductionOrderException("The Inserted Production Order Code Doesn't Exist!");
                    }

                    return factory.buildActivityEndMessage(sendDate, machine, productionOrder);
                }
                throw new InvalidMessageParameters();
        }

        return null;
    }

    private Machine identifyMachine(String idMachine) {
        if (machineRepository.ofIdentity(AlfanumericCode.valueOf(idMachine)).isPresent()) {
            return machineRepository.ofIdentity(AlfanumericCode.valueOf(idMachine)).get();
        }
        return null;
    }

    private Product identifyProduct(String fabricationCode) {
        if (productRepository.ofIdentity(AlfanumericCode.valueOf(fabricationCode)).isPresent()) {
            return productRepository.ofIdentity(AlfanumericCode.valueOf(fabricationCode)).get();
        }
        return null;
    }

    private RawMaterial identifyRawMaterial(String rawMaterialCode) {
        if (rawMaterialRepository.ofIdentity(AlfanumericCode.valueOf(rawMaterialCode)).isPresent()) {
            return rawMaterialRepository.ofIdentity(AlfanumericCode.valueOf(rawMaterialCode)).get();
        }
        return null;
    }

    private Deposit identifyDeposit(String depositCode) {
        if (depositRepository.ofIdentity(Designation.valueOf(depositCode)).isPresent()) {
            return depositRepository.ofIdentity(Designation.valueOf(depositCode)).get();
        }
        return null;
    }

    private ProductionOrder identifyProductionOrder(String productionOrderID) {
        if (productionOrderRepository.ofIdentity(AlfanumericCode.valueOf(productionOrderID)).isPresent()) {
            return productionOrderRepository.ofIdentity(AlfanumericCode.valueOf(productionOrderID)).get();
        }
        return null;
    }

}