package HW1;

import HW1.fruits.Fruit;
import java.util.ArrayList;
/**
 *  3. Большая задача:
 * a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
 * b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 * c. Для хранения фруктов внутри коробки можете использовать ArrayList;
 * d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
 * e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
 * f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов,
 *      нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается,
 *      а в другую перекидываются объекты, которые были в этой коробке;
 * g. Не забываем про метод добавления фрукта в коробку.
*/

public class Box<T extends  Fruit> {
    private final ArrayList<T> fruitBox;

    public Box() {
        this.fruitBox = new ArrayList<>();
    }

    //g. Не забываем про метод добавления фрукта в коробку.
    public void addFruit(T f){
        fruitBox.add(f);
    }

    //d. Сделать метод getWeight() который высчитывает вес коробки,
    // зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
    public Float getWeight(){
        if (fruitBox.size()>0) {
            T fruit = fruitBox.get(0);
            return fruitBox.size()* fruit.getWeight();
        }
        return 0F;
    }

    //e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
    // которую подадут в compare в качестве параметра,
    // true - если их веса равны, false в противном случае
    // (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
    public boolean compare(Box<? extends Fruit> box){
        return this.getWeight().equals(box.getWeight());
    }

//    f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
//    (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
//      соответственно в текущей коробке фруктов не остается,
//      а в другую перекидываются объекты, которые были в этой коробке;
    public void replaceFruitsTo(Box<T> box){
        fruitBox.forEach(box::addFruit);
        fruitBox.clear();
    }

    public void showBox(){
        fruitBox.forEach(f -> System.out.println(f.getName()));
    }
}
