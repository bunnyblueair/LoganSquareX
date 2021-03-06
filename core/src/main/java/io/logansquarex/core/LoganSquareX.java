package io.logansquarex.core;

import com.fasterxml.jackson.core.JsonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.logansquarex.JsonMapperLoaderImpl;
import io.logansquarex.core.objectmappers.BooleanMapper;
import io.logansquarex.core.objectmappers.DoubleMapper;
import io.logansquarex.core.objectmappers.FloatMapper;
import io.logansquarex.core.objectmappers.IntegerMapper;
import io.logansquarex.core.objectmappers.ListMapper;
import io.logansquarex.core.objectmappers.LongMapper;
import io.logansquarex.core.objectmappers.MapMapper;
import io.logansquarex.core.objectmappers.ObjectMapper;
import io.logansquarex.core.objectmappers.StringMapper;
import io.logansquarex.core.simple.SimpleListResponse;
import io.logansquarex.core.simple.SimpleListResponseMapper;
import io.logansquarex.core.typeconverters.DefaultCalendarConverter;
import io.logansquarex.core.typeconverters.DefaultDateConverter;
import io.logansquarex.core.typeconverters.TypeConverter;
import io.logansquarex.core.util.SimpleArrayMap;

/**
 * The point of all interaction with this library.
 * Unlike LoganSquare, the default will not throw exception.
 */
public class LoganSquareX {

    protected static final ListMapper LIST_MAPPER = new ListMapper();
    protected static final MapMapper MAP_MAPPER = new MapMapper();

    protected static final SimpleArrayMap<Class, JsonMapper> OBJECT_MAPPERS = new SimpleArrayMap<Class, JsonMapper>(JsonMapperLoaderImpl.DEFAULT_MAP_SIZE);
    protected static final SimpleArrayMap<Class, Class> CLASS_MAPPERS = new SimpleArrayMap<Class, Class>(JsonMapperLoaderImpl.DEFAULT_MAP_SIZE);
    static {
        try {
            JsonMapperLoaderImpl JSON_MAPPER_LOADER;
            JSON_MAPPER_LOADER = new JsonMapperLoaderImpl();
           // JSON_MAPPER_LOADER.putAllJsonMappers(OBJECT_MAPPERS);
            JSON_MAPPER_LOADER.retainAllClassMapper(CLASS_MAPPERS);
        } catch (Exception e) {
            e.printStackTrace();
       //  throw new RuntimeException("JsonMapperLoaderImpl class not found");
        }
        OBJECT_MAPPERS.put(String.class, new StringMapper());
        OBJECT_MAPPERS.put(Integer.class, new IntegerMapper());
        OBJECT_MAPPERS.put(Long.class, new LongMapper());
        OBJECT_MAPPERS.put(Float.class, new FloatMapper());
        OBJECT_MAPPERS.put(Double.class, new DoubleMapper());
        OBJECT_MAPPERS.put(Boolean.class, new BooleanMapper());
        OBJECT_MAPPERS.put(Object.class, new ObjectMapper());
        OBJECT_MAPPERS.put(List.class, LIST_MAPPER);
        OBJECT_MAPPERS.put(ArrayList.class, LIST_MAPPER);
        OBJECT_MAPPERS.put(Map.class, MAP_MAPPER);
        OBJECT_MAPPERS.put(HashMap.class, MAP_MAPPER);
        OBJECT_MAPPERS.put(SimpleListResponse.class, new SimpleListResponseMapper());
    }

    protected static final ConcurrentHashMap<ParameterizedType, JsonMapper> PARAMETERIZED_OBJECT_MAPPERS = new ConcurrentHashMap<ParameterizedType, JsonMapper>();

    protected static final SimpleArrayMap<Class, TypeConverter> TYPE_CONVERTERS = new SimpleArrayMap<>();

    static {
        registerTypeConverter(Date.class, new DefaultDateConverter());
        registerTypeConverter(Calendar.class, new DefaultCalendarConverter());
    }



