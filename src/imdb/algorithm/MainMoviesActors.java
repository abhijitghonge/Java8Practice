package imdb.algorithm;


import imdb.model.Actor;
import imdb.model.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Abhijit.Ghonge
 */
public class MainMoviesActors {

    public static void main(String... args) throws IOException {

        Set<Movie> movies = new HashSet<>();

        try (Stream<String> lines = Files.lines(
                Paths.get("files", "movies-mpaa.txt")
        )) {

            lines.forEach(
                    (String line) -> {
                        String[] elements = line.split("/");
                        String title =
                                elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
                        String releaseYear =
                                elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));

                        if (releaseYear.contains(",")) {
                            // with skip movies with a coma in their title
                            return;
                        }

                        Movie movie = new Movie(title, Integer.valueOf(releaseYear));

                        for (int i = 1; i < elements.length; i++) {
                            String[] name = elements[i].split(", ");
                            String lastName = name[0].trim();
                            String firstName = "";
                            if (name.length > 1) {
                                firstName = name[1].trim();
                            }

                            Actor actor = new Actor(lastName, firstName);
                            movie.addActor(actor);
                        }

                        movies.add(movie);
                    }
            );
        }

        System.out.println("# movies = " + movies.size());

        long numberOfActors = movies.stream()
                .flatMap(movie -> movie.actors().stream())
                .distinct()
                .count();

        System.out.println("# of actors: " + numberOfActors);

        //# of actors that played most movies

        Map.Entry<Actor, Long> mostViewedActor = movies.stream()
                .flatMap(movie -> movie.actors().stream())
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(()-> new RuntimeException("No such actor exists!"));

        System.out.println("Most viewed actor:" + mostViewedActor);

        //Actor that played in most movies in a year
        //Create <release year, Map<Actor, # of movies during that year>>
        //Map<Integer, HashMap<Actor, AtomicLong>> countActorsInMoviesByYearMap =
        Map.Entry<Integer, Map.Entry<Actor, AtomicLong>> mostViewedActorInYear = movies.stream()
                .collect(groupingBy(Movie::releaseYear,
                        Collector.of(
                                () -> new HashMap<Actor, AtomicLong>(),
                                (actorInMoviesMap, movie) -> {
                                    movie.actors().forEach(
                                            actor -> actorInMoviesMap.computeIfAbsent(actor, aActor -> new AtomicLong()).incrementAndGet());
                                },
                                (actorInMoviesMap1, actorInMoviesMap2) -> {
                                    actorInMoviesMap2.forEach((key, value) -> actorInMoviesMap1.merge(
                                            key, value,
                                            (aCount1, aCount2) -> {
                                                aCount1.addAndGet(aCount2.get());
                                                return aCount1;
                                            }
                                    ));

                                    return actorInMoviesMap2;
                                },
                                IDENTITY_FINISH
                        )
                        )
                )//Map<Integer, HashMap<Actor, AtomicLong>>
                .entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().entrySet().stream()
                                        .max(Map.Entry
                                                .comparingByValue(comparing(AtomicLong::get)))
                                        .orElseThrow(()->new RuntimeException("No such Movie exists"))
                        )
                )
                //Map<Integer, Map.Entry<Actor, AtomicLong>>
                .entrySet().stream()
                .max(
                        Map.Entry.comparingByValue(
                                comparing(
                                        entry -> entry.getValue().get()
                                )
                        )
                )
                .orElseThrow(()-> new RuntimeException("No such Actor exist!!"));

        System.out.println("Most seen actor in a given year:"+mostViewedActorInYear);

    }
}
