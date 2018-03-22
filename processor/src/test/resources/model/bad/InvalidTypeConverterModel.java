package io.logansquarex.processor.bad;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class InvalidTypeConverterModel {

    @JsonField(typeConverter = InvalidTypeConverter.class)
    public UnsupportedType unsupportedType;

    public static class UnsupportedType { }

    public static class InvalidTypeConverter {

    }
}
