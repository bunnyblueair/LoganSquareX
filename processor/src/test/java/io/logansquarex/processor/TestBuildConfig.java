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

package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;

/**
 * Created by bunnyblue on 3/21/18.
 */
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
public class TestBuildConfig {
}