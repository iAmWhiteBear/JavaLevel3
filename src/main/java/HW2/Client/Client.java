package HW2.Client;

import HW2.GUI.ChatFrame;

class Client {
    private final ChatFrame morda;
    private final ClientConnection connection;
    public Client() {
        connection = new ClientConnection();
        //подключение графики и отправка сообщений
        morda = new ChatFrame(connection::sendMessage);
        History.restoreLast(100,morda.getChatConsumer());

        new Thread(() ->{
            String message;
            while (true){
                //приём сообщений
                message = connection.receiveMessage();
                if (message.split("\\s").length>1){
                    morda.getChatConsumer().accept(message);
                    History.writeToFile(message);
                }
            }
        }).start();
    }
}
