package org.example.view;

import org.example.controller.SignInMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.commands.SignInMenuCommands;

public class SignInMenu implements Menu{
    @Override
    public void handleMenu(String input) {
        Response response = null;
        if (SignInMenuCommands.REGISTER.matches(input)) {
            response = getRegisterResponse(input);
        } else if (SignInMenuCommands.PICK_QUESTION.matches(input)) {

        }
    }

    private static Response getRegisterResponse(String input) {
        Request request = new Request(input);
        request.body.put("username", SignInMenuCommands.REGISTER.getGroups(input, "username"));
        request.body.put("password", SignInMenuCommands.REGISTER.getGroups(input, "password"));
        request.body.put("passwordConfirm", SignInMenuCommands.REGISTER.getGroups(input, "passwordConfirm"));
        request.body.put("nickname", SignInMenuCommands.REGISTER.getGroups(input, "nickname"));
        request.body.put("email", SignInMenuCommands.REGISTER.getGroups(input, "email"));
        request.body.put("gender", SignInMenuCommands.REGISTER.getGroups(input, "gender"));
        Response response = SignInMenuController.handleRegister(request);
        return response;
    }
}