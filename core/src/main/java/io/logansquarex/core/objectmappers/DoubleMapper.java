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

package io.logansquarex.core.objectmappers;

import io.logansquarex.core.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * Built-in mapper for Double objects
 */
public class DoubleMapper extends JsonMapper<Double> {

    @Override
    public Double parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getDoubleValue();
        }
    }

    @Override
    public void parseField(Double instance, String fieldName, JsonParser jsonParser) throws IOException { }

    @Override
    public void serialize(Double object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeNumber(object);
    }
}
