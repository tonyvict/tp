package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 * (No longer contains live search; search is now a standard command.)
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        initializeListeners();
    }

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor} and {@code Logic}.
     * Logic reference is retained for potential future features (e.g. autocomplete).
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        initializeListeners();
    }

    /**
     * Initializes key and style listeners.
     */
    private void initializeListeners() {
        // Remove error style when user starts typing again
        commandTextField.textProperty().addListener((unused1,
                                                     unused2, unused3) -> setStyleToDefault());

        // Allow ESC key to clear the command box
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                commandTextField.clear();
            }
        });
    }

    /**
     * Handles the Enter key press to execute a command.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText().trim();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            CommandResult commandResult = commandExecutor.execute(commandText);
            setStyleToDefault();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Resets command box style to default.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}

