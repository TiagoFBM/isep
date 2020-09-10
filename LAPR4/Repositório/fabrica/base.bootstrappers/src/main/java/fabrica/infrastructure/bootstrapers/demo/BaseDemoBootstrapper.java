/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fabrica.infrastructure.bootstrapers.demo;

import eapli.framework.strings.util.Strings;
import fabrica.infrastructure.bootstrapers.ProductionOrdersBootstrap.ProductionOrderBootstrap;
import fabrica.infrastructure.bootstrapers.factoryfloor.DepositBootstrapper;
import fabrica.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Invariants;
import fabrica.infrastructure.bootstrapers.factoryfloor.MachineBootstrapper;
import fabrica.infrastructure.bootstrapers.factoryfloor.ProductionLineBootstrapper;
import fabrica.infrastructure.bootstrapers.productBootstrap.ProductBootstrap;
import fabrica.infrastructure.bootstrapers.production.RawMaterialBootstrap;
import fabrica.infrastructure.bootstrapers.production.RawMaterialCategoryBootstrap;

/**
 * Base Bootstrapping data app
 *
 * @todo avoid duplication with {@link BaseBootstrapper}
 * @author Paulo Gandra de Sousa
 */
@SuppressWarnings("squid:S106")
public class BaseDemoBootstrapper implements Action {

    private static final String POWERUSER_A1 = "poweruserA1";
    private static final String POWERUSER = "poweruser";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    @Override
    public boolean execute() {
        // declare bootstrap actions
        final Action[] actions = { new BackofficeUsersBootstrapper(),
                new ClientUserBootstrapper(),
                new DepositBootstrapper(),
                new RawMaterialCategoryBootstrap(),
                new RawMaterialBootstrap(),
                new ProductBootstrap(),
                new MachineBootstrapper(),
                new ProductionLineBootstrapper(),
                new ProductionOrderBootstrap()};

        authenticateForBootstrapping();

        // execute all bootstrapping
        boolean ret = true;
        for (final Action boot : actions) {
            System.out.println("Bootstrapping " + nameOfEntity(boot) + "...");
            ret &= boot.execute();
        }
        return ret;
    }

    /**
     * authenticate a super user to be able to register new users
     *
     */
    protected void authenticateForBootstrapping() {
        authenticationService.authenticate(POWERUSER, POWERUSER_A1);
        Invariants.ensure(authz.hasSession());
    }

    private String nameOfEntity(final Action boot) {
        final String name = boot.getClass().getSimpleName();
        return Strings.left(name, name.length() - "Bootstrapper".length());
    }
}
