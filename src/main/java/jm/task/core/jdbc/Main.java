package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("Name1", "LastName1", (byte) 20);
        userServiceImpl.saveUser("Name2", "LastName2", (byte) 25);
        userServiceImpl.saveUser("Name3", "LastName3", (byte) 31);
        userServiceImpl.saveUser("Name4", "LastName4", (byte) 38);


        userServiceImpl.getAllUsers();
        List<User> allUsers = userServiceImpl.getAllUsers();
        allUsers.forEach(System.out::println);
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();




        // реализуйте алгоритм здесь




    }
}
