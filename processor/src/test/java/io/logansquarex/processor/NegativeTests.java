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

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.processor.processor.JsonAnnotationProcessor;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
public class NegativeTests {

    @Test
    public void fieldWithoutObject() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/FieldWithoutObjectModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonField fields can only be in classes annotated with @JsonObject.");
    }

    @Test
    public void privateField() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/PrivateFieldModelWithoutAccessors.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonField annotation can only be used on private fields if both getter and setter are present.");
    }

    @Test
    public void invalidTypeConverter() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/InvalidTypeConverterModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("TypeConverter elements must implement the TypeConverter interface or extend from one of the convenience helpers");
    }

    @Test
    public void methodObject() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/MethodWithoutObjectModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("@OnJsonParseComplete methods can only be in classes annotated with @JsonObject.");
    }

    @Test
    public void methodWithArgs() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/MethodWithArgsModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("@OnJsonParseComplete methods must not take any parameters.");
    }

    @Test
    public void multipleMethods() {
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("model/bad/MultipleMethodsModel.java"))
                .processedWith(new JsonAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("There can only be one @OnJsonParseComplete method per class");
    }
}
