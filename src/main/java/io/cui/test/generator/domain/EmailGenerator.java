package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.fixedValues;

import io.cui.test.generator.TypedGenerator;

/**
 * Creates syntactically valid email-addresses
 *
 * @author Oliver Wolff
 *
 */
public class EmailGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
    private final TypedGenerator<String> familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();

    private static final TypedGenerator<String> TLDS = fixedValues("de", "org", "com", "net");
    private static final TypedGenerator<String> DOMAINS =
            fixedValues("email", "mail", "icw", "message", "example", "hospital");

    @Override
    public String next() {
        return createEmail(firstNames.next(), familyNames.next());
    }

    /**
     * @param firstname
     * @param lastname
     * @return an email address created in the form of
     *         firstname.lastname@|email|mail|icw.de|org|com|net
     */
    public static String createEmail(final String firstname, final String lastname) {
        return new StringBuilder().append(firstname.toLowerCase()).append('.').append(lastname.toLowerCase())
                .append('@').append(DOMAINS.next())
                .append('.').append(TLDS.next()).toString();
    }

}