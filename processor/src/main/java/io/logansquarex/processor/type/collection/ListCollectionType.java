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

package io.logansquarex.processor.type.collection;

import com.squareup.javapoet.ClassName;

import java.util.List;

public abstract class ListCollectionType extends SingleParameterCollectionType {

    private final ClassName mClassName;

    public ListCollectionType(ClassName className) {
        mClassName = className;
    }

    @Override
    public String getParameterizedTypeString() {
        return "$T<" + parameterTypes.get(0).getParameterizedTypeString() + ">";
    }

    @Override
    public Object[] getParameterizedTypeStringArgs() {
        return expandStringArgs(mClassName, parameterTypes.get(0).getParameterizedTypeStringArgs());
    }

    @Override
    public Class getGenericClass() {
        return List.class;
    }

}
