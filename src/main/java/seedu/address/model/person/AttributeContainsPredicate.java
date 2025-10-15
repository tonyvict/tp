package seedu.address.model.person;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s attributes match the specified attribute filters.
 * All specified attribute filters must match for the person to be included.
 */
public class AttributeContainsPredicate implements Predicate<Person> {
    private final Map<String, Set<String>> attributeFilters;

    public AttributeContainsPredicate(Map<String, Set<String>> attributeFilters) {
        this.attributeFilters = attributeFilters;
    }

    @Override
    public boolean test(Person person) {
        // If no filters specified, show all persons
        if (attributeFilters.isEmpty()) {
            return true;
        }

        // Check if person has all the required attributes with matching values
        for (Map.Entry<String, Set<String>> filter : attributeFilters.entrySet()) {
            String filterKey = filter.getKey();
            Set<String> filterValues = filter.getValue();

            // Find matching attribute in person
            boolean hasMatchingAttribute = person.getAttributes().stream()
                    .anyMatch(attr -> attr.key.equals(filterKey)
                        && filterValues.stream().anyMatch(filterValue -> attr.containsValue(filterValue)));

            if (!hasMatchingAttribute) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttributeContainsPredicate)) {
            return false;
        }

        AttributeContainsPredicate otherAttributeContainsPredicate = (AttributeContainsPredicate) other;
        return attributeFilters.equals(otherAttributeContainsPredicate.attributeFilters);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("attributeFilters", attributeFilters).toString();
    }
}
