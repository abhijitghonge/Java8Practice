package collectors;

import spliterators.CreatingSpliterator;
import spliterators.model.Person;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.*;

/**
 * @author Abhijit.Ghonge
 */
public class MainCollectorsExample {
    public static void main(String[] args) throws IOException {

        List<Person> people = CreatingSpliterator.createPeople("files/people.txt");
        Person oldest = people.stream()
                                    .max(comparing(Person::getAge))
                                    .orElseGet(() -> new Person("Not Applicable", 0, "Not Applicale"));
        double averageAge = people.stream().collect(averagingDouble(Person::getAge));
        System.out.printf("# oldest: %s, Average Age: %s\n",oldest, averageAge);

        Map<String, List<Person>> peopleInCity = people
                                                    .stream()
                                                    .parallel()
                                                    .collect(groupingBy(Person::getCity));
        System.out.println("# People in a city "+peopleInCity);

        //Example of downstream collector
        //Count people of same age
        Map<Integer, Long> numberOfPersonsByAge = people.stream().collect(groupingBy(Person::getAge, counting()));
        System.out.println("# Grouped peoply by age: "+numberOfPersonsByAge);

        TreeMap<Integer, TreeSet<String>> namesByAge = people.stream().collect(groupingBy(Person::getAge, TreeMap::new, mapping(Person::getName, toCollection(TreeSet::new))));

        System.out.println("#Sorted on age and names:"+namesByAge);

        //Collecting into a immutable map
        Map<Integer, List<Person>> peopleByAge = people.stream()
                                                        .collect(Collectors.collectingAndThen(groupingBy(Person::getAge), Collections::unmodifiableMap));

    }
}
