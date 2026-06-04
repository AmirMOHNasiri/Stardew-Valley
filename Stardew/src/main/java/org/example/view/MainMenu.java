package org.example.view;

import org.example.controller.MainMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.commands.MainMenuCommands;

public class MainMenu implements Menu {
    @Override
    public void handleMenu(String input) {
        Response response;
        if (MainMenuCommands.USER_LOGOUT.matches(input)) {
            response = getUserLogoutResponse();
        } else if (MainMenuCommands.SHOW_MENU.matches(input)) {
            response = getShowMenuResponse();
        } else if (MainMenuCommands.ENTER_MENU.matches(input)) {
            response = getEnterMenuResponse(input);
        } else if (MainMenuCommands.EXIT_MENU.matches(input)) {
            response = getExitMenuResponse();
        } else {
            response = getInvalidCommand();
        }
        printResponse(response);
    }

    private static Response getUserLogoutResponse() {
        return MainMenuController.handleUserLogout();
    }

    private static Response getShowMenuResponse() {
        return MainMenuController.handleShowMenu();
    }

    private static Response getEnterMenuResponse(String input) {
        Request request = new Request(input);
        return MainMenuController.handleEnterMenu(request);
    }

    private static Response getExitMenuResponse() {
        return MainMenuController.handleExitMenu();
    }
}
