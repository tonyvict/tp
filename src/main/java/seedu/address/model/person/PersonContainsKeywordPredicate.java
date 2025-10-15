package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s name, phone, or email contains any of the given keywords.
 * Matching is case-insensitive and allows partial matches.
 */
public class PersonContainsKeywordPredicate implements Predicate<Person> {

    private final List<String> keywords;

    /**
     * Constructs a {@code PersonContainsKeywordPredicate} with the specified list of keywords.
     *
     * @param keywords The list of keywords to match against a person's fields.
     */
    public PersonContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Evaluates this predicate on the given {@code Person}.
     * Returns true if the person's name, phone, or email contains any of the given keywords.
     * Matching is case-insensitive and allows partial matches.
     * <p>
     * If the list of keywords is empty or {@code null}, this method returns true for all persons.
     *
     * @param person The {@code Person} to test.
     * @return true if any field (name, phone, or email) contains one of the keywords, or if there are no keywords.
     */
    @Override
    public boolean test(Person person) {
        if (keywords == null || keywords.isEmpty()) {
            return true;
        }

        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || person.getPhone().value.toLowerCase().contains(keyword.toLowerCase())
                        || person.getEmail().value.toLowerCase().contains(keyword.toLowerCase())
        );
    }

    /**
     * Compares this predicate with another object for equality.
     * Two predicates are equal if they have the same list of keywords.
     *
     * @param other The object to compare to.
     * @return true if the other object is a {@code PersonContainsKeywordPredicate} with the same keywords.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonContainsKeywordPredicate
                && keywords.equals(((PersonContainsKeywordPredicate) other).keywords));
    }
}


