// Copyright 2025 Synadia Communications, Inc.
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

package io.synadia.json;

import io.ResourceUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static io.synadia.json.JsonValue.*;
import static io.synadia.json.JsonValueUtils.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "DataFlowIssue"})
public final class BenchmarkUnitCoverage {
    private static final String TEST_JSON = ResourceUtils.resourceAsString("test.json");
    private static final JsonValue TEST_JV = JsonParser.parseUnchecked(TEST_JSON);
    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(99);

    private static final String STRING = "string";
    private static final String INTEGER = "integer";
    private static final String LONG = "long";
    private static final String BIG_DECIMAL = "big_decimal";
    private static final String BOOL = "bool";
    private static final String DATE = "date";
    private static final String NANOS = "nanos";
    private static final String BASE_64_BASIC = "base_64_basic";
    private static final String BASE_64_URL = "base_64_url";
    private static final String MAP = "map";
    private static final String ARRAY = "array";
    private static final String MMAP = "mmap";
    private static final String SMAP = "smap";
    private static final String SLIST = "slist";
    private static final String MLIST = "mlist";
    private static final String ILIST = "ilist";
    private static final String LLIST = "llist";
    private static final String NLIST = "nlist";
    private static final String NOT_A_KEY = "not-a-key";

    public static long unitCoverage() {
        long start = System.nanoTime();

        // testRead
        read(TEST_JV, STRING, null, v -> v);
        read(null, NOT_A_KEY, null, v -> v);
        read(EMPTY_ARRAY, NOT_A_KEY, null, v -> v);
        read(TRUE, NOT_A_KEY, null, v -> v);
        read(FALSE, NOT_A_KEY, null, v -> v);
        read(NULL, NOT_A_KEY, null, v -> v);

        // testReadStrings
        readString(TEST_JV, STRING);
        readBytes(TEST_JV, STRING);
        readBytes(TEST_JV, STRING, StandardCharsets.UTF_8);
        readString(TEST_JV, DATE);
        readString(TEST_JV, BASE_64_BASIC);
        readString(TEST_JV, BASE_64_URL);
        readDate(TEST_JV, DATE);
        readBase64Basic(TEST_JV, BASE_64_BASIC);
        readBase64Url(TEST_JV, BASE_64_URL);
        readBase64Basic(TEST_JV, INTEGER);
        readBase64Url(TEST_JV, INTEGER);
        readString(TEST_JV, INTEGER);
        readString(TEST_JV, LONG);
        readString(TEST_JV, BOOL);
        readString(TEST_JV, MAP);
        readString(TEST_JV, ARRAY);
        readString(TEST_JV, NOT_A_KEY);
        readString(TEST_JV, INTEGER, STRING);
        readString(TEST_JV, LONG, STRING);
        readString(TEST_JV, BOOL, STRING);
        readString(TEST_JV, MAP, STRING);
        readString(TEST_JV, ARRAY, STRING);
        readString(TEST_JV, NOT_A_KEY, STRING);
        readString(TEST_JV, NOT_A_KEY);
        readString(TEST_JV, INTEGER);
        readString(TEST_JV, STRING, STRING);
        readString(TEST_JV, NOT_A_KEY, STRING);
        readString(TEST_JV, INTEGER, STRING);
        readString(null, NOT_A_KEY, STRING);

        // testReadInteger
        readInteger(TEST_JV, INTEGER);
        readInteger(TEST_JV, STRING);
        readInteger(TEST_JV, BOOL);
        readInteger(TEST_JV, MAP);
        readInteger(TEST_JV, ARRAY);
        readInteger(TEST_JV, NOT_A_KEY);

        readInteger(TEST_JV, STRING, 99);
        readInteger(TEST_JV, BOOL, 99);
        readInteger(TEST_JV, MAP, 99);
        readInteger(TEST_JV, ARRAY, 99);
        readInteger(TEST_JV, NOT_A_KEY, 99);

        readInteger(TEST_JV, INTEGER, 99);
        readInteger(TEST_JV, STRING, 99);
        readInteger(TEST_JV, BOOL, 99);
        readInteger(TEST_JV, MAP, 99);
        readInteger(TEST_JV, ARRAY, 99);
        readInteger(TEST_JV, NOT_A_KEY, 99);

        // testReadLong
        readLong(TEST_JV, INTEGER);
        readLong(TEST_JV, LONG);
        readLong(TEST_JV, STRING);
        readLong(TEST_JV, BOOL);
        readLong(TEST_JV, MAP);
        readLong(TEST_JV, ARRAY);
        readLong(TEST_JV, NOT_A_KEY);
        readLong(TEST_JV, STRING, 99);
        readLong(TEST_JV, BOOL, 99);
        readLong(TEST_JV, MAP, 99);
        readLong(TEST_JV, ARRAY, 99);
        readLong(TEST_JV, NOT_A_KEY, 99);
        readLong(TEST_JV, INTEGER, 99);
        readLong(TEST_JV, LONG, 99);
        readLong(TEST_JV, STRING, 99);
        readLong(TEST_JV, BOOL, 99);
        readLong(TEST_JV, MAP, 99);
        readLong(TEST_JV, ARRAY, 99);
        readLong(TEST_JV, NOT_A_KEY, 99);

        // testReadBoolean
        readBoolean(TEST_JV, BOOL);
        readBoolean(TEST_JV, STRING);
        readBoolean(TEST_JV, INTEGER);
        readBoolean(TEST_JV, LONG);
        readBoolean(TEST_JV, MAP);
        readBoolean(TEST_JV, ARRAY);
        readBoolean(TEST_JV, NOT_A_KEY);
        readBoolean(TEST_JV, BOOL, true);
        readBoolean(TEST_JV, STRING, true);
        readBoolean(TEST_JV, INTEGER, true);
        readBoolean(TEST_JV, LONG, true);
        readBoolean(TEST_JV, MAP, true);
        readBoolean(TEST_JV, ARRAY, true);
        readBoolean(TEST_JV, NOT_A_KEY, true);
        readBoolean(TEST_JV, STRING, false);
        readBoolean(TEST_JV, INTEGER, false);
        readBoolean(TEST_JV, LONG, false);
        readBoolean(TEST_JV, MAP, false);
        readBoolean(TEST_JV, ARRAY, false);
        readBoolean(TEST_JV, NOT_A_KEY, false);

        // testReadDate
        readDate(TEST_JV, DATE);
        readDate(TEST_JV, BOOL);
        readDate(TEST_JV, MAP);
        readDate(TEST_JV, ARRAY);
        readDate(TEST_JV, NOT_A_KEY);

        // testReadNanosAsDuration
        readNanosAsDuration(TEST_JV, NANOS);
        readNanosAsDuration(TEST_JV, STRING);
        readNanosAsDuration(TEST_JV, BOOL);
        readNanosAsDuration(TEST_JV, MAP);
        readNanosAsDuration(TEST_JV, ARRAY);
        readNanosAsDuration(TEST_JV, NOT_A_KEY);
        readNanosAsDuration(TEST_JV, NANOS, DEFAULT_DURATION);
        readNanosAsDuration(TEST_JV, STRING, DEFAULT_DURATION);
        readNanosAsDuration(TEST_JV, BOOL, DEFAULT_DURATION);
        readNanosAsDuration(TEST_JV, MAP, DEFAULT_DURATION);
        readNanosAsDuration(TEST_JV, ARRAY, DEFAULT_DURATION);
        readNanosAsDuration(TEST_JV, NOT_A_KEY, DEFAULT_DURATION);

        // testObjectAndMaps
        readMapObjectOrNull(TEST_JV, SMAP);
        readMapMapOrNull(TEST_JV, SMAP);
        readMapMapOrEmpty(TEST_JV, SMAP);
        readMapObjectOrNull(TEST_JV, MMAP);
        readMapObjectOrEmpty(TEST_JV, MMAP);
        readMapMapOrNull(TEST_JV, MMAP);
        readMapMapOrEmpty(TEST_JV, MMAP);
        readStringMapOrNull(TEST_JV, MMAP);
        readStringMapOrEmpty(TEST_JV, MMAP);

        // testArrays
        listOfOrNull(null, jv -> jv);
        listOfOrNull(readValue(TEST_JV, STRING), jv -> jv);
        listOfOrNull(readValue(TEST_JV, SLIST), jv -> jv);
        listOfOrEmpty(null, jv -> jv);
        listOfOrEmpty(readValue(TEST_JV, STRING), jv -> jv);
        listOfOrEmpty(readValue(TEST_JV, SLIST), jv -> jv);
        readArrayOrNull(TEST_JV, STRING);
        readArrayOrEmpty(TEST_JV, STRING);
        readArrayOrNull(TEST_JV, SLIST);
        readArrayOrEmpty(TEST_JV, SLIST);
        readArrayOrNull(TEST_JV, MLIST);
        readArrayOrEmpty(TEST_JV, MLIST);
        readStringListOrNull(TEST_JV, STRING);
        readStringListOrEmpty(TEST_JV, STRING);
        readStringListOrNull(TEST_JV, SLIST);
        readStringListOrEmpty(TEST_JV, SLIST);
        readStringListOrNull(TEST_JV, MLIST);
        readStringListOrEmpty(TEST_JV, MLIST);
        readIntegerListOrNull(TEST_JV, STRING);
        readIntegerListOrEmpty(TEST_JV, STRING);
        readIntegerListOrNull(TEST_JV, SLIST);
        readIntegerListOrEmpty(TEST_JV, SLIST);
        readIntegerListOrNull(TEST_JV, LLIST);
        readIntegerListOrEmpty(TEST_JV, LLIST);
        readIntegerListOrNull(TEST_JV, MLIST);
        readIntegerListOrEmpty(TEST_JV, MLIST);
        readIntegerListOrNull(TEST_JV, ILIST);
        readIntegerListOrEmpty(TEST_JV, ILIST);
        readLongListOrNull(TEST_JV, STRING);
        readLongListOrEmpty(TEST_JV, STRING);
        readLongListOrNull(TEST_JV, SLIST);
        readLongListOrEmpty(TEST_JV, SLIST);
        readLongListOrNull(TEST_JV, MLIST);
        readLongListOrEmpty(TEST_JV, MLIST);
        readLongListOrNull(TEST_JV, ILIST);
        readLongListOrEmpty(TEST_JV, ILIST);
        readLongListOrNull(TEST_JV, LLIST);
        readLongListOrEmpty(TEST_JV, LLIST);
        readNanosAsDurationListOrNull(TEST_JV, STRING);
        readNanosAsDurationListOrEmpty(TEST_JV, STRING);
        readNanosAsDurationListOrNull(TEST_JV, NLIST);
        readNanosAsDurationListOrEmpty(TEST_JV, NLIST);

        // testNotFoundOrWrongType() {
        validateNotFoundOrWrongType(STRING, false, true, true, true, true, true, true);
        validateNotFoundOrWrongType(INTEGER, true, true, false, false, true, true, true);
        validateNotFoundOrWrongType(LONG, true, true, true, false, true, true, true);
        validateNotFoundOrWrongType(BOOL, true, true, true, true, false, true, true);
        validateNotFoundOrWrongType(DATE, false, false, true, true, false, true, true);
        validateNotFoundOrWrongType(BASE_64_BASIC, false, true, true, true, true, true, true);
        validateNotFoundOrWrongType(BIG_DECIMAL, true, true, true, true, true, true, true);
        validateNotFoundOrWrongType(MAP, true, true, true, true, true, false, true);
        validateNotFoundOrWrongType(ARRAY, true, true, true, true, true, true, false);
        validateNotFoundOrWrongType(SMAP, true, true, true, true, true, false, true);
        validateNotFoundOrWrongType(SLIST, true, true, true, true, true, true, false);
        validateNotFoundOrWrongType(NOT_A_KEY, true, true, true, true, true, true, true);

        // testGetIntLong
        JsonValue x = readValue(TEST_JV, STRING);
        JsonValue i = new JsonValue(Integer.MAX_VALUE);
        JsonValue li = new JsonValue((long)Integer.MAX_VALUE);
        JsonValue lmax = new JsonValue(Long.MAX_VALUE);
        JsonValue lmin = new JsonValue(Long.MIN_VALUE);
        getInt(i, -1);
        getInt(li, -1);
        getInt(x, -1);
        getInt(JsonValue.NULL, -1);
        getInt(EMPTY_MAP, -1);
        getInt(EMPTY_ARRAY, -1);
        getInteger(i);
        getInteger(li);
        getInteger(x);
        getInteger(lmax);
        getInteger(lmin);
        getInteger(JsonValue.NULL);
        getInteger(EMPTY_MAP);
        getInteger(EMPTY_ARRAY);
        getLong(i);
        getLong(li);
        getLong(lmax);
        getLong(lmin);
        getLong(x);
        getLong(JsonValue.NULL);
        getLong(EMPTY_MAP);
        getLong(EMPTY_ARRAY);
        getLong(i, -1);
        getLong(li, -1);
        getLong(lmax, -1);
        getLong(lmin, -1);
        getLong(x, -1);
        getLong(JsonValue.NULL, -1);
        getLong(EMPTY_MAP, -1);
        getLong(EMPTY_ARRAY, -1);

        return System.nanoTime() - start;
    }

