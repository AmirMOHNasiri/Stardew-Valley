package org.example.controller;

import org.example.model.App;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.User;
import org.example.model.enums.MenuTypes;
import org.example.model.enums.Question;
import org.example.repository.UserRepository;
import org.example.utilities.Validation;
import org.intellij.lang.annotations.Language;

public class SignInMenuController extends Controller {
    public static boolean isProgramWaitingForPasswordConfirm = false;
    public static boolean isProgramWaitingForUsername = false;
    public static boolean isProgramWaitingForPassword = false;
    public static boolean isProgramWaitingForQuestion = false;
    public static boolean isProgramWaitingForAnswer = false;
    private static User userWaitingForPasswordConfirm = null;
    private static User userWaitingForUsername = null;
    private static String userPassword;
    private static User userWaitingForQuestion = null;
    private static User userWaitingForPassword = null;
    private static User userForgetPassword = null;

    public static User getUserForgetPassword() {
        return userForgetPassword;
    }
    public static User getUserWaitingForUsername() {
        return userWaitingForUsername;
    }

    public static Response handleListQuestions() {
        Response response = new Response();
        response.setSuccess(true);

        StringBuilder stringBuilder = new StringBuilder("List of questions:\n");
        int index = 1;
        for (Question question : Question.values()) {
            stringBuilder.append(index).append("- ").append(question.getQuestion()).append("\n");
            index++;
        }
        response.setMessage(stringBuilder.toString());
        return response;
    }

    public static Response handlePickQuestions(Request request) {
        int questionNumber = Integer.parseInt(request.body.get("questionNumber"));
        String answer = request.body.get("answer");
        String answerConfirm = request.body.get("answerConfirm");
        if (questionNumber < 1 || questionNumber > Question.values().length) {
            return new Response(false, "Invalid question number!");
        }
        if (!answer.equals(answerConfirm)) {
            return new Response(false, "Answer doesn't match!");
        }
        User user = userWaitingForQuestion;
        user.setQuestion(Question.values()[questionNumber - 1]);
        user.setAnswer(answer);
        UserRepository.save(user);
        isProgramWaitingForQuestion = false;
        userWaitingForQuestion = null;
        return new Response(true, "Question pick successfully.");
    }

    public static Response handleRegister(Request request) {
        String username = request.body.get("username");
        String password = request.body.get("password");
        String passwordConfirm = request.body.get("passwordConfirm");
        String nickname = request.body.get("nickname");
        String email = request.body.get("email");
        String gender = request.body.get("gender");
        if (Validation.validateUsername(username)) {
            return new Response(false, "Username is invalid!");
        }
        while (UserRepository.existsByUsername(username)) {
            username = username + (int) (Math.random() * 69420);
            isProgramWaitingForUsername = true;
        }
        if (Validation.validateEmail(email)) {
            return new Response(false, "Email is invalid!");
        }
        if (password.equalsIgnoreCase(passwordConfirm) &&  password.compareToIgnoreCase("random") == 0) {
            password = Validation.createRandomPassword();
            userPassword = password;
            userWaitingForPassword = new User(username, Validation.hashPassword(password), nickname, email, gender);
            isProgramWaitingForPassword = true;
            return new Response(true, "Your password is " + password + "\n" +
                    "Type 1 or 2 or 3\n" +
                    "Continue [1]\nNew random password [2]\nBack [3]");
        } else {
            if (Validation.validatePasswordFormat(password)) {
                return new Response(false, "Password format is invalid!");
            }
            if (!Validation.validatePasswordSecurity(password).equals("Success")) {
                return new Response(false, "Password isn't secure! " +
                        Validation.validatePasswordSecurity(password));
            }
            if (!password.equals(passwordConfirm)) {
                isProgramWaitingForPasswordConfirm = true;
                userPassword = password;
                userWaitingForPasswordConfirm = new User(username, Validation.hashPassword(password), nickname, email, gender);
                return new Response(true, "Passwords do not match! Please enter your password again or exit.");
            }
        }
        if (isProgramWaitingForUsername) {
            userWaitingForUsername = new User(username, password, nickname, email, gender);
            String message = "Username was exist! your new username is: " + username + "\n" +
                    "Type 1 or 2\n" +
                    "Continue [1]\nBack [2]";
            return new Response(true, message);
        }
        userWaitingForQuestion = new User(username, Validation.hashPassword(password), nickname, email, gender);
        isProgramWaitingForQuestion = true;
        String message = "User created! Password is: " + password + "\n" +
                "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                "You can enter 'list questions' command to see possible security questions\n";
        return new Response(true, message);
    }

    public static Response handlePasswordConfirm(Request request) {
        String passwordConfirm = request.command;
        if (passwordConfirm.compareToIgnoreCase("exit") == 0) {
            isProgramWaitingForPasswordConfirm = false;
            userPassword = null;
            userWaitingForPasswordConfirm = null;
            return new Response(true, "Register again.");
        }
        User user = userWaitingForPasswordConfirm;
        if (!user.getPasswordHash().equals(Validation.hashPassword(passwordConfirm))) {
            return new Response(false, "Passwords do not match! Please enter your password again or exit.");
        } else {
            isProgramWaitingForPasswordConfirm = false;
            userPassword = null;
            userWaitingForPasswordConfirm = null;
            if (isProgramWaitingForUsername) {
                user.setPasswordHash(passwordConfirm);
                userWaitingForUsername = user;
                String message = "Username was exist! your new username is: " + user.getUsername() + "\n" +
                        "Type 1 or 2\n" +
                        "Continue [1]\nBack [2]";
                return new Response(true, message);
            } else {
                userWaitingForQuestion = user;
                isProgramWaitingForQuestion = true;
                String message = "User created! Password is: " + passwordConfirm + "\n" +
                        "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                        "You can enter 'list questions' command to see possible security questions\n";
                return new Response(true, message);
            }
        }
    }

