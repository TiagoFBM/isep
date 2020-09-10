/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.infrastructure.bootstrapers.factoryfloor;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.application.SpecifyNewDepositController;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.infrastructure.bootstrapers.TestDataConstants;
import fabrica.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Tiago Sousa
 */
public class DepositBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(DepositBootstrapper.class);

    private final DepositRepository depositRepository = PersistenceContext.repositories().deposit();

    private Deposit getDepositType(final String name) {
        return depositRepository.ofIdentity(Designation.valueOf(name)).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {

        register(TestDataConstants.DEPOSIT_1, "Deposit 1");
        register(TestDataConstants.DEPOSIT_2, "Deposit 2");
        register(TestDataConstants.DEPOSIT_3, "Deposit 3");
        register(TestDataConstants.DEPOSIT_4, "Deposit 4");
        register(TestDataConstants.DEPOSIT_5, "Deposit 5");

        return true;
    }

    /**
     *
     */
    private void register(final String code, final String desc) {
        final SpecifyNewDepositController controller = new SpecifyNewDepositController();
        try {
            controller.specifyNewDeposit(code, desc);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", desc);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
