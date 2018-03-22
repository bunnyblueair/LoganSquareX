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

import io.logansquarex.processor.processor.JsonObjectHolder;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class Processor {

    protected ProcessingEnvironment mProcessingEnv;

    protected Processor(ProcessingEnvironment processingEnv) {
        mProcessingEnv = processingEnv;
    }

    public abstract Class getAnnotation();
    public abstract void findAndParseObjects(RoundEnvironment env, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types);

    public static List<Processor> allProcessors(ProcessingEnvironment processingEnvironment) {
        List<Processor> list = new ArrayList<>();
        list.add(new PackageProcessor(processingEnvironment));
        list.add(new PackageMergeProcessor(processingEnvironment));
        list.add(new JsonObjectProcessor(processingEnvironment));
        list.add(new OnJsonParseCompleteProcessor(processingEnvironment));
        list.add(new OnPreSerializeProcessor(processingEnvironment));
        list.add(new JsonFieldProcessor(processingEnvironment));
        return list;
    }

    public void error(Element element, String message, Object... args) {
        mProcessingEnv.getMessager().printMessage(ERROR, String.format(message, args), element);
    }
}
