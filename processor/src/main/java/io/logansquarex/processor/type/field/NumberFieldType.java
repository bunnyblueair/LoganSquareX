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

import com.squareup.javapoet.MethodSpec.Builder;

import java.util.List;

import static io.logansquarex.processor.processor.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;

public abstract class NumberFieldType extends FieldType {

    protected boolean isPrimitive;

    public NumberFieldType(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull) {
        if (!isPrimitive && checkIfNull) {
            builder.beginControlFlow("if ($L != null)", getter);
        }

        if (isObjectProperty) {
            builder.addStatement("$L.writeNumberField($S, $L)", JSON_GENERATOR_VARIABLE_NAME, fieldName, getter);
        } else {
            builder.addStatement("$L.writeNumber($L)", JSON_GENERATOR_VARIABLE_NAME, getter);
        }

        if (!isPrimitive && checkIfNull) {
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

}
