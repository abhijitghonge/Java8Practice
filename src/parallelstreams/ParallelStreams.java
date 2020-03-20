package parallelstreams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abhijit.Ghonge
 */
public class ParallelStreams {
    public static void main(String[] args) {

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2") ;

        List<String> strings =  Stream.iterate("+", s->s+"+")
                .parallel()
                .limit(1000)
                //.forEach(strings::add);
                .collect(Collectors.toList());
        System.out.println("Size: "+strings.size());
    }
}
