package HW2.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * я не стал добавлять кнопку отправить, потому что по нажатию enter удобнее
 * и когда такая опция есть - кнопка никогда не используется
 */
public class ChatPanel extends JPanel implements ActionListener {
    private final JTextField textField;
    private final JTextArea textArea;
    private final static String newline = "\n";
    private Consumer<String> receiver;

    public ChatPanel(Consumer<String> receiver) {
        super(new GridBagLayout());

        //инициализация функций
        this.receiver = receiver;

        //текстовое поле, куда писать
        textField = new JTextField(40);
        textField.addActionListener(this);
        //текстовое поле, где читать
        textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 241, 207));
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Добавление компонентов на панель
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane, c);
        add(textField, c);
    }

    //добавление текста в чат
    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        receiver.accept(text); //отправка текста по коллбэку
        textField.setText("");
    }

    //обработка входящих сообщений
    public Consumer<String> updateChatArea() {
        return (message) -> {
            if (!message.isEmpty()) {
                textArea.append(message + newline);
                textArea.selectAll(); //переход на последнее сообщение
            }
        };
    }
}

