package fabrica.scm.messagesmanagement;

import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import fabrica.Application;
import fabrica.scm.messagesmanagement.presentation.MessageImporterUI;
import fabrica.scm.messagesmanagement.presentation.ReceiveMessageUI;

public class MainMenuSCM extends AbstractUI {

    private static final int RECEIVE_MESSAGES_OPTION = 1;
    private static final int IMPORT_MESSAGES_OPTION = 2;
    private static final int SEND_CONFIG_FILE_OPTION = 3;
    private static final int EXIT_OPTION = 0;

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    @Override
    protected boolean doShow() {
        Menu menu = buildMenu();

        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    private Menu buildMenu(){
        final Menu menu = new Menu("Messages Importation");
        menu.addItem(RECEIVE_MESSAGES_OPTION, "Receive Messages From Machines", new ReceiveMessageUI()::show);
        menu.addItem(IMPORT_MESSAGES_OPTION, "Import Messages From a Directory", new MessageImporterUI()::show);
        menu.addItem(EXIT_OPTION, "Return ", Actions.SUCCESS);
        return menu;
    }

    @Override
    public String headline() {
        return "--- SCM Menu ---";
    }
}
