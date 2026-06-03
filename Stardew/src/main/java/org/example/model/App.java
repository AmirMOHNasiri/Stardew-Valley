package org.example.model;

import org.example.model.enums.MenuTypes;

public class App {
    private static MenuTypes currentMenuType = MenuTypes.SignInMenu;

    public static MenuTypes getCurrentMenuType() {
        return currentMenuType;
    }

    public static void setCurrentMenuType(MenuTypes currentMenuType) {
        App.currentMenuType = currentMenuType;
    }
}