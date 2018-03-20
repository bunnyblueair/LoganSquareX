package io.logansquarex.processor.model;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonIgnore;
import io.logansquarex.core.annotation.JsonObject;

// Note: intentionally not annotated so we can test loading external JsonObjectMappers
@JsonObject
public class ExternalModelTest {
@JsonField
    public String string;

}
