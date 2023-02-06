package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.integers;

import io.cui.test.generator.TypedGenerator;

/**
 * Generator for some German Streets with Housenumber.
 *
 * @author Oliver Wolff
 *
 */
public class StreetGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> streets = new StreetNameGenerator();
    private final TypedGenerator<Integer> number = integers(1, 111);

    @Override
    public String next() {
        return streets.next() + " " + number.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}