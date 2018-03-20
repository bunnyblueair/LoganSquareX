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

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/** Attempt at making a DateFormat that can actually parse ISO 8601 dates correctly */
public class DefaultDateFormatter extends SimpleDateFormat {

    public DefaultDateFormatter() {
        super("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /** Replace ending Z's with +0000 so Java's SimpleDateFormat can handle it correctly */
    private String getFixedInputString(String input) {
        return input != null ? input.replaceAll("Z$", "+0000") : null;
    }

    @Override
    public Date parse(String string) throws ParseException {
        return super.parse(getFixedInputString(string));
    }

    @Override
    public Object parseObject(String string, ParsePosition position) {
        return super.parseObject(getFixedInputString(string), position);
    }

    @Override
    public Object parseObject(String string) throws ParseException {
        return super.parseObject(getFixedInputString(string));
    }

    @Override
    public Date parse(String string, ParsePosition position) {
        return super.parse(getFixedInputString(string), position);
    }
}