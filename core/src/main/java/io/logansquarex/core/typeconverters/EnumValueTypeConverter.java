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

package io.logansquarex.core.typeconverters;

public class EnumValueTypeConverter<T extends Enum<T>> extends StringBasedTypeConverter<T> {

    private Class<T> mClass;

    public EnumValueTypeConverter(Class<T> cls) {
        mClass = cls;
    }

    @Override
    public T getFromString(String string) {
        return Enum.valueOf(mClass, convertString(string, true));
    }

    @Override
    public String convertToString(T object) {
        return convertString(object.toString(), false);
    }

    public String convertString(String string, boolean forParse) {
        return string;
    }

}
