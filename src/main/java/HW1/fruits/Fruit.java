package HW1.fruits;

public abstract class Fruit{
    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public abstract float getWeight();
}
