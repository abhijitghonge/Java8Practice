package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Abhijit.Ghonge
 */
public class Shakespeare {

    public static void main(String[] args) throws IOException {
        // http://introcs.cs.princeton.edu/java/data/words.shakespeare.txt
        // http://introcs.cs.princeton.edu/java/data/ospd.txt

        Set<String> shakespeareWords =
                Files.lines(Paths.get("files/words.shakespeare.txt"))
                        .map(word -> word.toLowerCase())
                        .collect(Collectors.toSet());

        Set<String> scrabbleWords =
                Files.lines(Paths.get("files/ospd.txt"))
                        .map(word -> word.toLowerCase())
                        .collect(Collectors.toSet());

        System.out.println("# words of Shakespeare : " + shakespeareWords.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());

        final int[] scrabbleENScore = {
                // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y,  z
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

        Function<String, Integer> score = word -> word
                .toLowerCase()
                .chars()
                .map(letter -> scrabbleENScore[letter - 'a'])
                .sum();
        //Historgram of scrabble words
        Map<Integer, List<String>> histogramOfWordsByScore = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .collect(groupingBy(score));

        System.out.println("# histogram of words by score:" + histogramOfWordsByScore.size());

        histogramOfWordsByScore.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> -entry.getKey()))
                .limit(3)
                .forEach(System.out::println);

        int[] scrabbleENDistribution = {
                // a, b, c, d,  e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
                9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        Function<String, Map<Integer, Long>> histogramWord =
                word -> word.chars().boxed()
                        .collect(groupingBy(letter -> letter, counting()));

        Function<String, Long> numberOfBlanksRequired =
                word -> histogramWord.apply(word)
                        .entrySet().stream()
                        .mapToLong(
                                entry -> Long.max(entry.getValue() - scrabbleENDistribution[entry.getKey() - 'a'], 0L)
                        )
                        .sum();

        System.out.println(" # Number of blanks required: " + numberOfBlanksRequired.apply("whizzing"));

        Function<String, Integer> scoreAdjustedForBlanks =
                word -> histogramWord.apply(word)
                        .entrySet()
                        .stream()
                        .mapToInt(
                                entry -> scrabbleENScore[entry.getKey() - 'a'] *
                                        Integer.min(entry.getValue().intValue(),
                                                scrabbleENDistribution[entry.getKey() - 'a']
                                        )

                        )
                        .sum();
        System.out.println(" # Score adjusted for blanks in whizzing:" + scoreAdjustedForBlanks.apply("whizzing"));

        //histogram of scores adjusted for blanks
        Map<Integer, List<String>> histogramOfWordsByScoreAdjustedForBlanks = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .collect(groupingBy(scoreAdjustedForBlanks));

        histogramOfWordsByScoreAdjustedForBlanks.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> -entry.getKey()))
                .limit(3)
                .forEach(System.out::println);


        ToIntFunction<String> intScore = word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        System.out.println("Score:" + intScore.applyAsInt("hello"));

        Optional<String> maxScore = shakespeareWords.stream()
                .filter(word -> scrabbleWords.contains(word))
                .max(Comparator.comparing(score));
        maxScore.ifPresent(System.out::println);

        IntSummaryStatistics intSummaryStatistics = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .mapToInt(intScore)
                .summaryStatistics();

        System.out.println("Stats: " + intSummaryStatistics);
    }
}
