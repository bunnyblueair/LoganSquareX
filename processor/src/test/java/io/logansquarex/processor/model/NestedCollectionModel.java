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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonObject
public class NestedCollectionModel {

    @JsonField
    public List<ArrayList<Set<Map<String, List<String>>>>>[] crazyCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return Arrays.equals(crazyCollection, ((NestedCollectionModel)o).crazyCollection);

    }

    @Override
    public int hashCode() {
        return crazyCollection != null ? Arrays.hashCode(crazyCollection) : 0;
    }
}
