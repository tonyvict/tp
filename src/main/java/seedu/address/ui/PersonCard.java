package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 * This card shows all person details including grades, attributes, and lessons.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private VBox detailsPane;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label lessonList;
    @FXML
    private Label attendance;
    @FXML
    private VBox attributes;
    @FXML
    private FlowPane grades;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText(person.getRemark().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        //making lessons into numbered list
        StringBuilder lessonSb = new StringBuilder();
        int lessonNumber = 1;
        for (Lesson lesson : person.getLessonList().getLessons()) {
            lessonSb.append(lessonNumber++).append(". ").append(lesson.toString()).append("\n");
        }
        lessonList.setText(lessonSb.toString().trim());
        //calculating attendance
        long attendedClasses = person.getLessonList().getAttendedLessonCount();
        long totalClasses = person.getLessonList().size();
        attendance.setText("Attendance: " + attendedClasses + " / " + totalClasses);
        // Display attributes
        //gets attributes and sorts them by key
        person.getAttributes().stream()
                .sorted(Comparator.comparing(Attribute::getKey))
                .forEach(attr -> {
                    String valueText = attr.getValues().stream()
                            .sorted()
                            .collect(Collectors.joining(","));
                    String displayText = attr.getKey() + " = " + valueText;
                    Label attributeLabel = new Label(displayText);
                    attributeLabel.setWrapText(true);
                    attributeLabel.setMinWidth(0);
                    attributeLabel.setMaxWidth(Double.MAX_VALUE);
                    attributes.getChildren().add(attributeLabel);
                });

        // Display grades
        person.getGradeList().getGrades().values().stream()
                .sorted(Comparator.comparing(grade -> grade.getSubject() + "/" + grade.getAssessment()))
                .forEach(grade -> {
                    String displayText = grade.getSubject() + "/" + grade.getAssessment() + ": " + grade.getScore();
                    Label gradeLabel = new Label(displayText);
                    gradeLabel.setWrapText(true);
                    gradeLabel.setMinWidth(0);
                    gradeLabel.setMaxWidth(Double.MAX_VALUE);
                    grades.getChildren().add(gradeLabel);
                });
        detailsPane.visibleProperty().bind(person.expandedProperty());
        detailsPane.managedProperty().bind(person.expandedProperty());
    }

}
