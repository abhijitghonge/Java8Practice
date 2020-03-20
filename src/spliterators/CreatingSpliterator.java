package spliterators;

import spliterators.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Abhijit.Ghonge
 */
public class CreatingSpliterator {

    public static void main(String[] args) {
        Path path = Paths.get("files/people.txt");
        try (Stream<String> lines = Files.lines(path);) {

            Spliterator<String> linesSpliterator = lines.spliterator();

            Spliterator<Person> peopleSpliterator = new PersonSpliterator(linesSpliterator);
            Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);
            people.mapToInt(Person::getAge)
                    .filter(age -> age > 20)
                    .average().ifPresent(System.out::println);

//            people.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> createPeople(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path);) {

            Spliterator<String> linesSpliterator = lines.spliterator();

            Spliterator<Person> peopleSpliterator = new PersonSpliterator(linesSpliterator);
            return StreamSupport.stream(peopleSpliterator, false).collect(Collectors.toList());
        }
    }
}
