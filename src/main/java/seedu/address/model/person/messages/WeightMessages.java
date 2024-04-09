package seedu.address.model.person.messages;

public class WeightMessages {

    public static final String MESSAGE_CONSTRAINTS = "Weight value can only be a positive number.";
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";
    public static final String MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    public static final String MESSAGE_EMPTY_WEIGHT_MAP = "There are no more weight values to be removed. "
            + "This client has no more weight values associated with them.";
}
