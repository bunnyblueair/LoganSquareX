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

package io.logansquarex.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.logansquarex.core.Constants;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * 每个项目只允许定义一个配置
 * Created by bunnyblue on 3/21/18.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface XBuildConfig {
    String targetPkg() default Constants.LOADER_PACKAGE_NAME;

    String targetClass() default Constants.LOADER_CLASS_NAME;

    boolean autoMerge() default false;

    public String[] configList() default {};

}
