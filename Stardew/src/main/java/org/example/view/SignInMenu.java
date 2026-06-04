package org.example.view;

import org.example.controller.SignInMenuController;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.commands.SignInMenuCommands;

public class SignInMenu implements Menu {
    @Override
    public void handleMenu(String input) {
        Response response;
        if (SignInMenuController.isProgramWaitingForPasswordConfirm) {
            response = getPasswordConfirmResponse(input);
        } else if (SignInMenuController.getUserWaitingForUsername() != null) {
            response = getRandomUsernameResponse(input);
        } else if (SignInMenuController.isProgramWaitingForPassword) {
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
                response = getAnswerResponse(input);
            } else {
                response = getInvalidCommand();
            }
        } else if (SignInMenuController.getUserForgetPassword() != null) {
            response = getChangePasswordResponse(input);
        } else if (SignInMenuCommands.REGISTER.matches(input)) {
            response = getRegisterResponse(input);
        } else if (SignInMenuCommands.LOGIN.matches(input)) {
            response = getLoginResponse(input);
        } else if (SignInMenuCommands.FORGET.matches(input)) {
            response = getForgetPasswordResponse(input);
        } else if (SignInMenuCommands.SHOW_MENU.matches(input)) {
            response = getShowMenuResponse();
        } else if (SignInMenuCommands.ENTER_MENU.matches(input)) {
            response = getEnterMenuResponse(input);
        } else if (SignInMenuCommands.EXIT_MENU.matches(input)) {
            response = getExitMenuEnter();
        } else {
            response = getInvalidCommand();
        }
        printResponse(response);
    }

    private static Response getPasswordConfirmResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handlePasswordConfirm(request);
    }

    private static Response getRandomUsernameResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handleRandomUsername(request);
    }

    private static Response getRandomPasswordResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handleRandomPassword(request);
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

    private static Response getAnswerResponse(String input) {
        Request request = new Request(input);
        request.body.put("answer", SignInMenuCommands.ANSWER.getGroups(input, "answer"));
        return SignInMenuController.handleAnswer(request);
    }

    private static Response getChangePasswordResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handleChangePassword(request);
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

    private static Response getShowMenuResponse() {
        return SignInMenuController.handleShowMenu();
    }

    private static Response getEnterMenuResponse(String input) {
        Request request = new Request(input);
        return SignInMenuController.handleEnterMenu(request);
    }

    private static Response getExitMenuEnter() {
        return SignInMenuController.handleExitMenu();
    }
}