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

package io.logansquarex.core.typeconverters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

/** Implement this interface in order to create a way to custom parse and serialize @JsonFields */
public interface TypeConverter<T> {

    /**
     * Called to parse the current object in the jsonParser to an object of type T
     *
     * @param jsonParser The JsonParser that is pre-configured for this field.
     */
    T parse(JsonParser jsonParser) throws IOException;

    /**
     * Called to serialize an object of type T to JSON using the JsonGenerator and field name.
     *
     * @param object The object to serialize
     * @param fieldName The JSON field name of the object when it is serialized
     * @param writeFieldNameForObject If true, you're responsible for calling jsonGenerator.writeFieldName(fieldName) before writing the field
     * @param jsonGenerator The JsonGenerator object to which the object should be written
     */
    void serialize(T object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException;

}
