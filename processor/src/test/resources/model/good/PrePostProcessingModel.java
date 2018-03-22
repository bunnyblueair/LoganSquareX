package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.annotation.OnJsonParseComplete;
import io.logansquarex.core.annotation.OnPreJsonSerialize;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class PrePostProcessingModel {

    @JsonField(name = "unformatted_string")
    public String unformattedString;

    public String formattedString;

    @OnPreJsonSerialize
    public void onPreSerialize() {
        unformattedString = formattedString.toLowerCase();
    }

    @OnJsonParseComplete
    public void onParseComplete() {
        formattedString = formattedString.toUpperCase();
    }
}
