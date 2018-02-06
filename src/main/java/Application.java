import com.sample.tools.Tcpserver;

import java.sql.*;

public class Application {

    public static void main(String[] args) {
        Tcpserver tcpserver = new Tcpserver();
        tcpserver.init();
        tcpserver.start();
    }
}

