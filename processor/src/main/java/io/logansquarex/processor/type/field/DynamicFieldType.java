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

import io.logansquarex.processor.processor.ObjectMapperInjector;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import java.util.List;
import java.util.Set;

import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class DynamicFieldType extends FieldType {

    private TypeName mTypeName;

    public DynamicFieldType(TypeName typeName) {
        mTypeName = typeName;
    }

    @Override
    public TypeName getTypeName() {
        return mTypeName;
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return mTypeName;
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, ObjectMapperInjector.getTypeConverterGetter(mTypeName) + "().parse($L)");
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull) {
        if (!mTypeName.isPrimitive() && checkIfNull) {
            builder.beginControlFlow("if ($L != null)", getter);
        }

        builder.addStatement(ObjectMapperInjector.getTypeConverterGetter(mTypeName) + "().serialize($L, $S, $L, $L)", getter, isObjectProperty ? fieldName : null, isObjectProperty, JSON_GENERATOR_VARIABLE_NAME);
        if (!mTypeName.isPrimitive() && checkIfNull) {
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
    public Set<TypeName> getUsedTypeConverters() {
        Set<TypeName> set = super.getUsedTypeConverters();
        set.add(mTypeName);
        return set;
    }
}
