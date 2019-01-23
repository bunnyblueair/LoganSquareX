package io.logansquarex.demo.model;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.LOMBOK_FIELDS_AND_ACCESSORS)
@Getter
@Setter
public class FriendXX {


    private Long gid;
    private Long gOwnid;
    private Long uid;
    private Short state;
    private Long optAt;
    private String message;
    private int id;

    public FriendXX() {


    }

    private String name;

}
