package com.app.websocket;

import org.glassfish.tyrus.server.Server;

public class WebSocketServerMain {

    public static void main(String[] args) {
        // Configura o servidor WebSocket para usar o endpoint definido
        
        Server server = new Server("localhost", 8080, "/", null, WebSocketServer.class);
        
        try {
            // Inicia o servidor
            server.start();
            System.out.println("Servidor WebSocket rodando em ws://localhost:8080/websocket");

            // Manter o servidor rodando
            Thread.sleep(100000); // Serve para o servidor rodar por um tempo
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Para o servidor quando o programa terminar
            server.stop();
        }
    }
}
