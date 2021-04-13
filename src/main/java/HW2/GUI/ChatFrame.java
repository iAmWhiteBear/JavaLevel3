package HW2.GUI;

import javax.swing.*;
import java.util.function.Consumer;

public class ChatFrame {
    private final ChatPanel panel;

    public ChatFrame(Consumer<String> receiver) {
        JFrame frame = new JFrame("MyChat");
        panel = new ChatPanel(receiver);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Consumer<String> getChatConsumer() {
        return panel.updateChatArea();
    }




}
