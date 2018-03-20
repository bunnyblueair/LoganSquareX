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

import io.logansquarex.core.annotation.JsonObject;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;

public abstract class MethodProcessor extends Processor {

    protected MethodProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public boolean isCallbackMethodAnnotationValid(Element element, String annotationName) {
        TypeElement enclosingElement = (TypeElement)element.getEnclosingElement();

        if (enclosingElement.getAnnotation(JsonObject.class) == null) {
            error(enclosingElement, "%s: @%s methods can only be in classes annotated with @%s.", enclosingElement.getQualifiedName(), annotationName, JsonObject.class.getSimpleName());
            return false;
        }

        ExecutableElement executableElement = (ExecutableElement)element;
        if (executableElement.getParameters().size() > 0) {
            error(element, "%s: @%s methods must not take any parameters.", enclosingElement.getQualifiedName(), annotationName);
            return false;
        }

        List<? extends Element> allElements = enclosingElement.getEnclosedElements();
        int methodInstances = 0;
        for (Element enclosedElement : allElements) {
            for (AnnotationMirror am : enclosedElement.getAnnotationMirrors()) {
                if (am.getAnnotationType().asElement().getSimpleName().toString().equals(annotationName)) {
                    methodInstances++;
                }
            }
        }
        if (methodInstances != 1) {
            error(element, "%s: There can only be one @%s method per class.", enclosingElement.getQualifiedName(), annotationName);
            return false;
        }

        return true;
    }

}
