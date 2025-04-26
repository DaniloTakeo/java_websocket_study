package com.app.websocket;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketServer {

	@OnOpen
	public void onOpen(Session session) {
        System.out.println("Conexão aberta: " + session.getId());
    }
	
	@OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Mensagem recebida: " + message);
        return "Mensagem recebida: " + message; // Responde de volta ao cliente
    }
	
	@OnClose
    public void onClose(Session session) {
        System.out.println("Conexão fechada: " + session.getId());
    }
	
	@OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Erro na conexão " + session.getId());
        throwable.printStackTrace();
    }
}
