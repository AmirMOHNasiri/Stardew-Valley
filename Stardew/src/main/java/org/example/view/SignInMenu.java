package org.example.view;

import org.example.controller.SignInMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.commands.SignInMenuCommands;

public class SignInMenu implements Menu{
    @Override
    public void handleMenu(String input) {
        Response response = null;
        if (SignInMenuCommands.LIST_QUESTIONS.matches(input)) {
            response = getListQuestionsResponse(input);
        } else if (SignInMenuController.isProgramWaitingForQuestion) {
            if (SignInMenuCommands.PICK_QUESTION.matches(input)) {
                response = getPickQuestionResponse(input);
            } else {
                response = getInvalidCommand();
            }
        }else if (SignInMenuCommands.REGISTER.matches(input)) {
            response = getRegisterResponse(input);
        } else if (SignInMenuCommands.LOGIN.matches(input)) {

        }
        printResponse(response);
    }
    private static Response getListQuestionsResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handleListQuestions(request);
    }

    private static Response getPickQuestionResponse(String input) {
        Request request = new Request(input);
        request.body.put("questionNumber", SignInMenuCommands.PICK_QUESTION.getGroups(input, "questionNumber"));
        request.body.put("answer", SignInMenuCommands.PICK_QUESTION.getGroups(input, "answer"));
        request.body.put("answerConfirm", SignInMenuCommands.PICK_QUESTION.getGroups(input, "answerConfirm"));
        return SignInMenuController.handlePickQuestions(request);
    }

    private static Response getRegisterResponse(String input) {
        Request request = new Request(input);
        request.body.put("username", SignInMenuCommands.REGISTER.getGroups(input, "username"));
        request.body.put("password", SignInMenuCommands.REGISTER.getGroups(input, "password"));
        request.body.put("passwordConfirm", SignInMenuCommands.REGISTER.getGroups(input, "passwordConfirm"));
        request.body.put("nickname", SignInMenuCommands.REGISTER.getGroups(input, "nickname"));
        request.body.put("email", SignInMenuCommands.REGISTER.getGroups(input, "email"));
        request.body.put("gender", SignInMenuCommands.REGISTER.getGroups(input, "gender"));
        return SignInMenuController.handleRegister(request);
    }
}