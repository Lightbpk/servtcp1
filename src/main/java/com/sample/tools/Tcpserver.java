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
/*
* TO DO
* 1 Просмотри комменты в коде, поправь
* 2 Добавь все файлы относящиеся к idea в игнор gita чтобы их не было в репозитории.
*   иначе когда сливешь проект idea ругается
*   гугли .gitignore
* 3 Почитай про логгеры для джавы. Это Log4j slf4j. Подключи их в свой проект.
*   Это мощные библиотеки для логирования, это важная часть создания программ.
*   Когда софт сдается на промэксплуатацию или просто работает и падает больше нет вариантов узнать что с ним было кроме как из логов
*   Эти две библиотеки для логирования есть почти везде и надо уметь ими пользоваться
*   Все что ты выводишь через System.out.println теперь ты должен выводить с помошью логгера
*   Используй несколько уровней логирования info, debug, error
*   Настройки логгера вынеси в файл пропертей
* 4 Объедини оба своих проекта server и client в один. Загугли многомодульные проекты maven
*   "parent pom".
*   Удобно когда ты можешь работать с проектом внутри одной ide.
*   Сервер и клиент логически связаны и могут иметь общую кодовую базу поэтому имеет смысл держать их в одном проекте
*   но в разных maven артефактах.
*   (Перед тем как будешь делать эту задачу, обязательно сделай бекапы, просто скопировав их на диск в другое место
*   для надежности Ж)
*
* */
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
            // Вынеси это в настройки C://servtcp1Logs
            // при старте выводи конфигурацию в консоль чтобы было видно твоя программа пишет
            // иначе можно долго искать свои логи
            File dirLogs = new File("C://servtcp1Logs");
            File filename;
            if (!dirLogs.exists()) {
                boolean cf = dirLogs.mkdir();
                //Не стесняйся писать длинные и доходчивые комменты это приветствуется
                // например
                // Check log dir...
                // Log directory 'servtcp1Logs' is not exist in C:\
                // Created directory C:\servtcp1Logs
                //
                // Check log dir...
                // OK! Log directory C:\servtcp1Logs exist
                //
                // В таком случае понятно что ты проверял где это живет и как это исправлять в случае косяков
                // исходи из того что никто не имеет доступ к твоим исходникам а есть только логи
                System.out.println("Dir make");
            }
            logFiles = dirLogs.listFiles();
            try {
                // то же самое непонятно старт чего произошел
                System.out.println("start");
                n = logFiles.length;
                if(n==0){
                    // все что повторяется выводи в переменные, вот эту часть  чтобы по два раза не исправлять.
                    // так удобнее и безопаснее, иначе будешь долго выяснять где что то сломалось когда забыл исправить
                    filename = new File("C://servtcp1Logs//log" + 1 + ".txt");
                    // из логов неясно что это за 1
                    System.out.println("1");
                }else {
                    filename = new File("C://servtcp1Logs//log" + n + ".txt");
                    System.out.println("n");
                }
                // тоже что за go on из одних логов непонятно
                System.out.println("go on");
                if (!filename.exists()) {
                    boolean cf = filename.createNewFile();
                    //Когда что-то создаешь в файловой системе пиши где и что ты создал
                    System.out.println("creating file");
                }

                FileInputStream fileInputStream = new FileInputStream(filename);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                // нужен подробный коммент в логи
                System.out.println("buff");
                while (bufferedReader.readLine() != null) {
                    System.out.println("s");
                    s++;
                }
                if (s > 10) {
                    // вынеси путь в переменую
                    filename = new File("C://servtcp1Logs//log" + (n+1) + ".txt");
                    s=0;
                }
                // подробный коммент в логи
                System.out.println(""+s);
                FileWriter writer = new FileWriter(filename, true);
                writer.append("\r\n");
                writer.write(SQLDOWN);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                // подробный коммент в логи
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
                // должен написать что он принял соединение от такого то ip на таком то порту в такое то время
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
