package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {

   private final SessionFactory sessionFactory = Util.getSessionFactory();


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String table = " CREATE TABLE IF NOT EXISTS User (id INTEGER NOT NULL AUTO_INCREMENT," +
                " name VARCHAR(45) NOT NULL," +
                " lastName VARCHAR(45) NOT NULL," +
                " age DOUBLE NOT NULL, PRIMARY KEY (id)," +
                " UNIQUE INDEX id_UNIQUE ( id ASC ) VISIBLE)";

        String sql = "CREATE TABLE IF NOT EXISTS User " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age DOUBLE NOT NULL)";

        Session sesion = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = sesion.beginTransaction();
            Query<User> queryCreate = sesion.createSQLQuery(table).addEntity(User.class);
            queryCreate.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
                e.printStackTrace();

        } finally {
            sesion.close();
            }
        }



    @Override
    public void dropUsersTable() {
        String dropTable = "DROP table if exists User";
        Session sesion = null;
        try {
            sesion =  sessionFactory.openSession();
            sesion.beginTransaction();
            Query queryCreate = sesion.createSQLQuery(dropTable);
            queryCreate.executeUpdate();
            sesion.getTransaction().commit();
        } catch (Exception e) {
            if(sesion.getTransaction() != null){
                sesion.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            sesion.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        Session sesion = null;
        try {
            sesion =  sessionFactory.openSession();
            sesion.beginTransaction();
            sesion.save(user);
            sesion.getTransaction().commit();
        } catch ( Exception e) {
            if(sesion.getTransaction() != null){
                sesion.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            sesion.close();
            }
        }


    @Override
    public void removeUserById(long id) {
        Session sesion = null;
        try {
            sesion =  sessionFactory.openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("DELETE FROM User WHERE id = :id");
            query.setParameter("id",1);
            sesion.getTransaction().commit();
        } catch (Exception e) {
            if (sesion.getTransaction() != null) {
                sesion.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            sesion.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        Session sesion = null;
        try {
            sesion =  sessionFactory.openSession();
            sesion.beginTransaction();
            users = sesion.createQuery("select u from User u where u.id > 0", User.class).getResultList();

            sesion.getTransaction().commit();
        } catch (Exception e) {
            if (sesion.getTransaction() != null) {
                sesion.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            sesion.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM User";
        Session sesion = null;
        try {
            sesion =  sessionFactory.openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery(cleanTable);
            query.executeUpdate();
            sesion.getTransaction().commit();

        } catch (Exception e) {
            if (sesion.getTransaction() != null) {
                sesion.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            sesion.close();

        }
    }
}

