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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class to build a JsonValue for an array
 */
public class ArrayBuilder implements JsonSerializable {

    /**
     * The JsonValue backing this ArrayBuilder
     */
    @NonNull
    public final JsonValue jv;

    /**
     * Get a new instance of ArrayBuilder
     */
    public ArrayBuilder() {
        jv = new JsonValue(new ArrayList<>());
    }

    /**
     * Get an instance of ArrayBuilder
     * @return an ArrayBuilder instance
     */
    @NonNull
    public static ArrayBuilder instance() {
        return new ArrayBuilder();
    }

    /**
     * Add an object to the array. The object is converted to a JsonValue if it isn't one already.
     * @param o the object
     * @return the builder
     */
    @NonNull
    public ArrayBuilder add(@Nullable Object o) {
        //noinspection DataFlowIssue // NO ISSUE, WE KNOW jv.array is NOT NULL
        jv.array.add(JsonValue.instance(o));
        return this;
    }

    /**
     * Add all items in the collection to the array, unless an item is null;
     * @param c the collection
     * @return the builder
     */
    @NonNull
    public ArrayBuilder addItems(@Nullable Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                //noinspection DataFlowIssue // NO ISSUE, WE KNOW jv.array is NOT NULL
                jv.array.add(JsonValue.instance(o));
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String toJson() {
        return jv.toJson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public JsonValue toJsonValue() {
        return jv;
    }
}
