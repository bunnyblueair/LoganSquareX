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
import io.logansquarex.core.LoganSquare;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.util.Map;

/**
 * Built-in mapper for Map objects of a unknown value types
 */
public class MapMapper extends JsonMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> parse(JsonParser jsonParser) throws IOException {
        return LoganSquare.mapperFor(Object.class).parseMap(jsonParser);
    }

    @Override
    public void parseField(Map<String, Object> instance, String fieldName, JsonParser jsonParser) throws IOException { }

    @Override
    public void serialize(Map<String, Object> map, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        LoganSquare.mapperFor(Object.class).serialize(map, generator);
    }

}
