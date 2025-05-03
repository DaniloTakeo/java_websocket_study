package com.app.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketServer {

    // Sessões conectadas e apelidos associados
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Session, String> nicknames = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Nova conexão: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject json = new JSONObject(message);

        if (json.getString("type").equals("init")) {
            String nickname = json.getString("nickname");
            nicknames.put(session, nickname);
            broadcast("👋 " + nickname + " entrou na sala.");
            broadcastUserList();
        } else if (json.getString("type").equals("message")) {
            String content = json.getString("content");
            String nickname = nicknames.getOrDefault(session, "Anônimo");
            broadcast("💬 " + nickname + ": " + content);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        String nickname = nicknames.remove(session);
        if (nickname != null) {
            broadcast("🚪 " + nickname + " saiu da sala.");
            broadcastUserList();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Erro na sessão " + session.getId() + ": " + throwable.getMessage());
    }

    private void broadcast(String message) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar para " + s.getId() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
    
    private void broadcastUserList() {
        List<String> users = new ArrayList<>(nicknames.values());
        JSONObject json = new JSONObject();
        json.put("type", "userList");
        json.put("users", users);

        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(json.toString());
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar lista para " + s.getId());
                    }
                }
            }
        }
    }
}
