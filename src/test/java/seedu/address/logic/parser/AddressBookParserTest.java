package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.messages.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.messages.ClearCommandMessages;
import seedu.address.logic.messages.DeleteCommandMessages;
import seedu.address.logic.messages.EditCommandMessages;
import seedu.address.logic.messages.ExitCommandMessages;
import seedu.address.logic.messages.FindCommandMessages;
import seedu.address.logic.messages.HelpCommandMessages;
import seedu.address.logic.messages.ListCommandMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AlwaysTruePredicate;
import seedu.address.model.person.predicates.CombinedPredicates;
import seedu.address.model.person.predicates.NameContainsSubstringPredicate;
import seedu.address.model.person.predicates.PhoneContainsSubstringPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) this.parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(this.parser.parseCommand(ClearCommandMessages.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(this.parser.parseCommand(ClearCommandMessages.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) this.parser.parseCommand(
                DeleteCommandMessages.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) this.parser.parseCommand(EditCommandMessages.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(this.parser.parseCommand(ExitCommandMessages.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(this.parser.parseCommand(ExitCommandMessages.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) this.parser.parseCommand(
                FindCommandMessages.COMMAND_WORD + " " + PREFIX_NAME + "Alex");

        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate("Alex");
        PhoneContainsSubstringPredicate phonePredicate = new PhoneContainsSubstringPredicate("");
        AlwaysTruePredicate emailPredicate = new AlwaysTruePredicate();
        AlwaysTruePredicate addressPredicate = new AlwaysTruePredicate();
        AlwaysTruePredicate weightPredicate = new AlwaysTruePredicate();
        AlwaysTruePredicate heightPredicate = new AlwaysTruePredicate();
        AlwaysTruePredicate notePredicate = new AlwaysTruePredicate();
        AlwaysTruePredicate tagsPredicate = new AlwaysTruePredicate();
        FindCommand expectedCommand = new FindCommand(new CombinedPredicates(
                namePredicate, phonePredicate, emailPredicate, addressPredicate,
                weightPredicate, heightPredicate, notePredicate, tagsPredicate));
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(this.parser.parseCommand(HelpCommandMessages.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(this.parser.parseCommand(HelpCommandMessages.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(this.parser.parseCommand(ListCommandMessages.COMMAND_WORD) instanceof ListCommand);
        assertTrue(this.parser.parseCommand(ListCommandMessages.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommandMessages.MESSAGE_USAGE), () -> this.parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> this.parser.parseCommand("unknownCommand"));
    }
}
