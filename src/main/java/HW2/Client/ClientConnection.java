package HW2.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private DataInputStream input;
    private DataOutputStream output;

    public ClientConnection() {
        //соединение по адресу 127.0.0.1, на порт 8090 (тот что сервер слушает)
        try {
            Socket socket = new Socket("127.0.0.1",8090);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message){
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException("error while send message");
        }
    }

    public String receiveMessage(){
        try {
            return input.readUTF();
        } catch (IOException e) {
            throw new RuntimeException("error while receive message");
        }
    }
}
