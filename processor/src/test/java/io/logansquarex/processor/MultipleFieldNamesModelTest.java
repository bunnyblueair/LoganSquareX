/*
 * Copyright 2018 LoganSquareX
 *
 * Copyright 2015 BlueLine Labs, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.logansquarex.processor;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import io.logansquarex.processor.processor.JsonAnnotationProcessor;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class MultipleFieldNamesModelTest {

    @Test
    public void generatedSource() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/good/MultipleFieldNamesModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource("generated/MultipleFieldNamesModel$$X.java"));
    }
}
