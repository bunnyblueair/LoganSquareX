package io.logansquarex.processor.processor;

import io.logansquarex.processor.type.Type;
import io.logansquarex.processor.type.collection.CollectionType;
import io.logansquarex.processor.type.field.ParameterizedTypeField;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PRIVATE;

public class JsonFieldHolder {

    public String[] fieldName;
    public String setterMethod;
    public String getterMethod;
    public boolean shouldParse;
    public boolean shouldSerialize;
    public Type type;

    public String fill(Element element, Elements elements, Types types, String[] fieldNames, TypeMirror typeConverterType, JsonObjectHolder objectHolder, boolean shouldParse, boolean shouldSerialize, boolean lombok) {
        if (fieldNames == null || fieldNames.length == 0) {
            String defaultFieldName = element.getSimpleName().toString();

            switch (objectHolder.fieldNamingPolicy) {
                case LOWER_CASE_WITH_UNDERSCORES:
                    defaultFieldName = TextUtils.toLowerCaseWithUnderscores(defaultFieldName);
                    break;
            }

            fieldNames = new String[]{defaultFieldName};
        }
        fieldName = fieldNames;

        this.shouldParse = shouldParse;
        this.shouldSerialize = shouldSerialize;
        if (lombok && element.getModifiers().contains(PRIVATE)) {
            getterMethod = getGetter(element);
            setterMethod = getSetter(element);
        } else {
            getterMethod = getGetter(element, elements);
            setterMethod = getSetter(element, elements);
        }


        type = Type.typeFor(element.asType(), typeConverterType, elements, types);
        return ensureValidType(type, element);
    }

    private String ensureValidType(Type type, Element element) {
        if (type == null) {
            return "Type could not be determined for " + element.toString();
        } else {
            if (type instanceof CollectionType) {
                for (Type parameterType : type.parameterTypes) {
                    String errorMessage = ensureValidType(parameterType, element);
                    if (errorMessage != null) {
                        return errorMessage;
                    }
                }
            }

            return null;
        }
    }

    public static String getGetter(Element element, Elements elements) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        TypeKind elementTypeKind = element.asType().getKind();

        String elementName = element.getSimpleName().toString();
        String elementNameLowerCase = elementName.toLowerCase();

        List<String> possibleMethodNames = new ArrayList<>();
        possibleMethodNames.add("get" + elementNameLowerCase);
        if (elementTypeKind == TypeKind.BOOLEAN) {
            possibleMethodNames.add("is" + elementNameLowerCase);
            possibleMethodNames.add("has" + elementNameLowerCase);
            possibleMethodNames.add(elementNameLowerCase);
        }

        // Handle the case where variables are named in the form mVariableName instead of just variableName
        if (elementName.length() > 1 && elementName.charAt(0) == 'm' && (elementName.charAt(1) >= 'A' && elementName.charAt(1) <= 'Z')) {
            possibleMethodNames.add("get" + elementNameLowerCase.substring(1));
            if (elementTypeKind == TypeKind.BOOLEAN) {
                possibleMethodNames.add("is" + elementNameLowerCase.substring(1));
                possibleMethodNames.add("has" + elementNameLowerCase.substring(1));
                possibleMethodNames.add(elementNameLowerCase.substring(1));
            }
        }

        List<? extends Element> elementMembers = elements.getAllMembers(enclosingElement);
        List<ExecutableElement> elementMethods = ElementFilter.methodsIn(elementMembers);
        for (ExecutableElement methodElement : elementMethods) {
            if (methodElement.getParameters().size() == 0) {
                String methodNameString = methodElement.getSimpleName().toString();
                String methodNameLowerCase = methodNameString.toLowerCase();

                if (possibleMethodNames.contains(methodNameLowerCase)) {
                    if (methodElement.getParameters().size() == 0) {
                        if (methodElement.getReturnType().toString().equals(element.asType().toString())) {
                            return methodNameString;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static String getGetter(Element element) {
        String methodName = element.getSimpleName().toString();
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        String elementName = element.getSimpleName().toString();
       // String elementNameLowerCase = elementName.toLowerCase();

        List<String> possibleMethodNames = new ArrayList<>();
        methodName = "get" + (Character.isLowerCase(elementName.charAt(0)) ?
                (Character.toUpperCase(elementName.charAt(0))) + methodName.substring(1) :
                elementName.charAt(0) + methodName.substring(1));

        // Handle the case where variables are named in the form mVariableName instead of just variableName
        if (elementName.length() > 1 && elementName.charAt(0) == 'm' && (elementName.charAt(1) >= 'A' && elementName.charAt(1) <= 'Z')) {
            methodName = "get" + methodName.substring(1);
        }


        return methodName;
    }

    public static String getSetter(Element element) {
        String methodName = null;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        String elementName = element.getSimpleName().toString();
        String elementNameLowerCase = elementName.toLowerCase();

        List<String> possibleMethodNames = new ArrayList<>();
        methodName = "set" + (Character.isLowerCase(elementName.charAt(0)) ?
                (Character.toUpperCase(elementName.charAt(0))) + elementName.substring(1) :
                elementName.charAt(0) + elementName.substring(1));

        // Handle the case where variables are named in the form mVariableName instead of just variableName
        if (elementName.length() > 1 && elementName.charAt(0) == 'm' && (elementName.charAt(1) >= 'A' && elementName.charAt(1) <= 'Z')) {
            methodName = "set" + elementNameLowerCase.substring(1);
        }


        return methodName;
    }

    public static String getSetter(Element element, Elements elements) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        String elementName = element.getSimpleName().toString();
        String elementNameLowerCase = elementName.toLowerCase();

        List<String> possibleMethodNames = new ArrayList<>();
        possibleMethodNames.add("set" + elementNameLowerCase);

        // Handle the case where variables are named in the form mVariableName instead of just variableName
        if (elementName.length() > 1 && elementName.charAt(0) == 'm' && (elementName.charAt(1) >= 'A' && elementName.charAt(1) <= 'Z')) {
            possibleMethodNames.add("set" + elementNameLowerCase.substring(1));
        }

        List<? extends Element> elementMembers = elements.getAllMembers(enclosingElement);
        List<ExecutableElement> elementMethods = ElementFilter.methodsIn(elementMembers);
        for (ExecutableElement methodElement : elementMethods) {
            String methodNameString = methodElement.getSimpleName().toString();
            String methodNameLowerCase = methodNameString.toLowerCase();

            if (possibleMethodNames.contains(methodNameLowerCase)) {
                if (methodElement.getParameters().size() == 1) {
                    if (methodElement.getParameters().get(0).asType().toString().equals(element.asType().toString())) {
                        return methodNameString;
                    }
                }
            }
        }

        return null;
    }

    public boolean hasSetter() {
        return !TextUtils.isEmpty(setterMethod);
    }

    public boolean hasGetter() {
        return !TextUtils.isEmpty(getterMethod);
    }

    public boolean isGenericType() {
        return isGenericType(type);
    }

    private boolean isGenericType(Type type) {
        if (type instanceof ParameterizedTypeField) {
            return true;
        } else {
            for (Type parameterizedType : type.parameterTypes) {
                if (isGenericType(parameterizedType)) {
                    return true;
                }
            }
            return false;
        }
    }
}
