package imdb.model;

import java.util.Objects;

/**
 *
 * @author José Paumard
 */
public class Actor {
    public String lastName, firstName ;
    
    public Actor(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
    
    public String lastName() {
        return this.lastName ;
    }
    
    public String firstName() {
        return this.firstName ;
    }

    
    @Override
    public String toString() {
        return "Actor{" + "lastName=" + lastName + ", firstName=" + firstName + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(lastName, actor.lastName) &&
                Objects.equals(firstName, actor.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName);
    }
}
