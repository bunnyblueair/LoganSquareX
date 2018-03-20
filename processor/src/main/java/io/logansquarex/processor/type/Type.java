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

package io.logansquarex.processor.type;

import io.logansquarex.processor.type.collection.ArrayCollectionType;
import io.logansquarex.processor.type.collection.CollectionType;
import io.logansquarex.processor.type.field.FieldType;
import io.logansquarex.processor.type.field.ParameterizedTypeField;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class Type {

    public final List<Type> parameterTypes;

    public abstract TypeName getTypeName();
    public abstract String getParameterizedTypeString();
    public abstract Object[] getParameterizedTypeStringArgs();
    public abstract void parse(MethodSpec.Builder builder, int depth, String setter, Object... setterFormatArgs);
    public abstract void serialize(MethodSpec.Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull);

    public Type() {
        parameterTypes = new ArrayList<>();
    }

    public static Type typeFor(TypeMirror typeMirror, TypeMirror typeConverterType, Elements elements, Types types) {
        TypeMirror genericClassTypeMirror = types.erasure(typeMirror);
        boolean hasTypeConverter = typeConverterType != null && !typeConverterType.toString().equals("void");

        Type type;
        if (!hasTypeConverter && typeMirror instanceof ArrayType) {
            TypeMirror arrayTypeMirror = ((ArrayType)typeMirror).getComponentType();
            type = new ArrayCollectionType(Type.typeFor(arrayTypeMirror, null, elements, types));
        } else if (!hasTypeConverter && !genericClassTypeMirror.toString().equals(typeMirror.toString())) {
            type = CollectionType.collectionTypeFor(typeMirror, genericClassTypeMirror, elements, types);

            if (type == null) {
                if (typeMirror.toString().contains("?")) {
                    throw new RuntimeException("Generic types with wildcards are currently not supported by LoganSquare.");
                }
                try {
                    type = new ParameterizedTypeField(TypeName.get(typeMirror));
                } catch (Exception ignored) { }
            }
        } else {
            type = FieldType.fieldTypeFor(typeMirror, typeConverterType, elements, types);
        }

        return type;
    }

    protected Object[] expandStringArgs(Object... args) {
        List<Object> argList = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Object[]) {
                Collections.addAll(argList, (Object[])arg);
            } else {
                argList.add(arg);
            }
        }

        return argList.toArray(new Object[argList.size()]);
    }

    public void addParameterTypes(List<TypeMirror> parameterTypes, Elements elements, Types types) {
        for (TypeMirror typeMirror : parameterTypes) {
            addParameterType(typeMirror, elements, types);
        }
    }

    public void addParameterType(Type type) {
        parameterTypes.add(type);
    }

    public void addParameterType(TypeMirror parameterType, Elements elements, Types types) {
        parameterTypes.add(Type.typeFor(parameterType, null, elements, types));
    }

    public Set<ClassNameObjectMapper> getUsedJsonObjectMappers() {
        Set<ClassNameObjectMapper> set = new HashSet<>();
        for (Type parameterType : parameterTypes) {
            set.addAll(parameterType.getUsedJsonObjectMappers());
        }
        return set;
    }

    public Set<TypeName> getUsedTypeConverters() {
        Set<TypeName> set = new HashSet<>();
        for (Type parameterType : parameterTypes) {
            set.addAll(parameterType.getUsedTypeConverters());
        }
        return set;
    }

    public static class ClassNameObjectMapper {
        public final ClassName className;
        public final String objectMapper;

        public ClassNameObjectMapper(ClassName className, String objectMapper) {
            this.className = className;
            this.objectMapper = objectMapper;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o == null || getClass() != o.getClass()) {
                return false;
            } else {
                return objectMapper.equals(((ClassNameObjectMapper)o).objectMapper);
            }
        }

        @Override
        public int hashCode() {
            return objectMapper.hashCode();
        }
    }
}
