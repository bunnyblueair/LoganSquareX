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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public abstract class CalendarTypeConverter implements TypeConverter<Calendar> {

    // DateFormat is not thread-safe, so wrap it in a ThreadLocal
    private final ThreadLocal<DateFormat> mDateFormat = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return getDateFormat();
        }
    };

    @Override
    public Calendar parse(JsonParser jsonParser) throws IOException {
        String dateString = jsonParser.getValueAsString(null);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDateFormat.get().parse(dateString));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public void serialize(Calendar object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (fieldName != null) {
            jsonGenerator.writeStringField(fieldName, mDateFormat.get().format(object.getTimeInMillis()));
        } else {
            jsonGenerator.writeString(mDateFormat.get().format(object.getTimeInMillis()));
        }
    }

    /** Called to get the DateFormat used to parse and serialize objects */
    public abstract DateFormat getDateFormat();

}
