/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.GeneratorException;
import io.cui.test.generator.internal.net.java.quickcheck.MockFactory;
import io.cui.test.generator.internal.net.java.quickcheck.StatefulGenerator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;

class UniqueValuesGeneratorTest {

    @Test
    void testStopGenerationOfValuesAfterMaxTries() {
        int tries = 100;
        Generator<Boolean> generator = MockFactory.createBooleanMock();
        expect(generator.next()).andReturn(true);
        expectLastCall().times(tries + 1);
        replay(generator);
        StatefulGenerator<Boolean> unique = uniqueValuesGenerator(tries, generator);
        assertTrue(unique.next());
        assertThrows(GeneratorException.class, unique::next);
        verify(generator);
    }

    @Test
    void testGenerationOfUniqueValuesOnly() {
        int tries = 3;
        Generator<Boolean> generator = MockFactory.createBooleanMock();
        expect(generator.next()).andReturn(true).times(tries - 1);
        expect(generator.next()).andReturn(false);
        replay(generator);

        StatefulGenerator<Boolean> unique = uniqueValuesGenerator(tries, generator);
        assertTrue(unique.next());
        assertFalse(unique.next());
        verify(generator);
    }

    @Test
    void testDefaultValueForMaxTries() {
        Generator<Boolean> generator = MockFactory.createBooleanMock();
        expect(generator.next()).andReturn(true);
        expectLastCall().times(CombinedGenerators.DEFAULT_MAX_TRIES + 1);
        replay(generator);

        StatefulGenerator<Boolean> unique = uniqueValuesGenerator(generator);
        assertEquals(true, unique.next());
        assertThrows(GeneratorException.class, unique::next);
        verify(generator);
    }

    @Test
    void testResetOfGenerator() {
        int tries = 1;
        Generator<Boolean> generator = MockFactory.createBooleanMock();
        expect(generator.next()).andReturn(true);
        expectLastCall().times(1 + tries + 1);
        replay(generator);

        StatefulGenerator<Boolean> unique = uniqueValuesGenerator(tries, generator);
        unique.next();
        assertThrows(GeneratorException.class, unique::next);
        unique.reset();
        unique.next();
        verify(generator);
    }

    StatefulGenerator<Boolean> uniqueValuesGenerator(int tries, Generator<Boolean> generator) {
        return CombinedGenerators.uniqueValues(generator, tries);
    }

    StatefulGenerator<Boolean> uniqueValuesGenerator(Generator<Boolean> generator) {
        return CombinedGenerators.uniqueValues(generator);
    }
}