package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

   private final SessionFactory sessionFactory = Util.getSessionFactory();


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String table = "CREATE TABLE IF NOT EXISTS User " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age DOUBLE NOT NULL)";

             Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query<User> queryCreate = session.createSQLQuery(table).addEntity(User.class);
            queryCreate.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS User";

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query queryCreate = session.createSQLQuery(dropTable).addEntity(User.class);
            queryCreate.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
                session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
                session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :id");
            query.setParameter("id",1);
            session.getTransaction().commit();
//            session.delete(id);

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
                session.close();
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
                session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE User";
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createSQLQuery(cleanTable);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
                session.close();
        }
    }
}

