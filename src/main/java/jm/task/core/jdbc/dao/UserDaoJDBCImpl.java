package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = ConnectionUser.connection();
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS user (id bigint primary key auto_increment," +
                    " name varchar(255), lastName varchar(255),age smallint)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user";
    private static final String INSERT_USER_SQL =
            "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USER_SQL = "SELECT id, name, lastName, age FROM user";
    private static final String CLEAN_USERS_SQL = "TRUNCATE TABLE user";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_TABLE_SQL)) {
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(DROP_TABLE_SQL)) {
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            connection.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {
            connection.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_USER_SQL)) {
            connection.setAutoCommit(false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"))
                );
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CLEAN_USERS_SQL)) {
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new DaoException(e);
        }
    }
}
