package HW4;

/**
 * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
 * (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
 */
public class Execution {


    public static void main(String[] args) {
        //нужно в ресурсе точно указывать, сколько будет потоков, иначе не будет работать.
        Resource surs = new Resource(3);

        new Thread(new Sender(surs,'A',0)).start();
        new Thread(new Sender(surs,'B',1)).start();
        new Thread(new Sender(surs,'C',2)).start();

        new Thread(() ->{
            while (!surs.allThreadsDone()){
                System.out.print(surs.getValue());
            }
        }).start();
    }
}
