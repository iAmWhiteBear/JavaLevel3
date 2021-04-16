package HW2.BD;

import HW2.Server.User;

import java.sql.*;
import java.util.Optional;

public class BaseOperations {
    private static final String LOGINTABLE = "users";
    private static final String COLUMNNICKNAME = "nickname";
    private static final String COLUMNLOGIN = "login";
    private static final String COLUMNPASSWORD = "pwd";

    public static Optional<User> findUserByLoginPassword(String login, String password){
        Connection connection = ChatConnection.connect();
        try {
            Optional<User> user;
            PreparedStatement ps = connection.prepareStatement(String.format("SELECT * FROM %s WHERE %s = ? AND %s= ?",LOGINTABLE,COLUMNLOGIN,COLUMNPASSWORD));
            ps.setString(1,login);
            ps.setString(2,password);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                String nickname = result.getString(4);
                user = Optional.of(new User(login, password, nickname));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ChatConnection.close(connection);
        }

        return Optional.empty();
    }

    public static boolean changeNickName(User user,String newNick){
        boolean result = false;
        Connection connection = ChatConnection.connect();

        try{
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(String.format("UPDATE %s SET %s = ? WHERE %s = ?",LOGINTABLE,COLUMNNICKNAME,COLUMNLOGIN));
            ps.setString(1, newNick);
            ps.setString(2,user.getLogin());
            result = ps.execute();
            connection.commit();
        } catch (SQLException throwables) {
            ChatConnection.rollback(connection);
            throwables.printStackTrace();
        }finally {
            ChatConnection.close(connection);
        }

        return result;
    }

}
