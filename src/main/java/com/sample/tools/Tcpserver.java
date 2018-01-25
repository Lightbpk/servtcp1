package com.sample.tools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//--------------------------------------------------------------------------
//---------------------------Lisning 127.0.0.1 port portnum ----------------
//--------------------------------------------------------------------------
public class Tcpserver {

    private ServerSocket serverSocket;
    private Socket socket = null;
    private int i=0;
    private ExecutorService exec = Executors.newCachedThreadPool();
    public void init(int portnum) {
        System.out.println("lisening 127.0.0.1 port " + portnum);
        try {
            serverSocket = new ServerSocket(portnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//--------------------------------------------------------------------------
//-----------------------Start MultiServer Factory--------------------------
//--------------------------------------------------------------------------
    public void start(){
        while (!serverSocket.isClosed()){
            try {
                socket = serverSocket.accept();
                System.out.println("accepted");
                i++;
                exec.execute(new MultiSocket(socket,i));
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}
