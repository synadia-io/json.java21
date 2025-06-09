// Copyright 2025 The NATS Authors
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

import java.util.ArrayList;
import java.util.Collection;

public class ArrayBuilder implements JsonSerializable {

    public final JsonValue jv = new JsonValue(new ArrayList<>());

    public static ArrayBuilder instance() {
        return new ArrayBuilder();
    }

    public ArrayBuilder add(Object o) {
        //noinspection DataFlowIssue // NO ISSUE, WE KNOW jv.array is NOT NULL
        jv.array.add(JsonValue.instance(o));
        return this;
    }

    public ArrayBuilder addItems(Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                //noinspection DataFlowIssue // NO ISSUE, WE KNOW jv.array is NOT NULL
                jv.array.add(JsonValue.instance(o));
            }
        }
        return this;
    }

    @Override
    public String toJson() {
        return jv.toJson();
    }

    @Override
    public JsonValue toJsonValue() {
        return jv;
    }
}
