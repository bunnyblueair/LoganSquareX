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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Declare that a Java object is parsable and serializable.
 * <pre><code>
 * {@literal @}JsonObject
 * public class MyClass {
 *     ...
 * }
 * </code></pre>
 */
@Target(TYPE)
@Retention(CLASS)
public @interface JsonObject {

    public enum FieldDetectionPolicy {
        /** Only parse and serialize fields annotated with @JsonField */
        ANNOTATIONS_ONLY,

        /**
         * Parse and serialize all non-private fields that haven't been
         * annotated with @JsonIgnore.
         */
        NONPRIVATE_FIELDS,

        /**
         * Parse and serialize all non-private fields and accessors that
         * haven't been annotated with @JsonIgnore.
         */
        NONPRIVATE_FIELDS_AND_ACCESSORS
    }

    public enum FieldNamingPolicy {
        /**
         * Use the Java variable's name, unless the 'name' parameter is
         * passed into the @JsonField annotation
         */
        FIELD_NAME,

        /**
         * Use the Java variable's name converted to lower case separated by
         * underscores, unless the 'name' parameter is passed into the @JsonField
         * annotation
         */
        LOWER_CASE_WITH_UNDERSCORES
    }

    /**
     * Allows control over which fields will be used by LoganSquare for parsing and
     * serializing. By default, only fields annotated with @JsonField will be used.
     */
    FieldDetectionPolicy fieldDetectionPolicy() default FieldDetectionPolicy.ANNOTATIONS_ONLY;

    /**
     * Allows control over what field names LoganSquare expects in the JSON when parsing
     * and how the fields are named while serializing. By default, field names match
     * the name of the Java variable unless the 'name' parameter is passed into a
     * field's @JsonField annotation.
     */
    FieldNamingPolicy fieldNamingPolicy() default FieldNamingPolicy.FIELD_NAME;

    /**
     * Allows control over whether or not null fields are serialized. Defaults to false.
     */
    boolean serializeNullObjects() default false;

    /**
     * Allows control over whether or not null collection and array elements are serialized. Defaults to false.
     */
    boolean serializeNullCollectionElements() default false;
}
