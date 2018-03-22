/*
 * Copyright 2018 LoganSquareX
 *
 * Copyright 2015 BlueLine Labs, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.logansquarex.processor;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.processor.processor.JsonObjectHolder;

public class PackageProcessor extends Processor {
    protected XBuildConfig buildPackage = null;

    public PackageProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public Class getAnnotation() {
        return XBuildConfig.class;
    }

    @Override
    public void findAndParseObjects(RoundEnvironment env, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        if (buildPackage!=null){
            return;
        }
        Set<? extends Element> elementsBP = env.getElementsAnnotatedWith(XBuildConfig.class);
        if (elementsBP.size() != 1) {
            System.err.println("XBuildConfig must set only! but  you should check your config" + elementsBP.size());
           // throw new RuntimeException("XBuildConfig must set only! but " + elementsBP.size());
        }
        for (Element element : env.getElementsAnnotatedWith(XBuildConfig.class)) {
            buildPackage = element.getAnnotation(XBuildConfig.class);

        }
    }

    public XBuildConfig getBuildPackage() {
        return buildPackage;
    }
}
