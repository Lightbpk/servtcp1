package com.sample.tools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//--------------------------------------------------------------------------
//---------------------------Lisning 127.0.0.1 port portnum ----------------
//--------------------------------------------------------------------------
public class Tcpserver {
    public static final String PROPERTIES = "server.properties";
    InputStream inputStream;
    private Statement statement;
    private Connection connection;
    private ServerSocket serverSocket;
    private Socket socket = null;
    private int i = 0;
    private ExecutorService exec = Executors.newCachedThreadPool();
    private String SQLDOWN = "SQL server not found";

    Properties prop = new Properties();

    public void init() {
        int portnum;
        int n;
        int s = 0;
        File[] logFiles;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES);
            prop.load(inputStream);
            portnum = Integer.parseInt(prop.getProperty("port"));
            System.out.println("lisening 127.0.0.1 port " + portnum);
            serverSocket = new ServerSocket(portnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tcpservdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    "toor");
            System.out.println("connection ok");
            //statement = connection.createStatement();

        } catch (SQLException e) {
            //e.printStackTrace();

            System.out.println("SQL DOWN");
            File dirLogs = new File("C://servtcp1Logs");
            File filename;
            if (!dirLogs.exists()) {
                boolean cf = dirLogs.mkdir();
                System.out.println("Dir make");
            }
            logFiles = dirLogs.listFiles();
            try {
                System.out.println("start");
                n = logFiles.length;
                if(n==0){
                    filename = new File("C://servtcp1Logs//log" + 1 + ".txt");
                    System.out.println("1");
                }else {
                    filename = new File("C://servtcp1Logs//log" + n + ".txt");
                    System.out.println("n");
                }
                System.out.println("go on");
                if (!filename.exists()) {
                    boolean cf = filename.createNewFile();
                    System.out.println("creating file");
                }

                FileInputStream fileInputStream = new FileInputStream(filename);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                System.out.println("buff");
                while (bufferedReader.readLine() != null) {
                    System.out.println("s");
                    s++;
                }
                if (s > 10) {
                    filename = new File("C://servtcp1Logs//log" + (n+1) + ".txt");
                    s=0;
                }
                System.out.println(""+s);
                FileWriter writer = new FileWriter(filename, true);
                writer.append("\r\n");
                writer.write(SQLDOWN);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                System.out.println("Try err");
                System.out.println(ex.getMessage());
            }

        }
    }

    //--------------------------------------------------------------------------
//-----------------------Start MultiServer Factory--------------------------
//--------------------------------------------------------------------------
    public void start() {
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                System.out.println("accepted");
                i++;
                exec.execute(new MultiSocket(socket, i, connection));
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}
