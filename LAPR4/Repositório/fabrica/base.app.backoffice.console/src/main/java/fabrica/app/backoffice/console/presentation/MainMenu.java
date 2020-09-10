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
package fabrica.app.backoffice.console.presentation;

import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import fabrica.Application;
import fabrica.app.backoffice.console.presentation.authz.AddUserUI;
import fabrica.app.backoffice.console.presentation.authz.DeactivateUserAction;
import fabrica.app.backoffice.console.presentation.authz.ListUsersAction;
import fabrica.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import fabrica.app.backoffice.console.presentation.deposit.SpecifyNewDepositAction;
import fabrica.app.backoffice.console.presentation.errornotificationmanagement.ArchiveErrorNotificationAction;
import fabrica.app.backoffice.console.presentation.errornotificationmanagement.ListProcessorErrorNotificationsUI;
import fabrica.app.backoffice.console.presentation.exportXML.exportInformationToXMLUI;
import fabrica.app.backoffice.console.presentation.failuremanagement.QueryArchivedErrorsUI;
import fabrica.app.backoffice.console.presentation.machine.AddNewMachineUI;
import fabrica.app.backoffice.console.presentation.machine.SpecifyMachineConfigurationFileUI;
import fabrica.app.backoffice.console.presentation.product.*;
import fabrica.app.backoffice.console.presentation.productionLine.SpecifyNewProductionLineAction;
import fabrica.app.backoffice.console.presentation.productionorder.ImportProductionOrdersUI;
import fabrica.app.backoffice.console.presentation.productionorder.ListProductionOrdersPerStateAction;
import fabrica.app.backoffice.console.presentation.productionorder.addNewProductionOrderUI;
import fabrica.app.backoffice.console.presentation.rawMaterial.AddRawMaterialCategoryUI;
import fabrica.app.backoffice.console.presentation.rawMaterial.AddRawMaterialUI;
import fabrica.app.backoffice.console.presentation.scm.messagesmanagement.SendConfigToTheMachineUI;
import fabrica.app.common.console.presentation.authz.MyUserMenu;
import fabrica.usermanagement.domain.BaseRoles;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    // PRODUCTION MANAGER
    private static final int ADD_PRODUCT_OPTION = 1;
    private static final int IMPORT_PRODUCT_OPTION = 2;
    private static final int ADD_RAW_MATERIAL_OPTION = 3;
    private static final int ADD_RAW_MATERIAL_CATEGORY_OPTION = 4;
    private static final int SPECIFY_PRODUCTION_SHEET_OPTION = 5;
    private static final int SEARCH_PRODUCTS_WITHOUT_PRODUCTION_SHEET_OPTION = 6;
    private static final int ADD_NEW_PRODUCTION_ORDER_OPTION = 7;
    private static final int IMPORT_PRODUCTION_ORDERS_OPTION = 8;
    private static final int CONSULT_PRODUCTION_ORDER_FROM_REQUEST = 9;
    private static final int LIST_PRODUCTION_ORDERS = 10;

    // FACTORY FLOOR MANAGER
    private static final int ADD_NEW_MACHINE = 1;
    private static final int SPECIFY_PRODUCTION_LINE = 2;
    private static final int SPECIFY_NEW_DEPOSIT = 3;
    private static final int EXPORT_TO_XML = 4;
    private static final int ADD_CONFIG_FILE = 5;
    private static final int CONSULT_ERRORS_ARCHIVE = 6;
    private static final int ARCHIVE_ERROR_MESSAGE = 7;
    private static final int SEND_CONFIG_FILE_TO_MACHINE = 9;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 4;
    private static final int PRODUCTION_MANAGER_OPTION = 5;
    private static final int FACTORY_FLOOR_MANAGER_OPTION = 6;
    private static final int CONSULT_UNTREATED_MESSAGES = 8;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.PRODUCTION_MANAGER)) {
            final Menu settingsProductionManager = buildProductionManagerMenu();
            mainMenu.addSubMenu(2, settingsProductionManager);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.FACTORY_FLOOR_MANAGER)) {
            final Menu settingsFabricationManager = buildFactoryFloorManagerMenu();
            mainMenu.addSubMenu(2, settingsFabricationManager);

        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
            final Menu settingsProductionManager = buildProductionManagerMenu();
            mainMenu.addSubMenu(PRODUCTION_MANAGER_OPTION, settingsProductionManager);
            final Menu settingsFactoryFloorManager = buildFactoryFloorManagerMenu();
            mainMenu.addSubMenu(FACTORY_FLOOR_MANAGER_OPTION, settingsFactoryFloorManager);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Exit"));

        return mainMenu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
                new ShowMessageAction("Not implemented yet"));
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildProductionManagerMenu() {
        final Menu menu = new Menu("Production Manager >");
        menu.addItem(ADD_PRODUCT_OPTION, "Add Product", new AddNewProductUI()::show);
        menu.addItem(IMPORT_PRODUCT_OPTION, "Import Products", new ImportProductsUI()::show);
        menu.addItem(ADD_RAW_MATERIAL_OPTION, "Add Raw Material", new AddRawMaterialUI()::show);
        menu.addItem(ADD_RAW_MATERIAL_CATEGORY_OPTION, "Add Raw Material Category", new AddRawMaterialCategoryUI()::show);
        menu.addItem(SPECIFY_PRODUCTION_SHEET_OPTION, "Specify Production Sheet", new SpecifyProductionSheetUI()::show);
        menu.addItem(SEARCH_PRODUCTS_WITHOUT_PRODUCTION_SHEET_OPTION, "Search Products Without Production Sheet", new SearchProductsWithoutProductionSheetUI()::show);
        menu.addItem(ADD_NEW_PRODUCTION_ORDER_OPTION, "Add new production order", new addNewProductionOrderUI()::show);
        menu.addItem(IMPORT_PRODUCTION_ORDERS_OPTION, "Import production orders", new ImportProductionOrdersUI()::show);
        menu.addItem(CONSULT_PRODUCTION_ORDER_FROM_REQUEST, "Consult Production Orders From Request", new ConsultProductionOrdersFromRequestUI()::show);
        menu.addItem(LIST_PRODUCTION_ORDERS, "List Production Orders", new ListProductionOrdersPerStateAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildFactoryFloorManagerMenu() {
        final Menu menu = new Menu("Factory Floor Manager >");
        menu.addItem(ADD_NEW_MACHINE, "Add Machine", new AddNewMachineUI()::show);
        menu.addItem(SPECIFY_PRODUCTION_LINE, "Create new production line", new SpecifyNewProductionLineAction());
        menu.addItem(SPECIFY_NEW_DEPOSIT, "Create new deposit", new SpecifyNewDepositAction());
        menu.addItem(EXPORT_TO_XML, "Export to xml", new exportInformationToXMLUI()::show);
        menu.addItem(ADD_CONFIG_FILE, "Specify a machine's configuration file", new SpecifyMachineConfigurationFileUI()::show);
        menu.addItem(CONSULT_ERRORS_ARCHIVE, "Consult Archive Errors", new QueryArchivedErrorsUI()::show);
        menu.addItem(ARCHIVE_ERROR_MESSAGE,"Archive error message",new ArchiveErrorNotificationAction());
        menu.addItem(CONSULT_UNTREATED_MESSAGES, "Consult Untreated Errors", new ListProcessorErrorNotificationsUI()::show);
        menu.addItem(SEND_CONFIG_FILE_TO_MACHINE, "Send config file to the machine", new SendConfigToTheMachineUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

}
