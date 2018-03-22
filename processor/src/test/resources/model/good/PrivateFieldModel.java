package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;

import java.util.List;
import java.util.Map;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class PrivateFieldModel {

    @JsonField
    private String privateString;

    @JsonField(name = "private_named_string")
    private String privateNamedString;

    @JsonField
    private boolean privateBoolean;

    @JsonField
    private List<String> privateList;

    @JsonField
    private Map<String, String> privateMap;

    @JsonField(name = "string_to_test_m_vars")
    private String mStringThatStartsWithM;

    public String getPrivateString() {
        return privateString;
    }

    public void setPrivateString(String privateString) {
        this.privateString = privateString;
    }

    public String getPrivateNamedString() {
        return privateNamedString;
    }

    public void setPrivateNamedString(String privateNamedString) {
        this.privateNamedString = privateNamedString;
    }

    public boolean isPrivateBoolean() {
        return privateBoolean;
    }

    public void setPrivateBoolean(boolean privateBoolean) {
        this.privateBoolean = privateBoolean;
    }

    public List<String> getPrivateList() {
        return privateList;
    }

    public void setPrivateList(List<String> privateList) {
        this.privateList = privateList;
    }

    public Map<String, String> getPrivateMap() {
        return privateMap;
    }

    public void setPrivateMap(Map<String, String> privateMap) {
        this.privateMap = privateMap;
    }

    public String getStringThatStartsWithM() {
        return mStringThatStartsWithM;
    }

    public void setStringThatStartsWithM(String stringThatStartsWithM) {
        mStringThatStartsWithM = stringThatStartsWithM;
    }
}
