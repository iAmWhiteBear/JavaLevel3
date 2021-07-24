package HW1;


import HW1.fruits.Apple;
import HW1.fruits.Fruit;
import HW1.fruits.Orange;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
 * 2. Написать метод, который преобразует массив в ArrayList;
 */
public class Execution {



    public static void main(String[] args) {
        String[] strings = {"a","b","c","d","e"};
        Integer[] integers = {1,2,3,5,6,7};

        //1
        exchange(strings,1,2);
        System.out.println(Arrays.asList(strings));


        //2
        System.out.println(getArrayList(integers));

        //3
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();
        Box<Orange> apelsin = new Box<>();
        appleBox.addFruit(new Apple("мартыновка"));
        appleBox.addFruit(new Apple("Голден"));
        orangeBox.addFruit(new Orange("Canaria"));
        apelsin.addFruit(new Orange("Грузинский"));

        System.out.println(appleBox.compare(orangeBox));
        System.out.println(orangeBox.compare(apelsin));
        orangeBox.replaceFruitsTo(apelsin);
        orangeBox.showBox();
        apelsin.showBox();

    }

    public static <T> void exchange(T[] array, int first, int second){
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    public static <A> ArrayList<A> getArrayList(A[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

}
