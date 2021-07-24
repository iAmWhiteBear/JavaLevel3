package HW6;

import java.util.Arrays;

public class Methods {
    /**
     * 2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
     *     Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
     *     идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
     *     иначе в методе необходимо выбросить RuntimeException.
     *     Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
     * @param inputArray  входящий массив целых чисел
     * @return Integer[]
     * @throws RuntimeException выкинет если в массиве нет 4
     */
    public static Integer[] mapIntArrayAfterFour(Integer[] inputArray) throws RuntimeException{
        for (int i = inputArray.length-1; i >= 0; i--) {
            if (inputArray[i]==4){
                int len = inputArray.length-(i+1);
                Integer[] outArray = new Integer[len];
                System.arraycopy(inputArray,i+1,outArray,0,len);
                return outArray;
            }
        }
        throw new RuntimeException("no found four");
    }

    /**
     * 3. Написать метод, который проверяет состав массива из чисел 1 и 4.
     *     Если в нем нет хоть одной четверки или единицы, то метод вернет false;
     *     Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     * @param inputArray входящий массив целых чисел
     * @return boolean
     */
    public static boolean isHasOneOrFour(Integer[] inputArray){
        return Arrays.stream(inputArray)
                .anyMatch(integer -> integer==4 || integer == 1);
    }
}
