package HW2.Server;

import HW2.BD.BaseOperations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ClientHandler {
    private final Socket socket;
    private final MultiServer server;
    private DataInputStream input;
    private DataOutputStream output;
    private User user;
    private int countDown = 0;
    private final int TIMEOUT = 120;
    private volatile boolean authorized = false;
    private volatile boolean streamController = true;


    public ClientHandler(Socket clientSocket, MultiServer multiServer) {
        this.socket = clientSocket;
        this.server = multiServer;
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                getTimer().start();
                runHandler();
                receiveMessage();
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean doAuthentication(){
        try {
            String authorisation = input.readUTF();
            if (authorisation.startsWith("-auth")) {
                //разделение тэга, логина и паролья
                String[] inputLine = authorisation.split("\\s");
                //поиск указанного пользвателя
                Optional<User> userMayBe = BaseOperations.findUserByLoginPassword(inputLine[1], inputLine[2]);
                if (userMayBe.isPresent()) {
                    user = userMayBe.get();
                    authorized = true;
                    return true;
                } else sendMessage("this login/password not found");
            } else {
                sendMessage("should starts with -auth");
            }
        } catch (ArrayIndexOutOfBoundsException oob){
            sendMessage("there is must be login and password after -auth");
        } catch (IOException e) {
            throw new RuntimeException(socket + " timeout disconnect");
        }

        return false;
    }

    private void runHandler(){
        sendMessage("please log in by: -auth login password");
        while (streamController){
            //авторизация
            if (doAuthentication()){
                //исключения дублирования захода
                if (!server.isLoggedIn(user.getName())){
                    server.logInUser(this);
                    sendMessage(String.format("welcome! %s",user.getName()));
                    server.broadcast(String.format("%s join to chat", user.getName()));
                    return;
                }else {
                    sendMessage("this user is already logged in");
                }
            } else {
                sendMessage("incorrect authorisation");
            }
        }
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(socket + " timeout disconnect");
        }
    }

    public void receiveMessage(){
        while (streamController){
            try {
                String messageRaw = input.readUTF();
                String[] messageSep = messageRaw.split("\\s");
                //обработка тэгов
                switch (messageSep[0]){
                    case "/w":      sendWhisper(messageSep); break;
                    case "/nick":   changeNickTo(messageSep[1]);break;
                    default: server.broadcast(String.format("%s: %s", user.getName(), messageRaw));
                }
            } catch (IOException e) {
                //процедура потери связи с клиентом
                server.logOutUser(this);
                server.broadcast(user.getName()+" disconnected");
                break;
            }
        }
    }

    private void changeNickTo(String newNickName) {
        BaseOperations.changeNickName(user,newNickName);
        sendMessage("никнейм изменён на: "+ newNickName);
        user = BaseOperations.findUserByLoginPassword(user.getLogin(),user.getPass()).get();
    }

    public void sendWhisper(String[] messageSep){
        String message = Arrays.stream(messageSep)
                .skip(2)
                .collect(Collectors.joining(" "));
        if (server.isLoggedIn(messageSep[1])){
            //отправка личного сообщения, копия сообщения себе.
            server.whisper(messageSep[1],String.format("private %s: %s",user.getName(), message));
            sendMessage(String.format("private %s: %s",messageSep[1], message));
        }
    }




    public String getUsername() {
        return user.getName();
    }

    //таймер для автоматического прерывания сессии
    private Thread getTimer(){
        return new Thread(() ->{
            while (countDown<TIMEOUT && !authorized) {
                try {
                    Thread.sleep(1000);
                    countDown++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //отключение соединения по завершению таймера,
            //если пользователь так и не авторизировался
            if (!authorized){
                try {
                    sendMessage("вы не авторизировались и будете отключены от сервера");
                    streamController = false;
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
