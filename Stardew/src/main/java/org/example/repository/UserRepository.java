package org.example.repository;

import org.example.model.User;
import org.example.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {
    public static void save(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public static User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "from User where username = :username",
                    User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    public static boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }
}
