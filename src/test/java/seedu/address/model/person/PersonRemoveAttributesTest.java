package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonRemoveAttributesTest {

    @Test
    public void removeAttributesByKey_validKey_removesCorrectly() {
        Person person = new PersonBuilder()
                .withAttributes(new Attribute("subject", "Math"), new Attribute("age", "16"))
                .build();
        Set<String> keysToDelete = Set.of("subject");

        Person edited = person.removeAttributesByKey(keysToDelete);
        Set<Attribute> expected = new HashSet<>();
        expected.add(new Attribute("age", "16"));

        assertEquals(expected, edited.getAttributes());
    }

    @Test
    public void removeAttributesByKey_nonExistingKey_noChange() {
        Person person = new PersonBuilder()
                .withAttributes(new Attribute("subject", "Math"), new Attribute("age", "16"))
                .build();
        Set<String> keysToDelete = Set.of("cca");

        Person edited = person.removeAttributesByKey(keysToDelete);

        assertEquals(person.getAttributes(), edited.getAttributes());
    }

    @Test
    public void removeAttributesByKey_multipleKeys_removesAllSpecified() {
        Person person = new PersonBuilder()
                .withAttributes(new Attribute("subject", "Math"), new Attribute("age", "16"),
                        new Attribute("cca", "Football"))
                .build();
        Set<String> keysToDelete = Set.of("subject", "cca");

        Person edited = person.removeAttributesByKey(keysToDelete);
        Set<Attribute> expected = new HashSet<>();
        expected.add(new Attribute("age", "16"));

        assertEquals(expected, edited.getAttributes());
    }

    @Test
    public void removeAttributesByKey_emptySet_noChange() {
        Person person = new PersonBuilder()
                .withAttributes(new Attribute("subject", "Math"), new Attribute("age", "16"))
                .build();
        Set<String> keysToDelete = Set.of();

        Person edited = person.removeAttributesByKey(keysToDelete);
        assertEquals(person.getAttributes(), edited.getAttributes());
    }
}
