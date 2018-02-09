package com.sample.tools;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MultiSocket implements Runnable {
private Socket socket;
private InputStream is = null;
private String nextLine;
private int i;
private Statement statement;
private Connection connection;
private boolean runnable = true;
private Date date;
private String msg;
    MultiSocket(Socket socket, int i, Connection connection){
        this.socket = socket;
        this.i = i;
        this.connection = connection;
    }
    public void run() {
        try {
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

        while (runnable) {
            try {
                is = socket.getInputStream();
                DataInputStream in = new DataInputStream(is);
                nextLine = in.readUTF();
                String usr = nextLine.substring(0,nextLine.indexOf(" "));
                msg = nextLine.substring(nextLine.indexOf(" "),nextLine.length());
                System.out.println("in thread "+i+" "+nextLine);
                date = new Date();
                statement = connection.createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS messages(id integer primary key auto_increment, THREAD integer, USERS varchar(50), MESSAGES varchar(100), DATE varchar(50));");
                statement.execute("INSERT INTO messages (THREAD, USERS, MESSAGES, DATE) VALUES ("+i+",'"+usr+"','"+msg+"','"+date.toString()+"')");
            } catch (IOException e) {
                runnable = false;
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
