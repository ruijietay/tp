package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithoutEmail;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.NoteCommandMessages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class NoteCommandTest {

    private static final String NOTE_STUB = "Some note";
    private static final String EMPTY_NOTE = "";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());

    @Test
    public void execute_addNoteUnfilteredList_success() {
        Person firstPerson = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().getValue()));

        String expectedMessage = String.format(NoteCommandMessages.MESSAGE_ADD_NOTE_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(noteCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNoteUnfilteredList_success() {
        Person firstPerson = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote(EMPTY_NOTE).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().getValue()));

        String expectedMessage = String.format(NoteCommandMessages.MESSAGE_DELETE_NOTE_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(noteCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNoteUnfilteredListWithoutEmail_success() {
        Person firstPerson = this.modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().getValue()));

        String expectedMessage = String.format(NoteCommandMessages.MESSAGE_ADD_NOTE_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.modelWithoutEmail.getAddressBook()),
                new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(noteCommand, this.modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(this.model.getFilteredPersonList().size() + 1);
        NoteCommand noteCommand = new NoteCommand(invalidIndex, new Note(NOTE_STUB));
        assertCommandFailure(noteCommand, this.model, String.format(Messages.MESSAGE_INVALID_INDEX,
                NoteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void equals() {
        NoteCommand firstNote = new NoteCommand(INDEX_FIRST_PERSON, new Note(NOTE_STUB));
        NoteCommand secondNote = new NoteCommand(INDEX_SECOND_PERSON, new Note(NOTE_STUB));
        NoteCommand firstNoteClone = new NoteCommand(INDEX_FIRST_PERSON, new Note(NOTE_STUB));

        // same object => return true
        assertEquals(firstNote, firstNote);

        // same note details => return true
        assertEquals(firstNote, firstNoteClone);

        // same note, different person => return false
        assertNotEquals(firstNote, secondNote);

        // note not equal to number => return false
        assertNotEquals(firstNote, 10);
    }
}
