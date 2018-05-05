package com.main;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class Connection {
    public final Socket socket;
    public final Thread rxThread;
    private final TCPListener eventListener;
    public BufferedReader in;
    private BufferedWriter out;

    public Connection(TCPListener eventListener, String ipAddress, int port) throws IOException {
        this(eventListener, new Socket(ipAddress, port));
    }

    public Connection(TCPListener eventListener, Socket socket) throws IOException {
        this.socket = socket;
        this.eventListener = eventListener;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection.this.eventListener.onConnectionReady(Connection.this);
                    while (!rxThread.isInterrupted()) {
                        String data = in.readLine();
                        Connection.this.eventListener.onReceive(Connection.this, data);
                    }
                } catch (NullPointerException e) {
                    rxThread.interrupt();
                } catch (IOException e) {
                    //e.printStackTrace();
                    rxThread.interrupt();
                } finally {
                    Connection.this.eventListener.onDisconnect(Connection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void SendData(String data) {
        try {
            out.write(data + "\r\n");
            out.flush();
        } catch (IOException e) {
            this.eventListener.onExeption(Connection.this, e);
            Disconnect();
        }
    }

    public synchronized void Disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            this.eventListener.onExeption(Connection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnectin: " + socket.getInetAddress() + ":" + socket.getPort();
    }
}
