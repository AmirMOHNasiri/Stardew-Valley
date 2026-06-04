package org.example.view;

import org.example.controller.SignInMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.commands.SignInMenuCommands;

public class SignInMenu implements Menu{
    @Override
    public void handleMenu(String input) {
        Response response = null;
        if (SignInMenuController.isProgramWaitingForPassword) {
            response = getRandomPasswordResponse(input);
        } else if (SignInMenuCommands.LIST_QUESTIONS.matches(input)) {
            response = getListQuestionsResponse();
        } else if (SignInMenuController.isProgramWaitingForQuestion) {
            if (SignInMenuCommands.PICK_QUESTION.matches(input)) {
                response = getPickQuestionResponse(input);
            } else {
                response = getInvalidCommand();
            }
        } else if (SignInMenuController.isProgramWaitingForAnswer) {
            if (SignInMenuCommands.ANSWER.matches(input)) {
                response = getAnswerResponse();
            } else {
                response = getInvalidCommand();
            }
        } else if () {

        }else if (SignInMenuCommands.REGISTER.matches(input)) {
            response = getRegisterResponse(input);
        } else if (SignInMenuCommands.LOGIN.matches(input)) {
            response = getLoginResponse(input);
        } else if (SignInMenuCommands.FORGET.matches(input)) {

        }
        printResponse(response);
    }

    private static Response getRandomPasswordResponse(String input) {
        return SignInMenuController.handleRandomPassword(input);
    }

    private static Response getListQuestionsResponse() {
        return SignInMenuController.handleListQuestions();
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

    private static Response getLoginResponse(String input) {
        Request request = new Request(input);
        request.body.put("username", SignInMenuCommands.LOGIN.getGroups(input, "username"));
        request.body.put("password", SignInMenuCommands.LOGIN.getGroups(input, "password"));
        request.body.put("loginFlag", SignInMenuCommands.LOGIN.getGroups(input, "loginFlag"));
        return SignInMenuController.handleLogin(request);
    }

    private static Response getForgetPasswordResponse(String input) {
        Request request = new Request(input);
        request.body.put("username", SignInMenuCommands.FORGET.getGroups(input, "username"));
        return SignInMenuController.handleForgetPassword(request);
    }
}