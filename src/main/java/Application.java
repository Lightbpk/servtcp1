import com.sample.tools.Tcpserver;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        Tcpserver tcpserver = new Tcpserver();
        tcpserver.init(9001);
        tcpserver.start();
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("driver ok");
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("!");
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tcpservdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    "toor");
            System.out.println("connection ok");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO tcpservdb.messages (thread, usr, message) Values (1,'usr','sometext'); ");
            *//*ResultSet resultSet = statement.executeQuery("SELECT * FROM sandbox.tcp_messages");
            while (resultSet.next()==true) {
                System.out.println(resultSet.getString("message"));
                System.out.println(resultSet.getString("id"));
            }*//*
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("!");
        }*/
    }
}

