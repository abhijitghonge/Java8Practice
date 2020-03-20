package streams;

import java.util.Optional;

/**
 * @author Abhijit.Ghonge
 */
public class NewMath {
    public static Optional<Double> sqrt(Double d) {
        return d > 0 ? Optional.of(Math.sqrt(d)) : Optional.empty();
    }

    public static Optional<Double> inv(Double d) {
        return d > 0 ? Optional.of(1 / d) : Optional.empty();
    }
}
