package HW7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Создать класс, который может выполнять «тесты»,
 * в качестве тестов выступают классы с наборами методов с аннотациями @Test.
 *
 * Для этого у него должен быть статический метод start(),
 * которому в качестве параметра передается или объект типа Class, или имя класса.
 *
 * Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite,
 * если такой имеется, далее запущены методы с аннотациями @Test,
 * а по завершению всех тестов – метод с аннотацией @AfterSuite.
 *
 * К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10),
 * в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый,
 * то порядок не имеет значения.
 *
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре,
 * иначе необходимо бросить RuntimeException при запуске «тестирования».
 */
public class Execution {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        start(ExampleTestClass.class);
    }

    public static void start(Class c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ExampleTestClass obj = (ExampleTestClass) c.getDeclaredConstructor().newInstance();
        Method[] methods = c.getDeclaredMethods();
        ArrayList<Method> tests = new ArrayList<>();
        Method before = null;
        Method after = null;
        for (Method m: methods){
            if (m.getAnnotation(Test.class)!=null) tests.add(m);
            if (m.getAnnotation(BeforeSuite.class)!=null)
                if(before==null) before = m;
                else throw new RuntimeException("dublicate BeforeSuite annotation");
            if (m.getAnnotation(AfterSuite.class)!=null)
                if(after==null)   after = m;
                else throw new RuntimeException("dublicate AfterSuite annotation");
        }
        // упорядочить по приоритету
        tests.sort((m,n)-> new PriorityComparator().compare(m,n));


        //запуск методов
        if (before!=null) before.invoke(obj);
        for(Method t: tests) {
            t.setAccessible(true);
            t.invoke(obj);
        }
        if (after!=null) after.invoke(obj);

    }

    public static void start(String className){
        try {
            Class c = Class.forName(className);
            start(c);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static class PriorityComparator implements Comparator<Method>{
        @Override
        public int compare(Method m1, Method m2) {
            int prior1 = m1.getAnnotation(Test.class).priority();
            int prior2 = m2.getAnnotation(Test.class).priority();
            return Integer.compare(prior1, prior2);
        }
    }
}
