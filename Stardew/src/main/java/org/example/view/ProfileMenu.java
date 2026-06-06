package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.commands.ProfileMenuCommands;

public class ProfileMenu implements Menu {
    @Override
    public void handleMenu(String input) {
        Response response;
        if (ProfileMenuController.isProgramWaitingForUsername) {
            response = getWaitingForUsernameResponse(input);
        } else if (ProfileMenuCommands.CHANGE_USERNAME.matches(input)) {
            response = getChangeUsernameResponse(input);
        } else if (ProfileMenuCommands.CHANGE_NICKNAME.matches(input)) {
            response = getChangeNicknameResponse(input);
        } else if (ProfileMenuCommands.CHANGE_EMAIL.matches(input)) {
            response = getChangeEmailResponse(input);
        } else if (ProfileMenuCommands.CHANGE_PASSWORD.matches(input)) {
            response = getChangePasswordResponse(input);
        } else if (ProfileMenuCommands.USER_INFO.matches(input)) {
            response = getUserInfoResponse();
        } else if (ProfileMenuCommands.SHOW_MENU.matches(input)) {
            response = getShowMenuResponse();
        } else if (ProfileMenuCommands.ENTER_MENU.matches(input)) {
            response = getEnterMenuResponse(input);
        } else if (ProfileMenuCommands.EXIT_MENU.matches(input)) {
            response = getExitMenuResponse();
        } else {
            response = getInvalidCommand();
        }
        printResponse(response);
    }

    private static Response getWaitingForUsernameResponse(String input) {
        Request request = new Request(input);
        return ProfileMenuController.handleWaitingForUsername(request);
    }

    private static Response getChangeUsernameResponse(String input) {
        Request request = new Request(input);
        request.body.put("username", ProfileMenuCommands.CHANGE_USERNAME.getGroup(input, "username"));
        return ProfileMenuController.handleChangeUsername(request);
    }

    private static Response getChangeNicknameResponse(String input) {
        Request request = new Request(input);
        request.body.put("nickname", ProfileMenuCommands.CHANGE_NICKNAME.getGroup(input, "nickname"));
        return ProfileMenuController.handleChangeNickname(request);
    }

    private static Response getChangeEmailResponse(String input) {
        Request request = new Request(input);
        request.body.put("email", ProfileMenuCommands.CHANGE_EMAIL.getGroup(input, "email"));
        return ProfileMenuController.handleChangeEmail(request);
    }

    private static Response getChangePasswordResponse(String input) {
        Request request = new Request(input);
        request.body.put("newPassword", ProfileMenuCommands.CHANGE_PASSWORD.getGroup(input, "newPassword"));
        request.body.put("oldPassword", ProfileMenuCommands.CHANGE_PASSWORD.getGroup(input, "oldPassword"));
        return ProfileMenuController.handleChangePassword(request);
    }

    private static Response getUserInfoResponse() {
        return ProfileMenuController.handleUserInfo();
    }

    private static Response getShowMenuResponse() {
        return ProfileMenuController.handleShowMenu();
    }

    private static Response getEnterMenuResponse(String input) {
        Request request = new Request(input);
        request.body.put("menuName", ProfileMenuCommands.ENTER_MENU.getGroup(input, "menuName"));
        return ProfileMenuController.handleEnterMenu(request);
    }

    private static Response getExitMenuResponse() {
        return ProfileMenuController.handleExitMenu();
    }
}