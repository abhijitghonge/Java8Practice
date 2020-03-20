package streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abhijit.Ghonge
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(0,1,2,3,4,5,6,7,8);

        Set<Integer> randomNumbers = Stream.generate(()->ThreadLocalRandom.current().nextInt(9999)).limit(9999).collect(Collectors.toSet());

        System.out.println("size:"+randomNumbers.size());

        ints.stream().forEach(System.out::println);
    }
}
