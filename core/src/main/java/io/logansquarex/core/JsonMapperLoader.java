package io.logansquarex.core;


import io.logansquarex.core.util.SimpleArrayMap;

public interface JsonMapperLoader {

    void putAllJsonMappers(SimpleArrayMap<Class, JsonMapper> map);
    void retainAllClassMapper(SimpleArrayMap<Class, Class> map);
    <T> JsonMapper<T> mapperFor(ParameterizedType<T> type, SimpleArrayMap<ParameterizedType, JsonMapper> partialMappers);


}
