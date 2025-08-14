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

package io.nats.client.api;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Stream discard policies
 */
public enum DiscardPolicy {
    New("new"),
    Old("old");

    private final String policy;

    DiscardPolicy(String p) {
        policy = p;
    }

    @Override
    public String toString() {
        return policy;
    }

    private static final Map<String, DiscardPolicy> strEnumHash = new HashMap<>();

    static {
        for (DiscardPolicy env : DiscardPolicy.values()) {
            strEnumHash.put(env.toString(), env);
        }
    }

    @Nullable
    public static DiscardPolicy get(String value) {
        return strEnumHash.get(value);
    }
}
