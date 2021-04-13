package HW2.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MultiServer {

    private final Set<ClientHandler> clients;

    public MultiServer() {
        clients = new HashSet<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8090);
            System.out.println("server started");
            while (true){
                System.out.println("waiting for connection");
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, this);
            }
        } catch (IOException e) {
            System.out.println("server stopped");
            e.printStackTrace();
        }
    }

    public synchronized void logInUser(ClientHandler client){
        clients.add(client);
    }

    public synchronized void logOutUser(ClientHandler client){
        clients.remove(client);
    }

    public void broadcast(String message){
        for (ClientHandler client: clients) client.sendMessage(message);
    }

    public void whisper(String username, String message){
        clients.stream()
                .filter(client -> client.getUsername().equals(username))
                .forEach(client -> client.sendMessage(message));
    }

    public boolean isLoggedIn(String username){
        return clients.stream()
                .anyMatch(client -> client.getUsername().equals(username));
    }
}
