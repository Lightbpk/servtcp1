package com.sample.tools;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MultiSocket implements Runnable {
private Socket socket;
private InputStream is = null;
private String nextLine;
boolean runnable = true;
    MultiSocket(Socket socket){
        this.socket = socket;
    }
    public void run() {
        while (runnable) {
            try {
                is = socket.getInputStream();
                DataInputStream in = new DataInputStream(is);
                nextLine = in.readUTF();
                String usr = nextLine.substring(0,nextLine.indexOf(" "));
                System.out.println(nextLine);
            } catch (IOException e) {
                runnable = false;
            }

        }
    }
}
