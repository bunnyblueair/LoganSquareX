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

package io.logansquarex.processor.processor;


import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import io.logansquarex.core.Constants;
import io.logansquarex.core.JsonMapper;
import io.logansquarex.core.JsonMapperLoader;
import io.logansquarex.core.ParameterizedType;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.XHide;

import io.logansquarex.core.objectmappers.BooleanMapper;
import io.logansquarex.core.objectmappers.DoubleMapper;
import io.logansquarex.core.objectmappers.FloatMapper;
import io.logansquarex.core.objectmappers.IntegerMapper;
import io.logansquarex.core.objectmappers.ListMapper;
import io.logansquarex.core.objectmappers.LongMapper;
import io.logansquarex.core.objectmappers.MapMapper;
import io.logansquarex.core.objectmappers.ObjectMapper;
import io.logansquarex.core.objectmappers.StringMapper;
import io.logansquarex.core.util.SimpleArrayMap;

public class JsonMapperLoaderInjector {
    HashMap<String, String> mapperMergers;
    public JsonMapperLoaderInjector(Collection<JsonObjectHolder> mJsonObjectHolders, XBuildConfig buildPackage, HashMap<String, String> mapperMergers) {
        this.mJsonObjectHolders = mJsonObjectHolders;
        this.buildPackage = buildPackage;
        this.mapperMergers=mapperMergers;
    }

    private final Collection<JsonObjectHolder> mJsonObjectHolders;
    private final XBuildConfig buildPackage;


    public String getJavaClassFile() {
        try {
            return JavaFile.builder(Constants.LOADER_PACKAGE_NAME, getTypeSpec()).build().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TypeSpec getTypeSpec() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(buildPackage.targetClass()).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        builder.addSuperinterface(ClassName.get(JsonMapperLoader.class));
        // XHide autoList=new XHide();
        if (!buildPackage.autoMerge()) {
//            AnnotationSpec annotationSpec = AnnotationSpec.builder(XHideCl.class).build();
//            builder.addAnnotation(annotationSpec);
        }
        //     addAllBuiltInMappers(builder);
        builder.addMethod(getPutAllJsonMappersMethodEmpty(builder));

        addParameterizedMapperGetters(builder);
        if (!buildPackage.autoMerge()) {
          //  getAllFiledsAutoMergeAble(builder);
        }

        builder.addMethod(getPutAllJsoClassMappersMethod(builder,buildPackage));
        addDefaultSize(builder);
        return builder.build();
    }

    private void addAllBuiltInMappers(TypeSpec.Builder typeSpecBuilder) {
        addBuiltInMapper(typeSpecBuilder, StringMapper.class);
        addBuiltInMapper(typeSpecBuilder, IntegerMapper.class);
        addBuiltInMapper(typeSpecBuilder, LongMapper.class);
        addBuiltInMapper(typeSpecBuilder, FloatMapper.class);
        addBuiltInMapper(typeSpecBuilder, DoubleMapper.class);
        addBuiltInMapper(typeSpecBuilder, BooleanMapper.class);
        addBuiltInMapper(typeSpecBuilder, ObjectMapper.class);
        addBuiltInMapper(typeSpecBuilder, ListMapper.class);
        addBuiltInMapper(typeSpecBuilder, MapMapper.class);
    }

    private void addBuiltInMapper(TypeSpec.Builder typeSpecBuilder, Class mapperClass) {
        typeSpecBuilder.addField(FieldSpec.builder(mapperClass, getMapperVariableName(mapperClass))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", mapperClass)
                .build()
        );
    }

    private void addDefaultSize(TypeSpec.Builder typeSpecBuilder) {
        typeSpecBuilder.addField(FieldSpec.builder(int.class, "DEFAULT_MAP_SIZE")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(mJsonObjectHolders.size() + "")
                .build()
        );
    }

    private MethodSpec getPutAllJsonMappersMethodEmpty(TypeSpec.Builder typeSpecBuilder) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("putAllJsonMappers")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterizedTypeName.get(ClassName.get(SimpleArrayMap.class), ClassName.get(Class.class), ClassName.get(JsonMapper.class)), "map");

//        List<String> createdMappers = new ArrayList<>();
//        for (JsonObjectHolder jsonObjectHolder : mJsonObjectHolders) {
//            if (jsonObjectHolder.typeParameters.size() == 0) {
//                TypeName mapperTypeName = ClassName.get(jsonObjectHolder.packageName, jsonObjectHolder.injectedClassName);
//                String variableName = getMapperVariableName(jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName);
//
//                typeSpecBuilder.addField(FieldSpec.builder(mapperTypeName, variableName)
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .build()
//                );
//
//                createdMappers.add(variableName);
//                builder.addStatement("$L = new $T()", variableName, mapperTypeName);
//
//                if (!jsonObjectHolder.isAbstractClass) {
//                    builder.addStatement("map.put($T.class, $L)", jsonObjectHolder.objectTypeName, variableName);
//                }
//            }
//        }

//        for (String mapper : createdMappers) {
//            builder.addStatement("$L.ensureParent()", mapper);
//        }

