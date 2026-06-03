package org.example.model.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static SessionFactory sessionFactory;

    public static SessionFactory getDatabase() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .setProperty("hibernate.connection.url", System.getProperty("DB_URL"))
                        .setProperty("hibernate.connection.username", System.getProperty("DB_USERNAME"))
                        .setProperty("hibernate.connection.password", System.getProperty("DB_PASSWORD"))
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}