    private static void validateNotFoundOrWrongType(
        String key,
        boolean notString,
        boolean notDate,
        boolean notInteger,
        boolean notLong,
        boolean notBoolean,
        boolean notMap,
        boolean notArray)
    {
        JsonValue jv = readValue(TEST_JV, key);
        if (notString) {
            readBytes(TEST_JV, key);
            readBytes(TEST_JV, key, StandardCharsets.UTF_8);
            readDate(TEST_JV, key);
        }
        else {
            readBytes(TEST_JV, key);
            readBytes(TEST_JV, key, StandardCharsets.UTF_8);
        }
        if (notDate) {
            if (jv == null || jv.string == null) {
                readDate(TEST_JV, key);
            }
        }
        if (notInteger) {
            readInteger(TEST_JV, key);
            readInteger(TEST_JV, key, -1);
        }
        if (notLong) {
            readLong(TEST_JV, key);
            readLong(TEST_JV, key, -1);
        }
        if (notBoolean) {
            readBoolean(TEST_JV, key);
            readBoolean(TEST_JV, key, false);
        }

        if (notMap) {
            readMapObjectOrNull(TEST_JV, key);
            readMapObjectOrEmpty(TEST_JV, key);
            readMapMapOrNull(TEST_JV, key);
            readMapMapOrEmpty(TEST_JV, key);
            readStringMapOrNull(TEST_JV, key);
            readStringMapOrEmpty(TEST_JV, key);
        }
        if (notArray) {
            readArrayOrNull(TEST_JV, key);
            readArrayOrEmpty(TEST_JV, key);
        }
    }
}
