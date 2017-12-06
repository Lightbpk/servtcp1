package com.sample.tools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Tcpserver {

    private ServerSocket serverSocket;
    public Socket socket = null;
    ExecutorService exec = Executors.newFixedThreadPool(10);
    public void init(int portnum) {
        System.out.println("listening 127.0.0.1 port " + portnum);
        try {
            serverSocket = new ServerSocket(portnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//--------------------------------------------------------------------------
//-----------------------Start Multiserver Factory--------------------------
//--------------------------------------------------------------------------
    public void start(){
        while (!serverSocket.isClosed()){
            try {
                socket = serverSocket.accept();
                System.out.println("accepted");
                Future future = exec.submit(new MultiSocket(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}
