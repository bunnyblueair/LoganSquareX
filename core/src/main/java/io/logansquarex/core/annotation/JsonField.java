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

package io.logansquarex.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Declare that a variable should be parsed/serialized.
 * <pre><code>
 * {@literal @}JsonField(name = "random_variable_name")
 * public String randomVariableName;
 * </code></pre>
 */
@Target(FIELD)
@Retention(CLASS)
public @interface JsonField {

    /**
     * The name(s) of this field in JSON. Use an array if this could be represented by multiple names.
     * Note that using this field will override the enclosing JsonObject's fieldNamingPolicy.
     */
    String[] name() default {};

    /** The TypeConverter that will be used to parse/serialize this variable. */
    Class typeConverter() default void.class;

}
