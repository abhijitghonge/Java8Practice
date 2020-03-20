package predicates;

import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author Abhijit.Ghonge
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        Predicate<String> p1 = s-> s.length() < 20;
        Predicate<String> p2 = s-> s.length() > 5;
        Predicate<String> p3 = s-> s.equals("Swayam Abhijit Ghonge");

        Predicate<String> p4 = p1.and(p2).or(p3);

        boolean result = p4.test("Swayam Abhijit Ghonge");

        logger.info("Result:"+ result);
    }
}