    public static Response handleRandomUsername(Request request) {
        @Language("Regexp")
        String regex = "[1-2]";
        String number = request.command;
        if (!number.matches(regex)) {
            return new Response(false, "Invalid operation!\nContinue [1]\nBack [2]");
        }
        int num = Integer.parseInt(number);
        User user = userWaitingForUsername;
        userWaitingForUsername = null;
        isProgramWaitingForUsername = false;
        if (num == 1) {
            String message = "User created! Password is: " + user.getPasswordHash() + "\n" +
                    "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                    "You can enter 'list questions' command to see possible security questions\n";
            user.setPasswordHash(Validation.hashPassword(user.getPasswordHash()));
            userWaitingForQuestion = user;
            isProgramWaitingForQuestion = true;
            return new Response(true, message);
        } else {
            return new Response(true, "Register again.");
        }
    }

    public static Response handleRandomPassword(Request request) {
        @Language("Regexp")
        String regex = "[1-3]";
        String number = request.command;
        if (!number.matches(regex)) {
            return new Response(false, "Invalid operation!\nContinue [1]\nNew random password [2]\nBack [3]");
        }
        int num = Integer.parseInt(number);
        User user = userWaitingForPassword;
        if (num == 1) {
            userWaitingForPassword = null;
            isProgramWaitingForPassword = false;
            String message;
            if (userForgetPassword == null) {
                userWaitingForQuestion = user;
                isProgramWaitingForQuestion = true;
                message = "User created! Password is: " + userPassword + "\n" +
                        "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                        "You can enter 'list questions' command to see possible security questions\n";
            } else {
                message = "Password is changed! Password is: " + userPassword;
                UserRepository.updateUser(user);
                userForgetPassword = null;
            }
            userPassword = null;
            return new Response(true, message);
        }else if (num == 2) {
            userPassword = Validation.createRandomPassword();
            user.setPasswordHash(Validation.hashPassword(userPassword));
            return new Response(true, "Your password is " + userPassword + "\n" +
                    "Type 1 or 2 or 3\n" +
                    "Continue [1]\nNew password [2]\nBack [3]");
        } else {
            userWaitingForPassword = null;
            userPassword = null;
            isProgramWaitingForPassword = false;
            if (userForgetPassword == null) {
                return new Response(true, "Register again.");
            } else {
                return new Response(true, "Enter again your new password.");
            }
        }
    }

    public static Response handleLogin(Request request) {
        String username = request.body.get("username");
        String password = request.body.get("password");
        String loginFlag = request.body.get("loginFlag");

        User user = UserRepository.findByUsername(username);
        if (user == null) {
            return new Response(false, "User not found!");
        }
        if (!Validation.hashPassword(password).equals(user.getPasswordHash())) {
            return new Response(false, "Password doesn't match!");
        }
        if (loginFlag != null) {
            UserRepository.saveStayLoggedInUser(user);
        }
        App.setCurrentUser(user);
        App.setCurrentMenuType(MenuTypes.MainMenu);
        return new Response(true, "Login Successful. Going to Main Menu!");
    }

    public static Response handleForgetPassword(Request request) {
        String username = request.body.get("username");

        User user = UserRepository.findByUsername(username);
        if (user == null) {
            return new Response(false, "User not found!");
        }
        userForgetPassword = user;
        isProgramWaitingForAnswer = true;
        return new Response(true, "User " + user.getUsername()
                + ": Answer your security question next.");
    }

    public static Response handleAnswer(Request request) {
        String answer = request.body.get("answer");

        if (!answer.equals(userForgetPassword.getAnswer())) {
            userForgetPassword = null;
            isProgramWaitingForAnswer = false;
            return new Response(false, "Answer doesn't match!");
        }
        isProgramWaitingForAnswer = false;
        return new Response(true, "Your answer is correct; Enter your new password.");
    }

    public static Response handleChangePassword(Request request) {
        User user = userForgetPassword;
        String newPassword = request.command;

        if (newPassword.compareToIgnoreCase("random") == 0) {
            newPassword = Validation.createRandomPassword();
            userPassword = newPassword;
            isProgramWaitingForPassword = true;
            userWaitingForPassword = user;
            return new Response(true, "Your password is " + userPassword + "\n" +
                    "Type 1 or 2 or 3\n" +
                    "Continue [1]\nNew random password [2]\nBack [3]");
        } else {
            if (Validation.validatePasswordFormat(newPassword)) {
                return new Response(false, "Password Format is invalid!");
            }
            if (!Validation.validatePasswordSecurity(newPassword).equals("Success")) {
                return new Response(false, "Password isn't secure! " +
                        Validation.validatePasswordSecurity(newPassword));
            }
        }
        user.setPasswordHash(Validation.hashPassword(newPassword));
        UserRepository.updateUser(user);
        userForgetPassword = null;
        return new Response(true, "Successfully password change! Password updated to: " + newPassword);
    }
}