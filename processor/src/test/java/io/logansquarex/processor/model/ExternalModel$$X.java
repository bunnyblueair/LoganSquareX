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

package io.logansquarex.processor.model;

import io.logansquarex.core.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unsafe,unchecked")
public final class ExternalModel$$X extends JsonMapper<ExternalModelTest> {
  @Override
  public ExternalModelTest parse(JsonParser jsonParser) throws IOException {
    ExternalModelTest instance = new ExternalModelTest();
    if (jsonParser.getCurrentToken() == null) {
      jsonParser.nextToken();
    }
    if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
      jsonParser.skipChildren();
      return null;
    }
    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
      String fieldName = jsonParser.getCurrentName();
      jsonParser.nextToken();
      parseField(instance, fieldName, jsonParser);
      jsonParser.skipChildren();
    }
    return instance;
  }

  @Override
  public void parseField(ExternalModelTest instance, String fieldName, JsonParser jsonParser) throws IOException {
    if ("string".equals(fieldName)) {
      instance.string = jsonParser.getValueAsString(null);
    }
  }

  @Override
  public void serialize(ExternalModelTest object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws IOException {
    if (writeStartAndEnd) {
      jsonGenerator.writeStartObject();
    }
    if (object.string != null) {
      jsonGenerator.writeStringField("string", object.string);
    } else {
      jsonGenerator.writeFieldName("string");
      jsonGenerator.writeNull();
    }
    if (writeStartAndEnd) {
      jsonGenerator.writeEndObject();
    }
  }
}
