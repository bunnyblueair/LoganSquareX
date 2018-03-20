package io.logansquarex.demo.model;

import java.util.List;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;

/**
 * Created by bunnyblue on 3/20/18.
 */
@JsonObject
public class HelloList {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<HelloBean> getData() {
        return data;
    }

    public void setData(List<HelloBean> data) {
        this.data = data;
    }

    @JsonField
    private int code=0;
    @JsonField
    private List<HelloBean> data;
}
