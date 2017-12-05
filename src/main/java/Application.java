import com.sample.tools.Tcpserver;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        Tcpserver tcpserver = new Tcpserver();
        tcpserver.init(9000);
        tcpserver.start();
        /*try {
            Driver driver = (Driver)Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sandbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    "root");
            Statement statement = connection.createStatement();
            //statement.execute("INSERT INTO sandbox.tcp_messages (message, id) Values ('my message',3); ");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sandbox.tcp_messages");
            while (resultSet.next()==true) {
                System.out.println(resultSet.getString("message"));
                System.out.println(resultSet.getString("id"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}

