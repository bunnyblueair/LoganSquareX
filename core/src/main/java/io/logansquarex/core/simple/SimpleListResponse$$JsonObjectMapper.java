package io.logansquarex.core.simple;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import io.logansquarex.core.JsonMapper;
import io.logansquarex.core.LoganSquare;
import io.logansquarex.core.LoganSquareX;
import io.logansquarex.core.typeconverters.TypeConverter;


@SuppressWarnings("unsafe,unchecked")
public final class SimpleListResponse$$JsonObjectMapper extends JsonMapper<SimpleListResponse> {


  @Override
  public SimpleListResponse parse(JsonParser jsonParser) throws IOException {
    SimpleListResponse instance = new SimpleListResponse();
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
  public void parseField(SimpleListResponse instance, String fieldName, JsonParser jsonParser) throws IOException {

  }


  @Override
  public void serialize(SimpleListResponse object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws IOException {
    throw new IOException("you should not call SimpleListResponse");
//    if (writeStartAndEnd) {
//      jsonGenerator.writeStartObject();
//    }
//    jsonGenerator.writeNumberField("code", object.getCode());
//    if (object.getData() != null) {
//      getjava_util_List_type_converter().serialize(object.getData(), "data", true, jsonGenerator);
//    }
//    if (writeStartAndEnd) {
//      jsonGenerator.writeEndObject();
//    }
  }


  public void serialize(List object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws IOException {
    if (writeStartAndEnd) {
      jsonGenerator.writeStartObject();
    }
    jsonGenerator.writeNumberField("code", 0);
  //  final List<HelloBean> lslocaldata = object.getData();
    if (object != null) {
      jsonGenerator.writeFieldName("data");
      jsonGenerator.writeStartArray();
      for (Object element : object) {
        if (element != null) {
          JsonMapper mapper=  LoganSquareX.mapperFor(element.getClass());
          mapper.serialize(element, jsonGenerator, true);
        }
      }
      jsonGenerator.writeEndArray();
    }
    if (writeStartAndEnd) {
      jsonGenerator.writeEndObject();
    }
  }

//  @Override
//  public String serialize(List<SimpleListResponse> list) throws IOException {
//    return super.serialize(list);
//  }

  public String serialize(List list) throws IOException {
    StringWriter sw = new StringWriter();
    JsonGenerator jsonGenerator = LoganSquare.JSON_FACTORY.createGenerator(sw);
    serialize(list, jsonGenerator,true);
    jsonGenerator.close();
    return sw.toString();
  }
  /**
   * Serialize a list of objects to a JsonGenerator.
   *
   * @param list The list of objects to serialize.
   * @param jsonGenerator The JsonGenerator to which the list should be serialized
   */
  public void serialize(List list, JsonGenerator jsonGenerator) throws IOException {
    jsonGenerator.writeStartArray();
    if (list!=null){
      serialize( list, jsonGenerator, true);
    }else {
      jsonGenerator.writeNull();
    }
    jsonGenerator.writeEndArray();
  }



}
