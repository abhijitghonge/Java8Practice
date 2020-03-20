package comparators;


import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Abhijit.Ghonge
 */
public class MainComparator {
    public static void main(String[] args) {

        Person swayam = new Person("Swayam", "Ghonge", 6);
        Person arkaj = new Person("Arkaj", "Zaveri", 6);

        Comparator<Person> ageComparator = Comparator.comparing(Person::getAge);
        Comparator<Person> lastNameComparator = Comparator.comparing(Person::getLastName);
        Comparator<Person> firstnameComparator = Comparator.comparing(Person::getFirstName);

         SortedSet<Person> children = new TreeSet<>(java.util.Comparator.comparing(Person::getAge).thenComparing(Person::getFirstName).thenComparing(Person::getLastName));

    }
}
