package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class MultipleFieldNamesModel {

    @JsonField(name = {"possible_int_name_1", "possible_int_name_2"})
    public int testInt;

    @JsonField(name = {"possible_long_name_1", "possible_long_name_2"})
    public long testLong;

    @JsonField(name = {"possible_float_name_1", "possible_float_name_2"})
    public float testFloat;

    @JsonField(name = {"possible_double_name_1", "possible_double_name_2"})
    public double testDouble;

    @JsonField(name = {"possible_string_name_1", "possible_string_name_2"})
    public String testString;

}
