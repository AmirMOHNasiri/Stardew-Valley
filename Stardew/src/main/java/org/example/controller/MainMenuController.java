package org.example.controller;

import org.example.model.App;
import org.example.model.IO.Response;
import org.example.model.MenuTypes;
import org.example.repository.UserRepository;

public class MainMenuController extends Controller {
    public static Response handleUserLogout() {
        UserRepository.removeStayLoggedInUser();
        App.setCurrentUser(null);
        App.setCurrentMenuType(MenuTypes.SignInMenu);
        return new Response(true, "You are now logged out!");
    }
}