package seedu.address.ui;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.address.logic.Logic;
import seedu.address.model.person.PersonContainsKeywordPredicate;

/**
 * A UI component that provides a live search bar for filtering persons in the list.
 * <p>
 * The search matches name, phone, and email fields. Matching is case-insensitive and partial.
 */
public class QuickSearchBox extends UiPart<TextField> {

    private static final String FXML = "QuickSearchBox.fxml";
    private static final int DEBOUNCE_DELAY_MS = 300;

    private final Logic logic;
    private Timer debounceTimer;

    @FXML
    private TextField quickSearchField;

    /**
     * Constructs a {@code QuickSearchBox} that listens to user input
     * and updates the filtered person list in the model.
     *
     * @param logic The {@code Model} used to update the filtered person list.
     */
    public QuickSearchBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        debounceTimer = new Timer(true);
        initializeListener();
    }

    /**
     * Initializes the listener for the search field to apply debounce filtering.
     */
    private void initializeListener() {
        quickSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Cancel any pending tasks
            debounceTimer.cancel();
            debounceTimer = new Timer(true);

            // Schedule a new filter task
            debounceTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> applyFilter(newValue));
                }
            }, DEBOUNCE_DELAY_MS);
        });
    }

    /**
     * Applies the filtering logic based on the provided input string.
     *
     * @param input The current text in the search field.
     */
    private void applyFilter(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            // Reset filter to show all persons
            logic.updateFilteredPersonList(person -> true);
        } else {
            logic.updateFilteredPersonList(new PersonContainsKeywordPredicate(Arrays.asList(trimmed.split("\\s+"))));
        }
    }
}

