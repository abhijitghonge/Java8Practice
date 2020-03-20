package mapfilterreduce;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;


/**
 * @author Abhijit.Ghonge
 */
public class MainMapReduce {

    private static Integer reduce(List<Integer> numbers, int identity, BinaryOperator<Integer> reduction){
        int result = identity;
        for (int value: numbers){
            result = reduction.apply(result, value);
        }
        return result;
    }
    public static void main(String[] args) {

        List<Integer> ints = Arrays.asList(0, 1, 2, 3, 4, -1, -2, -3, -4);

        BinaryOperator<Integer> sum = (int1, int2) -> int1 + int2;


        List<Integer> ints1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> ints2 = Arrays.asList(-1, -2, -3, -4);

        int reduction1 = reduce(ints1, 0 , sum);
        int reduction2 = reduce(ints2, 0 , sum);


        System.out.println(reduce(ints, 0,  sum));
    }
}
