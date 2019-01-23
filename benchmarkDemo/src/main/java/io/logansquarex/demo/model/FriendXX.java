package io.logansquarex.demo.model;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import lombok.Getter;
import lombok.Setter;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.LOMBOK_FIELDS_AND_ACCESSORS)
@Getter
@Setter
public class FriendXX {


    private int id;

    public FriendXX() {

    }

    private String name;

}