        return builder.build();
    }

    private MethodSpec getPutAllJsonMappersMethod(TypeSpec.Builder typeSpecBuilder) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("putAllJsonMappers")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterizedTypeName.get(ClassName.get(SimpleArrayMap.class), ClassName.get(Class.class), ClassName.get(JsonMapper.class)), "map")
                .addStatement("map.put($T.class, $L)", String.class, getMapperVariableName(StringMapper.class))
                .addStatement("map.put($T.class, $L)", Integer.class, getMapperVariableName(IntegerMapper.class))
                .addStatement("map.put($T.class, $L)", Long.class, getMapperVariableName(LongMapper.class))
                .addStatement("map.put($T.class, $L)", Float.class, getMapperVariableName(FloatMapper.class))
                .addStatement("map.put($T.class, $L)", Double.class, getMapperVariableName(DoubleMapper.class))
                .addStatement("map.put($T.class, $L)", Boolean.class, getMapperVariableName(BooleanMapper.class))
                .addStatement("map.put($T.class, $L)", Object.class, getMapperVariableName(ObjectMapper.class))
                .addStatement("map.put($T.class, $L)", List.class, getMapperVariableName(ListMapper.class))
                .addStatement("map.put($T.class, $L)", ArrayList.class, getMapperVariableName(ListMapper.class))
                .addStatement("map.put($T.class, $L)", Map.class, getMapperVariableName(MapMapper.class))
                .addStatement("map.put($T.class, $L)", HashMap.class, getMapperVariableName(MapMapper.class));

        List<String> createdMappers = new ArrayList<>();
        for (JsonObjectHolder jsonObjectHolder : mJsonObjectHolders) {
            if (jsonObjectHolder.typeParameters.size() == 0) {
                TypeName mapperTypeName = ClassName.get(jsonObjectHolder.packageName, jsonObjectHolder.injectedClassName);
                String variableName = getMapperVariableName(jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName);

                typeSpecBuilder.addField(FieldSpec.builder(mapperTypeName, variableName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .build()
                );

                createdMappers.add(variableName);
                builder.addStatement("$L = new $T()", variableName, mapperTypeName);

                if (!jsonObjectHolder.isAbstractClass) {
                    builder.addStatement("map.put($T.class, $L)", jsonObjectHolder.objectTypeName, variableName);
                }
            }
        }

//        for (String mapper : createdMappers) {
//            builder.addStatement("$L.ensureParent()", mapper);
//        }

        return builder.build();
    }

    private MethodSpec getPutAllJsoClassMappersMethod(TypeSpec.Builder typeSpecBuilder,XBuildConfig config) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("retainAllClassMapper")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterizedTypeName.get(ClassName.get(SimpleArrayMap.class), ClassName.get(Class.class), ClassName.get(Class.class)), "map");

        for (JsonObjectHolder jsonObjectHolder : mJsonObjectHolders) {
            if (jsonObjectHolder.typeParameters.size() == 0) {
                TypeName mapperTypeName = ClassName.get(jsonObjectHolder.packageName, jsonObjectHolder.injectedClassName);
//                String variableName = getMapperVariableName(jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName);
//
//                typeSpecBuilder.addField(FieldSpec.builder(mapperTypeName, variableName)
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .build()
//                );
//
//                createdMappers.add(variableName);
//                builder.addStatement("$L = new $T()", variableName, mapperTypeName);

                if (!jsonObjectHolder.isAbstractClass) {
                    builder.addStatement("map.put($T.class, $T.class)", jsonObjectHolder.objectTypeName, mapperTypeName);
                }
            }
        }
        if (buildPackage.autoMerge()&&!mapperMergers.isEmpty()){
            for (Map.Entry<String, String> entry : mapperMergers.entrySet()) {
                builder.addStatement("map.put($T.class, $T.class)", entry.getValue(), entry.getKey());
            }
          // while ()

        }else {
           // System.err.println(buildPackage.autoMerge()+"=====not found=="+mapperMergers.size());
        }
        for (String cf:config.configList()){;
           builder.addStatement("new "+cf+"().retainAllClassMapper(map)");

        }
