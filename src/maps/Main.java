package maps;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Abhijit.Ghonge
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Person p1 = new Person("Alice", 23);
        Person p2 = new Person("Brian", 56);
        Person p3 = new Person("Chelsea", 46);
        Person p4 = new Person("David", 28);
        Person p5 = new Person("Erica", 37);
        Person p6 = new Person("Francisco", 18);

        City newYork = new City("New York");
        City shanghai = new City("Shanghai");
        City paris = new City("Paris");

        Map<City, List<Person>> map = new HashMap<>();

        map.putIfAbsent(paris, new ArrayList<>());
        map.get(paris).add(p1);

        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p2);
        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p3);

        logger.info("people from paris: " + map.getOrDefault(paris, Collections.EMPTY_LIST));
        logger.info("people from new york: " + map.getOrDefault(newYork, Collections.EMPTY_LIST));

        Map<City, List<Person>> map1 = new HashMap<>();
        map1.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p1);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p2);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p3);

        logger.info("Map 1");
        map1.forEach((city, people) -> logger.info(city + ":" + people));

        map.forEach((city, people) ->
                map1.merge(city, people, (existingPeople, newPeople) -> {
            existingPeople.addAll(newPeople);
            return existingPeople;
        }));

        logger.info("Merged Map");
        map1.forEach((city, people) -> logger.info(city + ":" + people));

        List<Person> people = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6));

        people.parallelStream().filter(person -> person.getAge() > 20).forEach(System.out::println);

        people.removeIf(person -> person.getAge() < 30);

        people.replaceAll(person -> new Person(person.getName().toLowerCase(), person.getAge()));

        people.sort(Comparator.comparing(Person::getAge).reversed());
        people.forEach(System.out::println);
    }
}
