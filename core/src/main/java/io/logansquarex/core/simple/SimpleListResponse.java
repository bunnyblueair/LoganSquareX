package io.logansquarex.core.simple;

import java.util.List;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.objectmappers.ListMapper;

/**
 * Created by bunnyblue on 3/20/18.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)

public class SimpleListResponse  {
    @JsonField
    private  int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    @JsonField

    private List data;
}
