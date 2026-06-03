package org.example.view;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.App;
import org.example.model.enums.MenuTypes;
import org.example.model.utilities.Connection;

import java.util.Scanner;

public class AppView {
    public final static Scanner scanner = new Scanner(System.in);

    static {
        Dotenv.configure()
                .directory(System.getProperty("user.dir") + "/src/main/java/org/example/config")
                .filename("env." + System.getenv("APP_MODE").toLowerCase())
                .systemProperties()
                .load();

        Connection.getDatabase();
    }

    public void run() {
        while (App.getCurrentMenuType() != MenuTypes.ExitMenu) {
            String input = scanner.nextLine().trim();
            App.getCurrentMenuType().getMenu().handleMenu(input);
        }
    }
}