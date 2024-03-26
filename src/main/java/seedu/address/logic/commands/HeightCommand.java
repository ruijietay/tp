package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the height of an existing person in the address book.
 */
public class HeightCommand extends Command {

    public static final String COMMAND_WORD = "height";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from height");
    }
}