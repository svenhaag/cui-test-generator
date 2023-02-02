/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import java.util.Objects;

import io.cui.test.generator.internal.net.java.quickcheck.ObjectGenerator;

public class ObjectGeneratorImpl<T> implements ObjectGenerator<T> {

    final ObjectDefinition<T> definition;
    private final ObjectFactory<T> factory;

    public ObjectGeneratorImpl(Class<T> objectType) {
        Objects.requireNonNull(objectType, "objectType");
        this.definition = new ObjectDefinition<>(objectType);
        this.factory = new ObjectFactory<>(definition);
    }

    @Override
    public T getRecorder() {
        return definition.getRecorder();
    }

    @Override
    public <R> ReturnValue<R> on(R obj) {
        return definition.onMethod(obj);
    }

    @Override
    public T next() {
        return factory.newValue();
    }
}
