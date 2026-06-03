package org.example.controller;

import org.example.model.IO.Request;
import org.example.model.IO.Response;

public class SignInMenuController extends Controller {

    public static Response handleRegister(Request request) {
        String username = request.body.get("username");
        String password = request.body.get("password");
        String passwordConfirm = request.body.get("passwordConfirm");
        String nickname = request.body.get("nickname");
        String email = request.body.get("email");
        String gender = request.body.get("gender");

        return new Response(true, "Login Successful. Going to Main Menu!");
    }
}