package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Shows help information with a list of all available commands.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a list of available commands and their usage.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE =
            "Available commands:\n\n"
                    + "📖 General Commands\n"
                    + "  help                     : Shows this help message\n"
                    + "  exit                     : Exits the program\n"
                    + "  clear                    : Clears all students\n"
                    + "  list                     : Lists all students\n"
                    + "  find KEYWORDS            : Finds students by name\n"
                    + "  delete INDEX             : Deletes a student by index\n"
                    + "  edit INDEX ...           : Edits student details\n"
                    + "  add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]... : Adds a new student\n\n"

                    + "🏷 Attribute Commands\n"
                    + "  tag INDEX attr/KEY=VALUE [attr/KEY=VALUE2]...   : Adds or overrides student attributes\n"
                    + "  deltag INDEX attr/KEY [attr/KEY2]...            : Deletes attributes by key\n"
                    + "  filter attr/KEY=VALUE [attr/KEY2=VALUE2]...     : Filters students by attributes\n\n"

                    + "📚 Lesson Commands\n"
                    + "  assign INDEX l/LESSON_NAME d/DAY t/TIME         : Assigns a lesson to a student\n"
                    + "  done INDEX l/LESSON_NAME                         : Marks lesson as completed\n\n"

                    + "💡 Tip: Use 'help' anytime to redisplay this list.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }
}
