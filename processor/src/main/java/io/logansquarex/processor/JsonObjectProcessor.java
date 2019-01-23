package io.logansquarex.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.JsonIgnore;
import io.logansquarex.core.annotation.JsonIgnore.IgnorePolicy;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.annotation.JsonObject.FieldDetectionPolicy;
import io.logansquarex.processor.processor.JsonFieldHolder;
import io.logansquarex.processor.processor.JsonObjectHolder;
import io.logansquarex.processor.processor.JsonObjectHolder.JsonObjectHolderBuilder;
import io.logansquarex.processor.processor.TextUtils;
import io.logansquarex.processor.processor.TypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PRIVATE;

public class JsonObjectProcessor extends Processor {

    public JsonObjectProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public Class getAnnotation() {
        return JsonObject.class;
    }

    @Override
    public void findAndParseObjects(RoundEnvironment env, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        for (Element element : env.getElementsAnnotatedWith(JsonObject.class)) {
            try {
                processJsonObjectAnnotation(element, jsonObjectMap, elements, types);
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));

                error(element, "Unable to generate injector for %s. Stack trace incoming:\n%s", JsonObject.class, stackTrace.toString());
            }
        }
    }

    private void processJsonObjectAnnotation(Element element, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        TypeElement typeElement = (TypeElement) element;

        if (element.getModifiers().contains(PRIVATE)) {
            error(element, "%s: %s annotation can't be used on private classes.", typeElement.getQualifiedName(), JsonObject.class.getSimpleName());
        }

        JsonObjectHolder holder = jsonObjectMap.get(TypeUtils.getInjectedFQCN(typeElement, elements));
        if (holder == null) {
            String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
            String objectClassName = TypeUtils.getSimpleClassName(typeElement, packageName);
            String injectedSimpleClassName = objectClassName + Constants.MAPPER_CLASS_SUFFIX;
            boolean abstractClass = element.getModifiers().contains(ABSTRACT);
            List<? extends TypeParameterElement> parentTypeParameters = new ArrayList<>();
            List<String> parentUsedTypeParameters = new ArrayList<>();
            TypeName parentClassName = null;

            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass.getKind() != TypeKind.NONE) {
                TypeElement superclassElement = (TypeElement) types.asElement(superclass);
                if (superclassElement.getAnnotation(JsonObject.class) != null) {
                    if (superclassElement.getTypeParameters() != null) {
                        parentTypeParameters = superclassElement.getTypeParameters();
                    }

                    String superclassName = superclass.toString();
                    int indexOfTypeParamStart = superclassName.indexOf("<");
                    if (indexOfTypeParamStart > 0) {
                        String typeParams = superclassName.substring(indexOfTypeParamStart + 1, superclassName.length() - 1);
                        parentUsedTypeParameters = Arrays.asList(typeParams.split("\\s*,\\s*"));
                    }
                }
            }
            while (superclass.getKind() != TypeKind.NONE) {
                TypeElement superclassElement = (TypeElement) types.asElement(superclass);

                if (superclassElement.getAnnotation(JsonObject.class) != null) {
                    String superclassPackageName = elements.getPackageOf(superclassElement).getQualifiedName().toString();
                    parentClassName = ClassName.get(superclassPackageName, TypeUtils.getSimpleClassName(superclassElement, superclassPackageName));
                    break;
                }

                superclass = superclassElement.getSuperclass();
            }

            JsonObject annotation = element.getAnnotation(JsonObject.class);

            holder = new JsonObjectHolderBuilder()
                    .setPackageName(packageName)
                    .setInjectedClassName(injectedSimpleClassName)
                    .setObjectTypeName(TypeName.get(typeElement.asType()))
                    .setIsAbstractClass(abstractClass)
                    .setParentTypeName(parentClassName)
                    .setParentTypeParameters(parentTypeParameters)
                    .setParentUsedTypeParameters(parentUsedTypeParameters)
                    .setFieldDetectionPolicy(annotation.fieldDetectionPolicy())
                    .setFieldNamingPolicy(annotation.fieldNamingPolicy())
                    .setSerializeNullObjects(annotation.serializeNullObjects())
                    .setSerializeNullCollectionElements(annotation.serializeNullCollectionElements())
                    .setTypeParameters(typeElement.getTypeParameters())
                    .build();

            FieldDetectionPolicy fieldDetectionPolicy = annotation.fieldDetectionPolicy();
            if (fieldDetectionPolicy == FieldDetectionPolicy.NONPRIVATE_FIELDS || fieldDetectionPolicy == FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS) {
                addAllNonPrivateFields(element, elements, types, holder);
            } else if (fieldDetectionPolicy == FieldDetectionPolicy.LOMBOK_FIELDS_AND_ACCESSORS) {
                addAlllombokPrivateFields(element, elements, types, holder);
            }
            if (fieldDetectionPolicy == FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS) {
                addAllNonPrivateAccessors(element, elements, types, holder);
            }

            jsonObjectMap.put(TypeUtils.getInjectedFQCN(typeElement, elements), holder);
        }
    }

    private void addAllNonPrivateFields(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            ElementKind enclosedElementKind = enclosedElement.getKind();
            if (enclosedElementKind == ElementKind.FIELD) {
                Set<Modifier> modifiers = enclosedElement.getModifiers();
                if (!modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.PROTECTED) && !modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {
                    createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder, false);
                }
            }
        }
    }

    private void addAlllombokPrivateFields(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            ElementKind enclosedElementKind = enclosedElement.getKind();
            if (enclosedElementKind == ElementKind.FIELD) {
                Set<Modifier> modifiers = enclosedElement.getModifiers();
                if (((modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.PROTECTED)) && !modifiers.contains(Modifier.STATIC))) {
                    createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder, true);

                }
            }
        }
    }

    private void addAllNonPrivateAccessors(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            ElementKind enclosedElementKind = enclosedElement.getKind();
            if (enclosedElementKind == ElementKind.FIELD) {
                Set<Modifier> modifiers = enclosedElement.getModifiers();

                if (modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {

                    String getter = JsonFieldHolder.getGetter(enclosedElement, elements);
                    String setter = JsonFieldHolder.getSetter(enclosedElement, elements);

                    if (!TextUtils.isEmpty(getter) && !TextUtils.isEmpty(setter)) {
                        createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder, false);
                    }
                }
            }
        }
    }

    private void createOrUpdateFieldHolder(Element element, Elements elements, Types types, JsonObjectHolder objectHolder, boolean lombok) {
        JsonIgnore ignoreAnnotation = element.getAnnotation(JsonIgnore.class);
        boolean shouldParse = ignoreAnnotation == null || ignoreAnnotation.ignorePolicy() == IgnorePolicy.SERIALIZE_ONLY;
        boolean shouldSerialize = ignoreAnnotation == null || ignoreAnnotation.ignorePolicy() == IgnorePolicy.PARSE_ONLY;

        if (shouldParse || shouldSerialize) {
            JsonFieldHolder fieldHolder = objectHolder.fieldMap.get(element.getSimpleName().toString());
            if (fieldHolder == null) {
                fieldHolder = new JsonFieldHolder();
                objectHolder.fieldMap.put(element.getSimpleName().toString(), fieldHolder);
            }

            String error = fieldHolder.fill(element, elements, types, null, null, objectHolder, shouldParse, shouldSerialize
                    , lombok);

            if (!TextUtils.isEmpty(error)) {
                error(element, error);
            }
        }
    }
}
