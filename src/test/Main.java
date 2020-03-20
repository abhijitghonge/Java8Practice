package test;


import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String[] names = {"Abhijit", "Mau", "Swayam"};


        Arrays.sort(names, Comparator.comparingInt(String::length));

        logger.info(Arrays.toString(names));


    }
}
