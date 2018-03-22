package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;
import io.logansquarex.core.typeconverters.DateTypeConverter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public abstract class DateModel {

    @JsonField(typeConverter = MyDateTypeConverter.class)
    public Date date;

    public static class MyDateTypeConverter extends DateTypeConverter {
        private DateFormat mDateFormat;

        public MyDateTypeConverter(){
            mDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSZ");
        }

        @Override
        public DateFormat getDateFormat() {
            return mDateFormat;
        }

    }
}