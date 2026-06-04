package org.example.model;

import org.example.model.enums.MenuTypes;

public class App {
    private static User currentUser = null;
    private static MenuTypes currentMenuType = MenuTypes.SignInMenu;

    public static MenuTypes getCurrentMenuType() {
        return currentMenuType;
    }
    public static void setCurrentMenuType(MenuTypes currentMenuType) {
        App.currentMenuType = currentMenuType;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }
}