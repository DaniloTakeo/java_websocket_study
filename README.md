# Projeto WebSocket com Java, Tomcat e Nginx

Este é um projeto de estudo que demonstra a implementação de uma **aplicação de chat em tempo real** utilizando WebSocket.

A aplicação é composta por:

- **Servidor WebSocket**: Implementado em Java, rodando no Tomcat
- **Cliente Web**: Construído com HTML e JavaScript, servido por Nginx
- **Ambiente de execução**: Totalmente conteinerizado com Docker Compose

---

## 🚀 Como executar

### Pré-requisitos

- Docker
- Docker Compose
- Maven (para build do servidor)

### Passos

1. **Build do projeto Java (gera o `.war`)**

```bash
cd websocket-server
mvn clean package
```

2. **Volte para o diretório raiz e execute os containers**

```bash
docker-compose up --build
```

3. **Acesse o cliente no navegador**

```arduino
http://localhost/
```

---

## 📡 Comunicação WebSocket

- O cliente HTML se conecta ao servidor usando WebSocket:
```arduino
ws://localhost:8080/websocket
```
---

## 🛠 Tecnologias e ferramentas
- Java 17
- Tomcat 9
- HTML5 + JavaScript
- WebSocket API (javax.websocket)
- Nginx
- Docker + Docker Compose

---

## 📚 Objetivo
**O objetivo do projeto é:**
- Entender o funcionamento do protocolo WebSocket
- Praticar a construção de um servidor de chat com broadcast
- Isolar cliente e servidor em containers distintos
- Usar o Nginx para servir o cliente e fazer proxy reverso se necessário
- Aprender a fazer deploy de aplicações Java com Tomcat via Docker

---

## 👨‍💻 Autor
Danilo Takeo Kanizawa

