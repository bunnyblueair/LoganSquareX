package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;

@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class SimpleWrapperModel {

    @JsonField
    public WrappedClass wrappedObject;

    @JsonObject
    public static class WrappedClass {

        @JsonField
        public String wrappedString;
    }

}
