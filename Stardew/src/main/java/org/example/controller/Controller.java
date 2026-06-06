package org.example.controller;

import org.example.model.App;
import org.example.model.IO.Request;
import org.example.model.IO.Response;
import org.example.model.MenuTypes;

public class Controller {
    public static Response handleEnterMenu(Request request) {
        String targetMenu = request.body.get("menuName");

        if (App.getCurrentMenuType() == MenuTypes.SignInMenu) {
            return new Response(false, "Sign in first to navigate menus.");
        } else if (App.getCurrentMenuType() == MenuTypes.MainMenu) {
            if (targetMenu.compareToIgnoreCase("GameMenu") == 0) {
                App.setCurrentMenuType(MenuTypes.GameMenu);
                return new Response(true, "Going to game menu...");
            } else if (targetMenu.compareToIgnoreCase("ProfileMenu") == 0) {
                App.setCurrentMenuType(MenuTypes.ProfileMenu);
                return new Response(true, "Going to profile menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else if (App.getCurrentMenuType() == MenuTypes.ProfileMenu) {
            if (targetMenu.compareToIgnoreCase("MainMenu") == 0) {
                App.setCurrentMenuType(MenuTypes.MainMenu);
                return new Response(true, "Going to main menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else if (App.getCurrentMenuType() == MenuTypes.GameMenu) {
            if (targetMenu.compareToIgnoreCase("MainMenu") == 0) {
                App.setCurrentMenuType(MenuTypes.MainMenu);
                return new Response(true, "Going to main menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else {
            return new Response(false, "Invalid Operation.");
        }
    }

    public static Response handleExitMenu() {
        if (App.getCurrentMenuType() == MenuTypes.SignInMenu) {
            App.setCurrentMenuType(MenuTypes.ExitMenu);
            return new Response(true, "Exiting app...");
        } else if (App.getCurrentMenuType() == MenuTypes.MainMenu) {
            App.setCurrentMenuType(MenuTypes.ExitMenu);
            return new Response(true, "Exiting app...");
        } else if (App.getCurrentMenuType() == MenuTypes.ProfileMenu) {
            App.setCurrentMenuType(MenuTypes.MainMenu);
            return new Response(true, "Exiting to Main Menu...");
        } else if (App.getCurrentMenuType() == MenuTypes.GameMenu) {
            App.setCurrentMenuType(MenuTypes.MainMenu);
            return new Response(true, "Exiting to Main Menu...");
        } else {
            return new Response(false, "Invalid Operation.");
        }
    }

    public static Response handleShowMenu() {
        return new Response(true, App.getCurrentMenuType().toString());
    }
}