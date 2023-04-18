package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Session session;


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String table = " CREATE TABLE IF NOT EXISTS userTable (id INTEGER NOT NULL AUTO_INCREMENT," +
                " name VARCHAR(45) NOT NULL," +
                " lastName VARCHAR(45) NOT NULL," +
                " age DOUBLE NOT NULL, PRIMARY KEY (id)," +
                " UNIQUE INDEX id_UNIQUE ( id ASC ) VISIBLE)";


        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query queryCreate = session.createSQLQuery(table).addEntity(User.class);
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
        String dropTable = "DROP TABLE IF EXISTS userTable";


        try {
            session = Util.getSessionFactory().openSession();
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
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(user);

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
    public void removeUserById(long id) {

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            session.delete(id);

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
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
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
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE userTable";

        try {
            session = Util.getSessionFactory().openSession();
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
            if (session != null) {
                session.close();
            }
        }
    }
}

