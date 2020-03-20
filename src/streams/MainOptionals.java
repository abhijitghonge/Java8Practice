package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abhijit.Ghonge
 */
public class MainOptionals {

    public static void main(String[] args) {
        double[] doubles = {1.0, 1.5, 2.7, 1.8, 2.5};

        List<Double> result = new ArrayList<>();

        Arrays.stream(doubles).forEach(d -> {
            NewMath.inv(d).flatMap(NewMath::sqrt).ifPresent(result::add);
        });

        Function<Double, Stream<Double>> doubleStreamFunction = d ->
                NewMath.inv(d).flatMap(NewMath::sqrt)
                        .map(Stream::of).orElseGet(()->Stream.empty());

    }

}
