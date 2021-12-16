package repo;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Generic Class that implements ICrudRepository
 * and sets up the connection to the local database
 * inside the constructor
 * @param <T>: T can be any object
 */
public abstract class SQLRepository<T> implements ICrudRepository<T> {
    Connection connection;
    Statement statement;
    Statement secondStatement;

    protected SQLRepository() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/labmap";
        String username = "root";
        String password = "961688";

        connection = DriverManager.getConnection(jdbcURL, username, password);
        statement = connection.createStatement();
        secondStatement = connection.createStatement();
    }

}
