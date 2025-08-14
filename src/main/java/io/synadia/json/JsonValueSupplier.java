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

import org.jspecify.annotations.Nullable;

/**
 * Interface allowing the ability to generically extract a value
 *
 * @param <T> the output type
 */
public interface JsonValueSupplier<T> {
    /**
     * Get the output type from the JsonValue
     *
     * @param valueForKey the value
     * @return the output type
     */
    @Nullable
    T get(JsonValue valueForKey);
}
