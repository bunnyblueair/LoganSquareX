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

import io.logansquarex.processor.type.Type;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class ParameterizedTypeField extends FieldType {

    private final TypeName mTypeName;
    private String mJsonMapperVariableName;

    public ParameterizedTypeField(TypeName typeName) {
        mTypeName = typeName;
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, String.format("%s.parse($L)", mJsonMapperVariableName));
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull) {
        if (checkIfNull) {
            builder.beginControlFlow("if ($L != null)", getter);
        }

        if (isObjectProperty) {
            builder.addStatement("$L.writeFieldName($S)", JSON_GENERATOR_VARIABLE_NAME, fieldName);
        }
        builder.addStatement("$L.serialize($L, $L, true)", mJsonMapperVariableName, getter, JSON_GENERATOR_VARIABLE_NAME);

        if (checkIfNull) {
            if (writeIfNull) {
                builder.nextControlFlow("else");

                if (isObjectProperty) {
                    builder.addStatement("$L.writeFieldName($S)", JSON_GENERATOR_VARIABLE_NAME, fieldName);
                }
                builder.addStatement("$L.writeNull()", JSON_GENERATOR_VARIABLE_NAME);
            }
            builder.endControlFlow();
        }
    }

    @Override
    public String getParameterizedTypeString() {
        if (parameterTypes.size() > 0) {
            StringBuilder string = new StringBuilder("$T<");
            for (int i = 0; i < parameterTypes.size(); i++) {
                if (i > 0) {
                    string.append(", ");
                }
                string.append(parameterTypes.get(i).getParameterizedTypeString());
            }
            string.append('>');
            return string.toString();
        } else {
            return "$T";
        }
    }

    @Override
    public Object[] getParameterizedTypeStringArgs() {
        List<Object> args = new ArrayList<>();

        args.add(mTypeName);

        for (Type parameterType : parameterTypes) {
            args.add(parameterType.getParameterizedTypeStringArgs());
        }

        return args.toArray(new Object[args.size()]);
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return mTypeName;
    }

    @Override
    public TypeName getTypeName() {
        return mTypeName;
    }

    public void setJsonMapperVariableName(String jsonMapperVariableName) {
        mJsonMapperVariableName = jsonMapperVariableName;
    }

    public String getParameterName() {
        return mTypeName.toString();
    }
}