    /**
     * The JsonFactory that should be used throughout the entire app.
     */
    public static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * Parse an object from an InputStream.
     *
     * @param is              The InputStream, most likely from your networking library.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> E parse(InputStream is, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parse(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse an object from a String. Note: parsing from an InputStream should be preferred over parsing from a String if possible.
     *
     * @param jsonString      The JSON string being parsed.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> E parse(String jsonString, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parse(jsonString);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a parameterized object from an InputStream.
     *
     * @param is             The InputStream, most likely from your networking library.
     * @param jsonObjectType The ParameterizedType describing the object. Ex: LoganSquare.parse(is, new ParameterizedType&lt;MyModel&lt;OtherModel&gt;&gt;() { });
     */
    public static <E> E parse(InputStream is, ParameterizedType<E> jsonObjectType) {
        try {
            return mapperFor(jsonObjectType).parse(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a parameterized object from a String. Note: parsing from an InputStream should be preferred over parsing from a String if possible.
     *
     * @param jsonString     The JSON string being parsed.
     * @param jsonObjectType The ParameterizedType describing the object. Ex: LoganSquare.parse(is, new ParameterizedType&lt;MyModel&lt;OtherModel&gt;&gt;() { });
     */
    public static <E> E parse(String jsonString, ParameterizedType<E> jsonObjectType) {
        try {
            return mapperFor(jsonObjectType).parse(jsonString);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a list of objects from an InputStream.
     *
     * @param is              The inputStream, most likely from your networking library.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> List<E> parseList(InputStream is, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parseList(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a list of objects from a String. Note: parsing from an InputStream should be preferred over parsing from a String if possible.
     *
     * @param jsonString      The JSON string being parsed.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> List<E> parseList(String jsonString, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parseList(jsonString);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a map of objects from an InputStream.
     *
     * @param is              The inputStream, most likely from your networking library.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> Map<String, E> parseMap(InputStream is, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parseMap(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Parse a map of objects from a String. Note: parsing from an InputStream should be preferred over parsing from a String if possible.
     *
     * @param jsonString      The JSON string being parsed.
     * @param jsonObjectClass The @JsonObject class to parse the InputStream into
     */
    public static <E> Map<String, E> parseMap(String jsonString, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).parseMap(jsonString);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serialize an object to a JSON String.
     *
     * @param object The object to serialize.
     */
    @SuppressWarnings("unchecked")
    public static <E> String serialize(E object) {
        try {
            return mapperFor((Class<E>) object.getClass()).serialize(object);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serialize an object to an OutputStream.
     *
     * @param object The object to serialize.
     * @param os     The OutputStream being written to.
     */
    @SuppressWarnings("unchecked")
    public static <E> void serialize(E object, OutputStream os) {
        try {
            mapperFor((Class<E>) object.getClass()).serialize(object, os);
        } catch (IOException e) {
            // ignored
            System.out.print(e.toString());
        }
    }

    /**
     * Serialize a parameterized object to a JSON String.
     *
     * @param object            The object to serialize.
     * @param parameterizedType The ParameterizedType describing the object. Ex: LoganSquare.serialize(object, new ParameterizedType&lt;MyModel&lt;OtherModel&gt;&gt;() { });
     */
    @SuppressWarnings("unchecked")
    public static <E> String serialize(E object, ParameterizedType<E> parameterizedType) {
        try {
            return mapperFor(parameterizedType).serialize(object);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serialize a parameterized  object to an OutputStream.
     *
     * @param object            The object to serialize.
     * @param parameterizedType The ParameterizedType describing the object. Ex: LoganSquare.serialize(object, new ParameterizedType&lt;MyModel&lt;OtherModel&gt;&gt;() { }, os);
     * @param os                The OutputStream being written to.
     */
    @SuppressWarnings("unchecked")
    public static <E> void serialize(E object, ParameterizedType<E> parameterizedType, OutputStream os) {
        try {
            mapperFor(parameterizedType).serialize(object, os);
        } catch (IOException e) {
            // ignored
            System.out.print(e.toString());
        }
    }

    /**
     * Serialize a list of objects to a JSON String.
     *
     * @param list            The list of objects to serialize.
     * @param jsonObjectClass The @JsonObject class of the list elements
     */
    public static <E> String serialize(List<E> list, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).serialize(list);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serialize a list of objects to an OutputStream.
     *
     * @param list            The list of objects to serialize.
     * @param os              The OutputStream to which the list should be serialized
     * @param jsonObjectClass The @JsonObject class of the list elements
     */
    public static <E> void serialize(List<E> list, OutputStream os, Class<E> jsonObjectClass) {
        try {
            mapperFor(jsonObjectClass).serialize(list, os);
        } catch (IOException e) {
            // ignored
            System.out.print(e.toString());
        }
    }

    /**
     * Serialize a map of objects to a JSON String.
     *
     * @param map             The map of objects to serialize.
     * @param jsonObjectClass The @JsonObject class of the list elements
     */
    public static <E> String serialize(Map<String, E> map, Class<E> jsonObjectClass) {
        try {
            return mapperFor(jsonObjectClass).serialize(map);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serialize a map of objects to an OutputStream.
     *
     * @param map             The map of objects to serialize.
     * @param os              The OutputStream to which the list should be serialized
     * @param jsonObjectClass The @JsonObject class of the list elements
     */
    public static <E> void serialize(Map<String, E> map, OutputStream os, Class<E> jsonObjectClass) {
        try {
            mapperFor(jsonObjectClass).serialize(map, os);
        } catch (IOException e) {
            // ignored
            System.out.print(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    /*package*/ static <E> JsonMapper<E> getMapper(Class<E> cls) {
        JsonMapper<E> mapper = OBJECT_MAPPERS.get(cls);
        if (mapper == null) {
            Class<?> mapperClass= CLASS_MAPPERS.get(cls);
            // The only way the mapper wouldn't already be loaded into OBJECT_MAPPERS is if it was compiled separately, but let's handle it anyway
            try {
                if (mapperClass==null)
                {

                  mapperClass = Class.forName(cls.getName() + Constants.MAPPER_CLASS_SUFFIX);
                }
                mapper = (JsonMapper<E>) mapperClass.newInstance();
                OBJECT_MAPPERS.put(cls, mapper);
            } catch (Exception ignored) {
            }
        }
        return mapper;
    }

    @SuppressWarnings("unchecked")
    private static <E> JsonMapper<E> getMapper(ParameterizedType<E> type, SimpleArrayMap<ParameterizedType, JsonMapper> partialMappers) {
        if (type.typeParameters.size() == 0) {
            return getMapper((Class<E>) type.rawType);
        }

        if (partialMappers == null) {
            partialMappers = new SimpleArrayMap<ParameterizedType, JsonMapper>();
        }

        if (partialMappers.containsKey(type)) {
            return partialMappers.get(type);
        } else if (PARAMETERIZED_OBJECT_MAPPERS.containsKey(type)) {
            return PARAMETERIZED_OBJECT_MAPPERS.get(type);
        } else {
            try {
                Class<?> mapperClass = Class.forName(type.rawType.getName() + Constants.MAPPER_CLASS_SUFFIX);
                Constructor constructor = mapperClass.getDeclaredConstructors()[0];
                Object[] args = new Object[2 + type.typeParameters.size()];
                args[0] = type;
                args[args.length - 1] = partialMappers;
                for (int i = 0; i < type.typeParameters.size(); i++) {
                    args[i + 1] = type.typeParameters.get(i);
                }
                JsonMapper<E> mapper = (JsonMapper<E>) constructor.newInstance(args);
                PARAMETERIZED_OBJECT_MAPPERS.put(type, mapper);
                return mapper;
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    /**
     * Returns whether or not LoganSquare can handle a given class.
     *
     * @param cls The class for which support is being checked.
     */
    @SuppressWarnings("unchecked")
    public static boolean supports(Class cls) {
        return getMapper(cls) != null;
    }

    /**
     * Returns whether or not LoganSquare can handle a given ParameterizedType.
     *
     * @param type The ParameterizedType for which support is being checked.
     */
    @SuppressWarnings("unchecked")
    public static boolean supports(ParameterizedType type) {
        return getMapper(type, null) != null;
    }

    /**
     * Returns a JsonMapper for a given class that has been annotated with @JsonObject.
     *
     * @param cls The class for which the JsonMapper should be fetched.
     */
    public static <E> JsonMapper<E> mapperFor(Class<E> cls) throws NoSuchMapperException {
        JsonMapper<E> mapper = getMapper(cls);

        if (mapper == null) {
            throw new NoSuchMapperException(cls);
        } else {
            return mapper;
        }
    }

    /**
     * Returns a JsonMapper for a given class that has been annotated with @JsonObject.
     *
     * @param type The ParameterizedType for which the JsonMapper should be fetched.
     */
    @SuppressWarnings("unchecked")
    public static <E> JsonMapper<E> mapperFor(ParameterizedType<E> type) throws NoSuchMapperException {
        return mapperFor(type, null);
    }

    public static <E> JsonMapper<E> mapperFor(ParameterizedType<E> type, SimpleArrayMap<ParameterizedType, JsonMapper> partialMappers) throws NoSuchMapperException {
        JsonMapper<E> mapper = getMapper(type, partialMappers);
        if (mapper == null) {
            throw new NoSuchMapperException(type.rawType);
        } else {
            return mapper;
        }
    }

    /**
     * Returns a TypeConverter for a given class.
     *
     * @param cls The class for which the TypeConverter should be fetched.
     */
    @SuppressWarnings("unchecked")
    public static <E> TypeConverter<E> typeConverterFor(Class<E> cls) throws NoSuchTypeConverterException {
        TypeConverter<E> typeConverter = TYPE_CONVERTERS.get(cls);
        if (typeConverter == null) {
            throw new NoSuchTypeConverterException(cls);
        }
        return typeConverter;
    }

    /**
     * Register a new TypeConverter for parsing and serialization.
     *
     * @param cls       The class for which the TypeConverter should be used.
     * @param converter The TypeConverter
     */
    public static <E> void registerTypeConverter(Class<E> cls, TypeConverter<E> converter) {
        TYPE_CONVERTERS.put(cls, converter);
    }

    /**
     * list to response
     *
     * @param list
     * @param <E>
     * @return {"code":0,"data":[{"description":"xxxxx"},{"description":"xxxxx"}]}
     */
    public static <E> String serializeListSimple(List<E> list) {

        try {
            SimpleListResponseMapper jsonObjectMapper = (SimpleListResponseMapper) mapperFor(SimpleListResponse.class);
            return jsonObjectMapper.serialize(list);
        } catch (IOException e) {
            return null;
        }
    }
}
