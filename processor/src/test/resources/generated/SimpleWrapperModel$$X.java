package io.logansquarex.processor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.logansquarex.core.JsonMapper;
import io.logansquarex.core.LoganSquareX;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unsafe,unchecked")
public final class SimpleWrapperModel$$X extends JsonMapper<SimpleWrapperModel> {
    private static final JsonMapper<SimpleWrapperModel.WrappedClass> IO_LOGANSQUAREX_PROCESSOR_SIMPLEWRAPPERMODEL_WRAPPEDCLASS__X = LoganSquareX.mapperFor(SimpleWrapperModel.WrappedClass.class);

    @Override
    public SimpleWrapperModel parse(JsonParser jsonParser) throws IOException {
        SimpleWrapperModel instance = new SimpleWrapperModel();
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
    public void parseField(SimpleWrapperModel instance, String fieldName, JsonParser jsonParser) throws IOException {
        if ("wrappedObject".equals(fieldName)) {
            instance.wrappedObject = IO_LOGANSQUAREX_PROCESSOR_SIMPLEWRAPPERMODEL_WRAPPEDCLASS__X.parse(jsonParser);
        }
    }

    @Override
    public void serialize(SimpleWrapperModel object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws IOException {
        if (writeStartAndEnd) {
            jsonGenerator.writeStartObject();
        }
        if (object.wrappedObject != null) {
            jsonGenerator.writeFieldName("wrappedObject");
            IO_LOGANSQUAREX_PROCESSOR_SIMPLEWRAPPERMODEL_WRAPPEDCLASS__X.serialize(object.wrappedObject, jsonGenerator, true);
        }
        if (writeStartAndEnd) {
            jsonGenerator.writeEndObject();
        }
    }
}
