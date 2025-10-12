package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the lesson of an existing person in the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the lesson of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing lesson will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "start/ [START TIME] end/ [END TIME} "
            + "date/ [DATE] sub/ [SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "start/ 09:30 end/ 11:30 "
            + "date/ 2025-09-20 sub/ Maths";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from schedule");
    }
}
