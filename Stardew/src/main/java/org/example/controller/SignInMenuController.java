package org.example.controller;

import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utilities.Validation;

public class SignInMenuController extends Controller {
    private static User userWaitingForQuestion = null;
    private static String userPassword;
    public static boolean isProgramWaitingForQuestion = false;
    public static boolean isProgramWaitingForAnswer = false;
    private static User userForgetPassword = null;

    public static Response handleListQuestions(Request request) {

    }

    public static Response handlePickQuestions(Request request) {

    }

    public static Response handleRegister(Request request) {
        String username = request.body.get("username");
        String password = request.body.get("password");
        String passwordConfirm = request.body.get("passwordConfirm");
        String nickname = request.body.get("nickname");
        String email = request.body.get("email");
        String gender = request.body.get("gender");
        if (!Validation.validateUsername(username)) {
            return new Response(false, "Username is invalid!");
        }
        while (UserRepository.findByUsername(username) != null) {
            username = username + (int) (Math.random() * 69420);
        }
        if (password.equalsIgnoreCase(passwordConfirm) &&  password.compareToIgnoreCase("random") == 0) {
            password = Validation.createRandomPassword();
            passwordConfirm = password;
        } else {
            if (!Validation.validatePasswordFormat(password)) {
                return new Response(false, "Password Format is invalid!");
            }
            if (!Validation.validatePasswordSecurity(password).equals("Success")) {
                return new Response(false, "Password isn't secure! " +
                        Validation.validatePasswordSecurity(password));
            }
            if (!password.equals(passwordConfirm)) {
                return new Response(false, "Passwords do not match!");
            }
        }
        if (!Validation.validateEmail(email)) {
            return new Response(false, "Email is invalid!");
        }
        userWaitingForQuestion = new User(username, Validation.hashPassword(password), nickname, email, gender);
        isProgramWaitingForQuestion = true;
        /*if (System.getenv("APP_MODE") != null && System.getenv("APP_MODE").equals("TEST")) {
            userPassword = password;
        }*/
        String message = "User created! Password is: " + password + "\n" +
                "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                "You can enter 'list questions' command to see possible security questions\n";
        return new Response(true, message);
    }
}