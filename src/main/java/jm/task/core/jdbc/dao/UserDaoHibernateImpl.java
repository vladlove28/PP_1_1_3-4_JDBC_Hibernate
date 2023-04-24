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

   private final Session session = Util.getSessionFactory().openSession();
   Transaction transaction = session.getTransaction();
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
            session.beginTransaction();
            Query queryCreate = session.createSQLQuery(table).addEntity(User.class);
            queryCreate.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
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
//            session.beginTransaction();
            Query queryCreate = session.createSQLQuery(dropTable).addEntity(User.class);
            queryCreate.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
//        User user = new User(name,lastName,age);
        try {
            User user = new User(name,lastName,age);
            Transaction ek = session.beginTransaction();
//               session1.beginTransaction();
                session.save(user);
            ek.commit();
        } catch ( Exception e) {
            transaction.rollback();
        } finally {
                session.close();
            }
        }


    @Override
    public void removeUserById(long id) {

        try {
//            session.beginTransaction();

            session.delete(id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
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

            Transaction ek = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            ek.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
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
        String cleanTable = "DELETE FROM User";

        try {
//            session.beginTransaction();
            Query query = session.createQuery(cleanTable);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