//        for (String mapper : createdMappers) {
//            builder.addStatement("$L.ensureParent()", mapper);
//        }

        return builder.build();
    }

    private void getAllFiledsAutoMergeAble(TypeSpec.Builder typeSpecBuilder) {

        AnnotationSpec annotationSpec = AnnotationSpec.builder(XHide.class).build();
       // builder.addAnnotation(annotationSpec);
        List<String> createdMappers = new ArrayList<>();
        for (JsonObjectHolder jsonObjectHolder : mJsonObjectHolders) {
            if (jsonObjectHolder.typeParameters.size() == 0) {
                TypeName mapperTypeName = ClassName.get("java.lang", "String");//ClassName.get(jsonObjectHolder.packageName, jsonObjectHolder.injectedClassName);
                String variableName = getMapperVariableName(jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName);
                if (createdMappers.contains(variableName)) {
                   // continue;
                }
              //  if ()
                String val=jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName;
               // System.err.println("val="+val );
                typeSpecBuilder.addField(FieldSpec.builder(mapperTypeName, variableName).initializer(CodeBlock.of("\""+val+"\""))//.initializer("\"" + jsonObjectHolder.packageName + "." + jsonObjectHolder.injectedClassName + "\"")
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC,Modifier.FINAL).addAnnotation(annotationSpec)
                        .build()
                );

                createdMappers.add(variableName);
                //typeSpecBuilder.addStatement("$L = new $T()", variableName, mapperTypeName);


            }
        }

//        for (String mapper : createdMappers) {
//            builder.addStatement("$L.ensureParent()", mapper);
//        }

        //    return builder.build();
    }

    private void addParameterizedMapperGetters(TypeSpec.Builder builder) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("mapperFor")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(TypeVariableName.get("T"))
                .returns(ParameterizedTypeName.get(ClassName.get(JsonMapper.class), TypeVariableName.get("T")))
                .addParameter(ParameterizedTypeName.get(ClassName.get(ParameterizedType.class), TypeVariableName.get("T")), "type")
                .addParameter(ParameterizedTypeName.get(ClassName.get(SimpleArrayMap.class), ClassName.get(ParameterizedType.class), ClassName.get(JsonMapper.class)), "partialMappers");

//        boolean conditionalStarted = false;
//        for (JsonObjectHolder jsonObjectHolder : mJsonObjectHolders) {
//            if (jsonObjectHolder.typeParameters.size() > 0) {
//                String conditional = String.format("if (type.rawType == %s.class)", jsonObjectHolder.objectTypeName.toString().replaceAll("<(.*?)>", ""));
//                if (conditionalStarted) {
//                    methodBuilder.nextControlFlow("else " + conditional);
//                } else {
//                    conditionalStarted = true;
//                    methodBuilder.beginControlFlow(conditional);
//                }
//
//                methodBuilder.beginControlFlow("if (type.typeParameters.size() == $L)", jsonObjectHolder.typeParameters.size());
//
//                StringBuilder constructorArgs = new StringBuilder();
//                for (int i = 0; i < jsonObjectHolder.typeParameters.size(); i++) {
//                    constructorArgs.append(", type.typeParameters.get(").append(i).append(")");
//                }
//                methodBuilder.addStatement("return new $T(type" + constructorArgs.toString() + ", partialMappers)", ClassName.get(jsonObjectHolder.packageName, jsonObjectHolder.injectedClassName));
//
//                methodBuilder.nextControlFlow("else");
//                methodBuilder.addStatement(
//                        "throw new $T(\"Invalid number of parameter types. Type $T expects $L parameter types, received \" + type.typeParameters.size())",
//                        RuntimeException.class, jsonObjectHolder.objectTypeName, jsonObjectHolder.typeParameters.size()
//                );
//                methodBuilder.endControlFlow();
//            }
//        }
//
//        if (conditionalStarted) {
//            methodBuilder.endControlFlow();
//        }

        methodBuilder.addStatement("return null");

        builder.addMethod(methodBuilder.build());
    }

    public static String getMapperVariableName(Class cls) {
        return getMapperVariableName(cls.getCanonicalName());
    }

    public static String getMapperVariableName(String fullyQualifiedClassName) {
        return fullyQualifiedClassName.replaceAll("\\.", "_").replaceAll("\\$", "_").toUpperCase();
    }
}
