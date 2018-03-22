package io.logansquarex.processor.bad;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.OnJsonParseComplete;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
public class MethodWithoutObjectModel {


    @OnJsonParseComplete
    public void onParseComplete() {

    }

}
