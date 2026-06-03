package org.example.view;

import org.example.model.App;
import org.example.model.enums.MenuTypes;

import java.util.Scanner;

public class AppView {
    public final static Scanner scanner = new Scanner(System.in);

    public void run() {
        while (App.getCurrentMenuType() != MenuTypes.ExitMenu) {
            String input = scanner.nextLine().trim();
            App.getCurrentMenuType().getMenu().handleMenu(input);
        }
    }
}