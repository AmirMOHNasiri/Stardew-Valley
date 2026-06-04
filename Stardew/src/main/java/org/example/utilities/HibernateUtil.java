package org.example.utilities;

import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
            sessionFactory = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url", System.getProperty("DB_URL"))
                    .setProperty("hibernate.connection.username", System.getProperty("DB_USERNAME"))
                    .setProperty("hibernate.connection.password", System.getProperty("DB_PASSWORD"))
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.show_sql", "false")
                    .setProperty("hibernate.format_sql", "false")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}