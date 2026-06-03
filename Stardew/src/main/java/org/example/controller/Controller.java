package org.example.controller;

import org.example.model.App;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.enums.MenuTypes;

public class Controller {
    public static Response handleEnterMenu(Request request) {
        String targetMenu = request.body.get("menuName");

        if (App.getCurrentMenuType() == MenuTypes.SignInMenu) {
            return new Response(false, "Sign in first to navigate menus.");
        } else {
            return new Response(false, "Invalid Operation.");
        }
    }

    public static Response handleExitMenu(Request request) {
        if (App.getCurrentMenuType() == MenuTypes.SignInMenu) {
            App.setCurrentMenuType(MenuTypes.ExitMenu);
            return new Response(true, "Exiting app...");
        } else {
            return new Response(false, "Invalid Operation.");
        }
    }

    public static Response handleShowMenu(Request request) {
        return new Response(true, App.getCurrentMenuType().toString());
    }
}
