import HW6.Methods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MethodsTest {

    @ParameterizedTest
    @MethodSource("sourceForTestNoFourException")
    public void shouldThrowRuntimeExceptionNoFour(Integer[] inputArray, Class<RuntimeException> resolver){
        Assertions.assertThrows(resolver, () -> Methods.mapIntArrayAfterFour(inputArray));
    }

    @ParameterizedTest
    @MethodSource("sourceForTestMapIntToArrayAfterLastFour")
    public void shouldMapIntArrayAfterLastFour(Integer[] inputArray, Integer[] expected){
        Assertions.assertArrayEquals(Methods.mapIntArrayAfterFour(inputArray),expected);
    }

    @ParameterizedTest
    @MethodSource("sourceIsHasOneOrFourPositive")
    public void testIsHasOneOrFourPositive(Integer[] inputArray){
        Assertions.assertTrue(Methods.isHasOneOrFour(inputArray));
    }

    @ParameterizedTest
    @MethodSource("sourceIsHasOneOrFourNegative")
    public void testIsHasOneOrFourNegative(Integer[] inputArray){
        Assertions.assertFalse(Methods.isHasOneOrFour(inputArray));
    }

    public static Stream<Arguments> sourceForTestMapIntToArrayAfterLastFour(){
        return Stream.of(
                Arguments.arguments(new Integer[]{1,2,3,4,5,6,7,8,9,0}, new Integer[]{5,6,7,8,9,0}),
                Arguments.arguments(new Integer[]{4,5,4,5,4,5,4,5}, new Integer[]{5}),
                Arguments.arguments(new Integer[]{4}, new Integer[]{})
        );
    }

    public static Stream<Arguments> sourceForTestNoFourException(){
        return Stream.of(
                Arguments.arguments(new Integer[]{},RuntimeException.class),
                Arguments.arguments(new Integer[]{5},RuntimeException.class),
                Arguments.arguments(new Integer[]{5,6,7,8,9,0},RuntimeException.class)
        );
    }

    public static Stream<Arguments> sourceIsHasOneOrFourPositive(){
        return Stream.of(
                Arguments.of(( Object) new Integer[]{0,1,2,5,4,8,7,6,2,1,4,2,5,9}),
                Arguments.of(( Object)new Integer[]{1}),
                Arguments.of(( Object) new Integer[]{4})
        );
    }

    public static Stream<Arguments> sourceIsHasOneOrFourNegative(){
        return Stream.of(
                Arguments.of(( Object)new Integer[]{0,2,5,8,7,6,2,2,5,9}),
                Arguments.of(( Object)new Integer[]{}),
                Arguments.of(( Object)new Integer[]{45})
        );
    }
}
