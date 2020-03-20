package Optionals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abhijit.Ghonge
 */
public class Main {
    public static void main(String[] args) {
        List<Double> result = new ArrayList<>();
//        ThreadLocalRandom.current()
//                .doubles(10_000).boxed(). parallel()
//                .forEach(d -> NewMath
//                                    .sqrt(d)
//                                    .ifPresent(sqrt-> NewMath.inv(sqrt)
//                                            .ifPresent(inv-> result.add(inv))));

        List<Double> collect = ThreadLocalRandom.current()
                .doubles(10_000)
                .map(d-> d*20 -10)
                .boxed()
                .parallel()
                .flatMap(d -> NewMath.inv(d)
                        .flatMap(inv -> NewMath.sqrt(inv))
                        .map(sqrt -> Stream.of(sqrt))
                        .orElseGet(() -> Stream.empty()))
                .collect(Collectors.toList());


        System.out.println(" # result = " + collect.size());
    }
}
