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

import java.util.List;

@JsonObject
public class TwoParamGenericModel<T, K> {

    @JsonField(name = "test_t")
    public T testT;

    @JsonField(name = "test_k")
    public K testK;

    @JsonField(name = "t_list")
    public List<T> tList;

    @JsonField(name = "k_list")
    public List<K> kList;

    @JsonField(name = "test_nested_generic")
    public TwoParamGenericModel<String, Integer> testNestedGeneric;

    @JsonField(name = "test_nested_generic_integer")
    public TwoParamGenericModel<T, Integer> testNestedGenericInteger;

    @JsonField(name = "test_nested_string_generic")
    public TwoParamGenericModel<String, T> testNestedStringGeneric;

}
