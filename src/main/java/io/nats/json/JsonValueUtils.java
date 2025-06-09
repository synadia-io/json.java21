// Copyright 2023-2025 The NATS Authors
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.nats.json;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

import static io.nats.json.JsonValue.*;

/**
 * Utilities around JsonValue.
 */
public abstract class JsonValueUtils {

    private JsonValueUtils() {} /* ensures cannot be constructed */

    /**
     * Interface allowing the ability to generically extract a value
     * @param <T> the output type
     */
    public interface JsonValueSupplier<T> {
        T get(JsonValue v);
    }

    /**
     * Read a value generically
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param valueSupplier the generic supplier that converts the object to the output type
     * @return the value represented by the key
     * @param <T> the output type
     */
    public static <T> T read(JsonValue jv, String key, JsonValueSupplier<T> valueSupplier) {
        JsonValue jvv = jv == null || jv.type != Type.MAP ? null : jv.map.get(key);
        return valueSupplier.get(jvv);
    }

    /**
     * Read a key's value, without assuming it's type
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The JsonValue or null if the key is not found
     */
    public static JsonValue readValue(JsonValue jv, String key) {
        return read(jv, key, v -> v);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.STRING,
     * If the key is not found or the type is not STRING, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the string or null
     */
    public static String readString(JsonValue jv, String key) {
        return read(jv, key, v -> v == null ? null : v.string);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.STRING,
     * If the key is not found or the type is not STRING, the supplied default is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param dflt the default value
     * @return the string or the default
     */
    public static String readString(JsonValue jv, String key, String dflt) {
        return read(jv, key, v -> v == null || v.type != Type.STRING ? dflt : v.string);
    }

    /**
     * Read a key's int value expecting the value to be of type JsonValue.Type.INTEGER,
     * or of type JsonValue.Type.LONG with a value in the range of integer.
     * If the key is not found or the value is not an integer, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the integer or null
     */
    public static Integer readInteger(JsonValue jv, String key) {
        return read(jv, key, v -> v == null ? null : getInteger(v));
    }

    /**
     * Read a key's int value expecting the value to be of type JsonValue.Type.INTEGER,
     * or of type JsonValue.Type.LONG with a value in the range of integer.
     * If the key is not found or the value is not an integer, the supplied default is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param dflt the default value
     * @return the integer or the default
     */
    public static int readInteger(JsonValue jv, String key, int dflt) {
        return read(jv, key, v -> {
            if (v != null) {
                Integer i = getInteger(v);
                if (i != null) {
                    return i;
                }
            }
            return dflt;
        });
    }

    /**
     * Read a key's int value expecting the value to be of type JsonValue.Type.INTEGER,
     * or of type JsonValue.Type.LONG.
     * If the key is not found or the value is not an integer or long, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the long or null
     */
    public static Long readLong(JsonValue jv, String key) {
        return read(jv, key, v -> v == null ? null : getLong(v));
    }

    /**
     * Read a key's int value expecting the value to be of type JsonValue.Type.INTEGER,
     * or of type JsonValue.Type.LONG.
     * If the key is not found or the value is not an integer or long, the default is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param dflt the default value
     * @return the long or the default
     */
    public static long readLong(JsonValue jv, String key, long dflt) {
        return read(jv, key, v -> {
            if (v != null) {
                Long l = getLong(v);
                if (l != null) {
                    return l;
                }
            }
            return dflt;
        });
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.BOOL
     * <p>If the key is not found or the type is not BOOL, false is returned.</p>
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the value or false
     */
    public static boolean readBoolean(JsonValue jv, String key) {
        return readBoolean(jv, key, false);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.BOOL
     * <p>If the key is not found or the type is not BOOL, the default returned.</p>
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param dflt the default value
     * @return the value or the default
     */
    public static Boolean readBoolean(JsonValue jv, String key, Boolean dflt) {
        return read(jv, key,
            v -> v != null && v.type == Type.BOOL ? v.bool : dflt);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.STRING,
     * and then parses that string to a ZonedDateTime.
     * <p>If the key is not found or the type is not STRING, null is returned.</p>
     * <p>If the string is found but is not parseable by ZonedDateTime, a DateTimeParseException is thrown</p>
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the string or null
     */
    public static ZonedDateTime readDate(JsonValue jv, String key) {
        String s = readString(jv, key);
        return s == null ? null : DateTimeUtils.parseDateTimeThrowParseError(s);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.INTEGER
     * or JsonValue.Type.LONG, and then converts that to a Duration assuming the number
     * represents nanoseconds
     * <p>If the key is not found or the type is not INTEGER or LONG, null is returned.</p>
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return the Duration or null
     */
    public static Duration readNanosAsDuration(JsonValue jv, String key) {
        Long l = readLong(jv, key);
        return l == null ? null : Duration.ofNanos(l);
    }

    /**
     * Read a key's string value expecting the value to be of type JsonValue.Type.INTEGER
     * or JsonValue.Type.LONG, and then converts that to a Duration assuming the number
     * represents nanoseconds
     * <p>If the key is not found or the type is not INTEGER or LONG, null is returned.</p>
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @param dflt the default value
     * @return the Duration or the default
     */
    public static Duration readNanosAsDuration(JsonValue jv, String key, Duration dflt) {
        Long l = readLong(jv, key);
        return l == null ? dflt : Duration.ofNanos(l);
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static byte[] readBytes(JsonValue jv, String key) {
        String s = readString(jv, key);
        return s == null ? null : s.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static byte[] readBase64(JsonValue jv, String key) {
        String b64 = readString(jv, key);
        return b64 == null ? null : Encoding.base64BasicDecode(b64);
    }

    /**
     * Read a key's map value as a generic JsonValue assuming the value is a JSON object (a map)
     * If the key is not found or the value type is not MAP, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The JsonValue for the MAP object or null
     */
    public static JsonValue readMapObjectOrNull(JsonValue jv, String key) {
        return read(jv, key, v -> v == null || v.type != Type.MAP ? null : v);
    }

    /**
     * Read a key's map value as a generic JsonValue assuming the value is a JSON object (a map)
     * If the key is not found or the value type is not MAP, EMPTY_MAP is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The JsonValue for the MAP object or EMPTY_MAP
     */
    public static JsonValue readMapObjectOrEmpty(JsonValue jv, String key) {
        return read(jv, key, v -> v == null || v.type != Type.MAP ? EMPTY_MAP : v);
    }

    /**
     * Read a key's map value as a Map of String to JsonValue
     * If the key is not found or the value type is not MAP, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The JsonValue for the MAP object or null
     */
    public static Map<String, JsonValue> readMapMapOrNull(JsonValue jv, String key) {
        JsonValue jvv = readMapObjectOrNull(jv, key);
        return jvv == null ? null : jvv.map;
    }

    /**
     * Read a key's map value as a Map of String to JsonValue
     * If the key is not found or the value type is not MAP, EMPTY_MAP is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The JsonValue for the MAP object or EMPTY_MAP
     */
    public static Map<String, JsonValue> readMapMapOrEmpty(JsonValue jv, String key) {
        return readMapObjectOrEmpty(jv, key).map;
    }

    /**
     * Read a key's map value as a Map of String to String. This will
     * assume that every value in the looked-up map is a string,
     * otherwise the key will not be included in the returned map.
     * If there is no map for the key, or the map is found but empty,
     * the function returns null.
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The resolved map or null
     */
    public static Map<String, String> readStringMapOrNull(JsonValue jv, String key) {
        Map<String, JsonValue> map = readMapMapOrNull(jv, key);
        return map == null ? null : convertToStringMap(map);
    }

    /**
     * Read a key's map value as a Map of String to String. This will
     * assume that every value in the looked-up map is a string,
     * otherwise the key will not be included in the returned map.
     * If there is no map for the key, or the map is found but empty,
     * the function returns null.
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The resolved map or null
     */
    public static Map<String, String> readStringMapOrEmpty(JsonValue jv, String key) {
        return convertToStringMap(readMapMapOrEmpty(jv, key));
    }

    /**
     * Converts a JsonValue's Map of String to JsonValue to a Map of String to String,
     * filtering out any key whose value is not a string.
     * @param map the input map
     * @return the converted map. Map be empty.
     */
    public static Map<String, String> convertToStringMap(Map<String, JsonValue> map) {
        Map<String, String> temp = new HashMap<>();
        for (String k : map.keySet()) {
            JsonValue value = map.get(k);
            if (value.type == Type.STRING) {
                temp.put(k, value.string);
            }
        }
        return temp;
    }

    /**
     * Read a value expecting it to be of type JsonValue.Type.ARRAY.
     * If the value is not found or the type is not ARRAY, an empty list is returned
     * @param jv the jsonValue that is an array (type is JsonValue.Type.ARRAY)
     * @param processor a function that processes each value in the array
     * @return The List of JsonValues in the array or an empty list if the value was null or not an ARRAY
     */
    public static <T> List<T> listOfOrNull(JsonValue jv, Function<JsonValue, T> processor) {
        if (jv == null || jv.type != Type.ARRAY) {
            return null;
        }
        return fillList(jv, new ArrayList<>(), processor);
    }

    /**
     * Read a value expecting it to be of type JsonValue.Type.ARRAY.
     * If the value is not found or the type is not ARRAY, null is returned
     * @param jv the jsonValue that is an array (type is JsonValue.Type.ARRAY)
     * @param processor a function that processes each value in the array
     * @return The List of JsonValues in the array or null if the value was null or not an ARRAY
     */
    public static <T> List<T> listOfOrEmpty(JsonValue jv, Function<JsonValue, T> processor) {
        if (jv == null || jv.type != Type.ARRAY) {
            return Collections.emptyList();
        }
        return fillList(jv, new ArrayList<>(), processor);
    }

    private static <T> List<T> fillList(JsonValue jv, List<T> target, Function<JsonValue, T> provider) {
        for (JsonValue jvv : jv.array) {
            T t = provider.apply(jvv);
            if (t != null) {
                target.add(t);
            }
        }
        return target;
    }

    /**
     * Read a key's value expecting the value to be of type JsonValue.Type.ARRAY,
     * If the key is not found or the type is not ARRAY, null is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The List of JsonValues in the null
     */
    public static List<JsonValue> readArrayOrNull(JsonValue jv, String key) {
        return read(jv, key, v -> v == null || v.type != Type.ARRAY ? null : v.array);
    }

    /**
     * Read a key's value expecting the value to be of type JsonValue.Type.ARRAY,
     * If the key is not found or the type is not ARRAY, EMPTY_ARRAY is returned
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The List of JsonValues in the array
     */
    public static List<JsonValue> readArrayOrEmpty(JsonValue jv, String key) {
        return read(jv, key, v -> v == null || v.type != Type.ARRAY ? EMPTY_ARRAY.array : v.array);
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<String> readStringListOrNull(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrNull(v, jvv -> jvv.string));
    }

    /**
     * Read a key's value expecting the value to be of type JsonValue.Type.ARRAY,
     * If the key is not found or the type is not ARRAY, an empty list is returned
     * If the value is not a string it is not included in the returned list.
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return The list of String in the array
     */
    public static List<String> readStringListOrEmpty(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrEmpty(v, jvv -> jvv.string));
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Integer> readIntegerListOrNull(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrNull(v, JsonValueUtils::getInteger));
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Integer> readIntegerListOrEmpty(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrEmpty(v, JsonValueUtils::getInteger));
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Long> readLongListOrNull(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrNull(v, JsonValueUtils::getLong));
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Long> readLongListOrEmpty(JsonValue jv, String key) {
        return read(jv, key, v -> listOfOrEmpty(v, JsonValueUtils::getLong));
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Duration> readNanosAsDurationListOrNull(JsonValue jv, String key) {
        List<Long> list = readLongListOrNull(jv, key);
        return list == null ? null : _nanosToDuration(list);
    }

    /**
     *
     * @param jv the jsonValue that is an object (type is JsonValue.Type.MAP)
     * @param key the key to look up
     * @return
     */
    public static List<Duration> readNanosAsDurationListOrEmpty(JsonValue jv, String key) {
        return _nanosToDuration(readLongListOrEmpty(jv, key));
    }

    private static List<Duration> _nanosToDuration(List<Long> list) {
        List<Duration> durations = new ArrayList<>();
        for (Long l : list) {
            durations.add(Duration.ofNanos(l));
        }
        return durations;
    }

    /**
     * Get the int value of the JsonValue
     * @param jv the jsonValue that represents an Integer or a Long with a value in the range of valid Integer
     * @return the Integer value, which may be null
     */
    public static Integer getInteger(JsonValue jv) {
        if (jv.i != null) {
            return jv.i;
        }
        // just in case the number was stored as long, which is unlikely, but I want to handle it
        if (jv.l != null && jv.l <= Integer.MAX_VALUE && jv.l >= Integer.MIN_VALUE) {
            return jv.l.intValue();
        }
        return null;
    }

    /**
     * Get the int value of the JsonValue
     * @param jv the jsonValue that represents an Integer
     * @param dflt the default value to use if the object does not represent an Integer
     * @return the int value or default
     */
    public static int getInt(JsonValue jv, int dflt) {
        Integer i = getInteger(jv);
        return  i == null ? dflt : i;
    }

    /**
     * Get the Long value of the JsonValue
     * @param jv the jsonValue that represents a Long or an Integer
     * @return the Long value, which may be null
     */
    public static Long getLong(JsonValue jv) {
        return jv.l != null ? jv.l : (jv.i != null ? (long)jv.i : null);
    }

    /**
     * Get the long value of the JsonValue
     * @param jv the jsonValue that represents a Long or an Integer
     * @param dflt the default value to use if the object does not represent a Long or Integer
     * @return the long value or default
     */
    public static long getLong(JsonValue jv, long dflt) {
        return jv.l != null ? jv.l : (jv.i != null ? (long)jv.i : dflt);
    }
}
