package HW2.Server;

import javax.sound.sampled.FloatControl;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class History {
    private final String filePath;

    public History(String username) {
        String basefilePath = "userHistory.txt";
        filePath = username+ basefilePath;
    }

    public void writeToFile(String line){
        //добавление очередной записи в файл
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            writer.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreLast(int nLines, Consumer<String> consumer){
        //прочитать из файла все линии и сохранить сколько надо
        Deque<String> deq = new ArrayDeque<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine())!=null){
                if (deq.size()>nLines){
                    deq.removeFirst();
                    deq.add(line);
                }else {
                    deq.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //маркер конца истории
        deq.add("--- конец истории ---");
        // скормить все линии консьюмеру
        String line;
        while ((line = deq.pollFirst())!=null){
            consumer.accept(line);
        }
    }
}
