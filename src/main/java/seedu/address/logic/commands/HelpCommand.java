package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Shows help information with a list of all available commands.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a list of available commands and their usage.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = String.join("\n",
            "ClassRosterPro — Command List",
            "",
            "GENERAL COMMANDS:",
            " - help (Shows this help message)",
            " - list (Lists all students)",
            " - clear (Clears all students)",
            " - exit (Exits the program)",
            "",
            "STUDENT COMMANDS:",
            " - add n/<NAME> p/<PHONE> e/<EMAIL> a/<ADDRESS> [t/TAG]...",
            " - edit <INDEX> [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...",
            " - delete <INDEX>",
            " - search <KEYWORD>",
            "",
            "ATTRIBUTE COMMANDS:",
            " - addattr <INDEX> attr/<KEY>=<VALUE>[,<VALUE2>]... [attr/<KEY2>=<VALUE2>]...",
            " - delattr <INDEX> attr/<KEY> [attr/<KEY2>]...",
            " - filter attr/<KEY>=<VALUE>[,<VALUE2>]... [attr/<KEY2>=<VALUE2>]...",
            "",
            "LESSON & ATTENDANCE COMMANDS:",
            " - schedule <INDEX> start/<START_TIME> end/<END_TIME> date/<DATE> sub/<SUBJECT>",
            " - unschedule <INDEX> lesson/<LESSON_INDEX>",
            " - mark <INDEX> lesson/<LESSON_INDEX>",
            " - unmark <INDEX> lesson/<LESSON_INDEX>",
            "",
            "GRADE COMMANDS:",
            " - grade <INDEX> sub/<SUBJECT>/<ASSESSMENT>/<SCORE> [sub/<SUBJECT2>/<ASSESSMENT2>/<SCORE2>]...",
            " - delgrade <INDEX> sub/<SUBJECT>/<ASSESSMENT> [sub/<SUBJECT2>/<ASSESSMENT2>]...",
            "",
            "OTHER STUDENT COMMANDS:",
            " - remark <INDEX> r/<REMARK1> [r/<REMARK2>]...",
            " - open <INDEX>",
            " - close <INDEX>",
            "",
            "------------------------------------------",
            "For full usage details, see the User Guide:",
            "https://ay2526s1-cs2103t-w13-4.github.io/tp/UserGuide.html"
    );

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Help window opened", true, false);
    }
}
