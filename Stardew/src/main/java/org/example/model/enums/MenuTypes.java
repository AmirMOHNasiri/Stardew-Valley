package org.example.model.enums;

import org.example.view.*;

public enum MenuTypes {
    SignInMenu(new SignInMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu());

    private final Menu menu;

    MenuTypes(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu(){
        return menu;
    }

    @Override
    public String toString() {
        if (this == MenuTypes.SignInMenu) {
            return "Sign In Menu";
        } else if (this == MenuTypes.MainMenu) {
            return "Main Menu";
        } else if (this == MenuTypes.ProfileMenu) {
            return "Profile Menu";
        } else if (this == MenuTypes.ExitMenu) {
            return "Exit Menu";
        }else {
            return "";
        }
    }
}