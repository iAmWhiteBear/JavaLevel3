package HW4;

public class Sender implements Runnable{
    private final Resource surs;
    private final char letter;
    private final int pos;
    private static final int nRepeat = 5;

    public Sender(Resource surs, char letter, int pos) {
        this.surs = surs;
        this.letter = letter;
        this.pos = pos;
    }

    @Override
    public void run() {
        for (int i = 0; i < nRepeat; i++) {
            surs.checkMe(pos);// кидает в ожидание, до тех пор, пока не будет его очередь
            surs.setValue(letter);
        }
        surs.finishThread();

    }
}
