package org.example.controller;

import org.example.model.App;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utilities.Validation;
import org.intellij.lang.annotations.Language;

public class ProfileMenuController extends Controller {
    public static boolean isProgramWaitingForUsername = false;
    private static String userUsername = null;

    public static Response handleChangeUsername(Request request) {
        String username = request.body.get("username");
        if (Validation.validateUsername(username)) {
            return new Response(false, "Username is invalid!");
        }
        User user = App.getCurrentUser();
        if (user.getUsername().equals(username)) {
            return new Response(false, "Please enter a new username");
        }
        while (UserRepository.existsByUsername(username)) {
            username = username + (int) (Math.random() * 69420);
            isProgramWaitingForUsername = true;
        }
        if (isProgramWaitingForUsername) {
            String message = "Username was exist! your new username is: " + username + "\n" +
                    "Type 1 or 2\n" +
                    "Continue [1]\nBack [2]";
            userUsername = username;
            return new Response(true, message);
        }
        user.setUsername(username);
        UserRepository.updateUser(user);
        return new Response(true, "Username successfully change to " + username + "!");
    }

    public static Response handleWaitingForUsername(Request request) {
        @Language("Regexp")
        String regex = "[1-2]";
        String number = request.command;
        if (!number.matches(regex)) {
            return new Response(false, "Invalid operation!\nContinue [1]\nBack [2]");
        }
        int num = Integer.parseInt(number);
        isProgramWaitingForUsername = false;
        if (num == 1) {
            User user = App.getCurrentUser();
            user.setUsername(userUsername);
            UserRepository.updateUser(user);
            userUsername = null;
            return new Response(true, "Username successfully change to " + user.getUsername() + "!");
        } else {
            userUsername = null;
            return new Response(true, "Enter your command again.");
        }
    }

    public static Response handleChangeNickname(Request request) {
        String nickname = request.body.get("nickname");
        User user = App.getCurrentUser();
        if (user.getNickname().equals(nickname)) {
            return new Response(false, "Please enter a new nickname");
        }
        user.setNickname(nickname);
        UserRepository.updateUser(user);
        return new Response(true, "Nickname successfully change to " + nickname + "!");
    }

    public static Response handleChangeEmail(Request request) {
        String email = request.body.get("email");
        if (Validation.validateEmail(email)) {
            return new Response(false, "Email is invalid");
        }
        User user = App.getCurrentUser();
        if (user.getEmail().equals(email)) {
            return new Response(false, "Please enter a new email");
        }
        user.setEmail(email);
        UserRepository.updateUser(user);
        return new Response(true, "Email successfully change to " + email + "!");
    }

    public static Response handleChangePassword(Request request) {
        String newPassword = request.body.get("newPassword");
        String oldPassword = request.body.get("oldPassword");
        if (Validation.validatePasswordFormat(newPassword)) {
            return new Response(false, "New password format is invalid");
        }
        if (!Validation.validatePasswordSecurity(newPassword).equals("Success")) {
            return new Response(false, "New password isn't secure! " +
                    Validation.validatePasswordSecurity(newPassword));
        }
        User user = App.getCurrentUser();
        if (!user.getPasswordHash().equals(Validation.hashPassword(oldPassword))) {
            return new Response(false, "Old password is wrong!");
        }
        if (newPassword.equals(oldPassword)) {
            return new Response(false, "New password is the same as the old password!");
        }
        user.setPasswordHash(Validation.hashPassword(newPassword));
        UserRepository.updateUser(user);
        return new Response(true, "Password successfully change to " + newPassword + "!");
    }

    public static Response handleUserInfo() {
        User user = App.getCurrentUser();
        String username = user.getUsername();
        String nickname = user.getNickname();
        String highScore = String.valueOf(user.getHighScore());
        String gamePlayed = String.valueOf(user.getGamePlayed());
        return new Response(true,
                "Username: " + username + "\n" +
                "Nickname: " + nickname + "\n" +
                "High score in one game: " + highScore + "\n" +
                "Total games played: " + gamePlayed);
    }
}