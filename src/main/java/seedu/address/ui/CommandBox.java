package seedu.address.ui;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordPredicate;

/**
 * The UI component that is responsible for receiving user command inputs.
 * Also integrates Quick Search directly into the command box.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int DEBOUNCE_DELAY_MS = 300;

    private final CommandExecutor commandExecutor;
    private final Logic logic;
    private Timer debounceTimer;
    private boolean isExecutingCommand = false;

    @FXML
    private TextField commandTextField;

    /**
     * Backward-compatible constructor (kept to avoid breaking callers).
     * NOTE: This version cannot do live search because it has no Logic reference.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = null; // no live filtering without Logic
        this.debounceTimer = new Timer(true);
        initializeCoreListeners(false);
    }

    /**
     * Preferred constructor with Logic so we can do live filtering + ESC reset.
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = logic;
        this.debounceTimer = new Timer(true);
        initializeCoreListeners(true);
    }

    /**
     * Initialize listeners: style reset, ESC clear, and (optionally) live search.
     */
    private void initializeCoreListeners(boolean enableLiveSearch) {
        // Reset error style whenever text changes
        commandTextField.textProperty().addListener((unused1,
                                                     unused2, unused3) ->
                setStyleToDefault());

        // ESC clears input and resets full list (if Logic available)
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                String currentText = commandTextField.getText();
                // Only reset filter if we're currently in search mode
                if (logic != null && (currentText.startsWith("search")
                        || currentText.startsWith("/search"))) {
                    logic.updateFilteredPersonList(person -> true);
                }
                commandTextField.clear();
            }
        });

        if (!enableLiveSearch || logic == null) {
            return;
        }

        // Live search when input starts with "search" or "/search"
        commandTextField.textProperty().addListener((unused1,
                                                     oldValue, newValue) -> {
            // Skip live search if we're executing a command
            if (isExecutingCommand) {
                return;
            }

            // Debounce updates to avoid spamming the model
            debounceTimer.cancel();
            debounceTimer = new Timer(true);

            debounceTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() ->
                            handleLiveSearchTextChanged(newValue));
                }
            }, DEBOUNCE_DELAY_MS);
        });
    }

    /**
     * Apply or clear live filtering based on the command box text.
     */
    private void handleLiveSearchTextChanged(String text) {
        if (logic == null) {
            return;
        }

        String s = text == null ? "" : text;

        boolean isSearchMode =
                s.startsWith("search") || s.startsWith("/search");

        if (!isSearchMode) {
            // Not in search mode: don't modify existing filters
            return;
        }

        // Extract keywords after the command keyword
        // Accepts "search", "search ", "/search", "/search "
        String withoutCmd = s.startsWith("/search")
                ? s.substring("/search".length())
                : s.substring("search".length());

        String trimmed = withoutCmd.trim();

        if (trimmed.isEmpty()) {
            logic.updateFilteredPersonList(person -> true);
            return;
        }

        Predicate<Person> predicate =
                new PersonContainsKeywordPredicate(Arrays.asList(trimmed.split("\\s+")));
        logic.updateFilteredPersonList(predicate);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        // If user is in "search" mode, don't execute as a normal command.
        // Live filtering is already applied; Enter should not cause "unknown command".
        if (commandText.startsWith("search") || commandText.startsWith("/search")) {
            // Do nothing on Enter (keep the filter active). Users can press ESC to clear.
            return;
        }

        try {
            isExecutingCommand = true; // Set flag to prevent live search interference
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        } finally {
            isExecutingCommand = false; // Always reset flag
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
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
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}

