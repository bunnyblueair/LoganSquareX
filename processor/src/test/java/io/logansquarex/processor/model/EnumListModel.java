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

package io.logansquarex.processor.model;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.typeconverters.StringBasedTypeConverter;

import java.util.List;
import java.util.Map;

@JsonObject
public class EnumListModel {

    public enum TestEnum {
        ONE, TWO
    }

    @JsonField(name = "enum_obj")
    TestEnum enumObj;

    @JsonField(name = "enum_list")
    List<TestEnum> enumList;

    @JsonField(name = "enum_array")
    TestEnum[] enumArray;

    @JsonField(name = "enum_map")
    Map<String, TestEnum> enumMap;

    public static class LsEnumTestConverter extends StringBasedTypeConverter<TestEnum> {
        @Override
        public TestEnum getFromString(String string) {
            return TestEnum.valueOf(string);
        }

        @Override
        public String convertToString(TestEnum testEnum) {
            return testEnum.toString();
        }
    }
}