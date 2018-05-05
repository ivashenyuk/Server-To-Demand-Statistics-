package com.main;

import com.google.gson.Gson;
import sun.rmi.transport.tcp.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements TCPListener {
    private ArrayList<DataProduct> productList;
    private DataBase dataBase;

    public Server() {
        dataBase = new DataBase();

        this.productList = this.dataBase.getProductList();
        System.out.println("Server is running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(9090)) {
                    System.out.println(serverSocket.getLocalSocketAddress());
                    while (true) {
                        new Connection(Server.this, serverSocket.accept());
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        thread.start();
    }

    @Override
    public void onConnectionReady(Connection tcpConnection) {
        System.out.println("Added connnection!");
        tcpConnection.SendData(new Gson().toJson(productList));
    }

    @Override
    public void onReceive(Connection tcpConnection, String data) {

    }

    @Override
    public void onDisconnect(Connection tcpConnection) {
        System.out.println("Client was disconnect!");
    }

    @Override
    public void onExeption(Connection tcpConnection, Exception ex) {
        System.out.println("Error!!!");
    }
}
