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

public abstract class DoubleBasedTypeConverter<T> implements TypeConverter<T> {

    /**
     * Called to convert a double into an object of type T.
     *
     * @param d The double parsed from JSON.
     */
    public abstract T getFromDouble(double d);

    /**
     * Called to convert a an object of type T into a double.
     *
     * @param object The object being converted.
     */
    public abstract double convertToDouble(T object);

    @Override
    public T parse(JsonParser jsonParser) throws IOException {
        return getFromDouble(jsonParser.getValueAsDouble());
    }

    @Override
    public void serialize(T object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (fieldName != null) {
            jsonGenerator.writeNumberField(fieldName, convertToDouble(object));
        } else {
            jsonGenerator.writeNumber(convertToDouble(object));
        }
    }

}
