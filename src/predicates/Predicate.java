package predicates;

/**
 * @author Abhijit.Ghonge
 */
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);

    default Predicate<T> and(Predicate<T> other){
        return var -> test(var) && other.test(var) ;
    }

    default Predicate<T> or(Predicate<T> other){
        return var -> test(var) || other.test(var) ;
    }
}
