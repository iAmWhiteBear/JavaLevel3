package HW4;

public class Resource {
    private char value;
    private boolean onReadState = false;
    private int counter = 0;
    private int threadsDone = 0;
    private final int threadsTotal;

    public Resource(int threadsTotal) {
        this.threadsTotal = threadsTotal;
    }

    //запись данных в общий ресурс
    public synchronized void setValue(char s) {
        while (onReadState){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        onReadState = true;
        value = s;
        counter++;
        notifyAll();
    }

    //чтение данных из общего ресурса
    public synchronized char getValue() {
        while (!onReadState){
            try
            {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        onReadState = false;
        notifyAll();
        return value;
    }

    //проверка очерёдности потоков
    public synchronized void checkMe(int mod){
        while (counter%threadsTotal!=mod){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishThread(){
        threadsDone++;
    }

    //Проверка выполнения всех потоков
    public boolean allThreadsDone(){
        return threadsDone==threadsTotal;
    }
}
