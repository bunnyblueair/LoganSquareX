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

package io.logansquarex.core.simple;

import java.util.List;

import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.objectmappers.ListMapper;

/**
 * Created by bunnyblue on 3/20/18.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)

public class SimpleListResponse  {
    @JsonField
    private  int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    @JsonField

    private List data;
}
