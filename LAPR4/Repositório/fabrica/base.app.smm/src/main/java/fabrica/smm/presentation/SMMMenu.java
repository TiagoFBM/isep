package fabrica.smm.presentation;

import eapli.framework.actions.menu.Menu;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import fabrica.Application;

public class SMMMenu extends AbstractUI {
    @Override
    protected boolean doShow() {
        final Menu menu = buildMenu();
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
        return "=== Monitoring System Menu ===";
    }

    private Menu buildMenu() {
        Menu mainMenu = new Menu();
        mainMenu.addItem(1, "Monitor a production line", new MonitorProductionLineUI()::show);
        //mainMenu.addItem(2, "Restart a machine", null);
        mainMenu.addItem(0, "Exit", new ExitWithMessageAction("Exit"));
        return mainMenu;
    }
}
