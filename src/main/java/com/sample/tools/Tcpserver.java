package com.sample.tools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Tcpserver {

    private ServerSocket serverSocket;
    public Socket socket = null;
    ExecutorService exec = Executors.newCachedThreadPool();
    public void init(int portnum) {
        System.out.println("lisening 127.0.0.1 port " + portnum);
        try {
            serverSocket = new ServerSocket(portnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        while (!serverSocket.isClosed()){
            try {
                socket = serverSocket.accept();
                System.out.println("accepted");
                exec.execute(new MultiSocket(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}
