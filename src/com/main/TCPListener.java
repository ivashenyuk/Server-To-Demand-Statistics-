package com.main;

public interface TCPListener {
    void onConnectionReady(Connection tcpConnection);
    void onReceive(Connection tcpConnection, String data);
    void onDisconnect(Connection tcpConnection);
    void onExeption(Connection tcpConnection, Exception ex);
}
