package org.example.view;

import org.example.model.App;
import org.example.model.User;
import org.example.model.MenuTypes;
import org.example.repository.UserRepository;
import org.example.utilities.Environment;
import org.example.utilities.HibernateUtil;

import java.util.Scanner;

public class AppView {
    public final static Scanner scanner = new Scanner(System.in);

    static {
        Environment.load();
        HibernateUtil.getSessionFactory();
        System.out.println("Database Connected!");

        User stayLoggedInUser = UserRepository.getStayLoggedInUser();
        if (stayLoggedInUser != null) {
            App.setCurrentUser(stayLoggedInUser);
            App.setCurrentMenuType(MenuTypes.MainMenu);
        }

        Runtime.getRuntime().addShutdownHook(
                new Thread(HibernateUtil::shutdown));
    }

    public void run() {
        while (App.getCurrentMenuType() != MenuTypes.ExitMenu) {
            String input = scanner.nextLine().trim();
            App.getCurrentMenuType().getMenu().handleMenu(input);
        }
    }
}