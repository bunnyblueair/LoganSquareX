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

package io.logansquarex.processor.type.field;

import com.fasterxml.jackson.core.JsonToken;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class FloatFieldType extends NumberFieldType {

    public FloatFieldType(boolean isPrimitive) {
        super(isPrimitive);
    }

    @Override
    public TypeName getTypeName() {
        return (isPrimitive ? TypeName.FLOAT : ClassName.get(Float.class));
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return ClassName.get(Float.class);
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        if (isPrimitive) {
            setter = replaceLastLiteral(setter, "(float)$L.getValueAsDouble()");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
        } else {
            setter = replaceLastLiteral(setter, "$L.getCurrentToken() == $T.VALUE_NULL ? null : new Float($L.getValueAsDouble())");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME, JsonToken.class, JSON_PARSER_VARIABLE_NAME));
        }
    }
}
