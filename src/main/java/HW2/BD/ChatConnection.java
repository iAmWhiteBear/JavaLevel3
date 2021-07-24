package HW2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ChatConnection {

    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/javaChat";
        Properties props = new Properties();
        props.setProperty("user","chatAdmin");
        props.setProperty("password","chatAdmin");
        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
