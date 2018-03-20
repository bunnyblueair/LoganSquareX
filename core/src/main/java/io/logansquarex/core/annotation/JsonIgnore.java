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
 * Declare that a variable should NOT be parsed/serialized when using a fieldDetectionPolicy other than
 * ANNOTATIONS_ONLY.
 * <pre><code>
 * {@literal @}JsonIgnore
 * public String variableIDontWantParsedOrSerialized;
 * </code></pre>
 */
@Target(FIELD)
@Retention(CLASS)
public @interface JsonIgnore {

    public enum IgnorePolicy {
        /** This field will be ignored for both parsing and serializing. */
        PARSE_AND_SERIALIZE,

        /** This field will be ignored for parsing, but will still be serialized. */
        PARSE_ONLY,

        /** This field will be ignored for serializing, but will still be parsed. */
        SERIALIZE_ONLY
    }

    /**
     * Allows control over whether a field should be parsed and/or serialized or not
     */
    IgnorePolicy ignorePolicy() default IgnorePolicy.PARSE_AND_SERIALIZE;
}
