package com.app.websocket;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/websocket")
public class WebSocketServer {

    // Conjunto sincronizado de sessões ativas
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Nova conexão: " + session.getId());
        broadcast("Usuário " + session.getId() + " entrou.");
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) {
        System.out.println("Mensagem recebida de " + senderSession.getId() + ": " + message);
        broadcast("Usuário " + senderSession.getId() + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Sessão fechada: " + session.getId());
        broadcast("Usuário " + session.getId() + " saiu.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Erro na sessão " + session.getId() + ": " + throwable.getMessage());
    }

    private void broadcast(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar mensagem para " + session.getId() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}