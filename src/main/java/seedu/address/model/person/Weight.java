package seedu.address.model.person;

import javafx.util.Pair;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's weight in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Weight extends Attribute<Float> {

    public static final String MESSAGE_CONSTRAINTS = "Weights can only take decimals (float)";

    /**
     * Constructs a {@code weight}.
     *
     * @param weight A weight.
     */
    public Weight(Float weight) {
        super(weight);
        requireNonNull(weight);
    }

    /**
     * Determine if the weight value stored is within the range specified in weightRange.
     * Returns true if specified value is within weightRange.
     *
     * @param weightRange Range of weight to check against.
     *
     * @return True if value is falls within weightRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object weightRange) {
        if (!(weightRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) weightRange;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Float firstVal = (Float) pair.getKey();
        Float secondVal = (Float) pair.getValue();

        assert (secondVal - firstVal >= 0) : "Range should be more than or equals to zero."
                + "Should have been handled in Parser class";

        return (this.getValue() >= firstVal && this.getValue() <= secondVal);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherWeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}