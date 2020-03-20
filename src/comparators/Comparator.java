package comparators;


import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {
    static <T> Comparator<T> comparing(Function<T, Comparable> f) {
        return (p1,p2)-> f.apply(p1).compareTo(f.apply(p2));
    }

    int compare(T t1, T t2);

    default Comparator<T> thenComparing(Function<T, Comparable> f){
        return (p1,p2)-> compare(p1,p2) == 0? comparing(f).compare(p1,p2): compare(p1,p2);
    }
}
