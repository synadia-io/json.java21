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

import io.synadia.json.JsonValue;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static io.nats.client.support.ApiConstants.BYTES;
import static io.nats.client.support.ApiConstants.MSGS;
import static io.synadia.json.JsonValueUtils.readLong;
import static io.synadia.json.JsonValueUtils.readLongListOrEmpty;

/**
 * Information about lost stream data
 */
public class LostStreamData {
    private final List<Long> messages;
    private final Long bytes;

    static LostStreamData optionalInstance(JsonValue vLost) {
        return vLost == null ? null : new LostStreamData(vLost);
    }


    LostStreamData(JsonValue vLost) {
        messages = readLongListOrEmpty(vLost, MSGS);
        bytes = readLong(vLost, BYTES);
    }

    /**
     * Get the lost message ids. May be empty
     * @return the list of message ids
     */
    @NonNull
    public List<Long> getMessages() {
        return messages;
    }

    /**
     * Get the number of bytes that were lost
     * @return the number of lost bytes
     */
    @Nullable
    public Long getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "LostStreamData{" +
            "messages=" + messages +
            ", bytes=" + bytes +
            '}';
    }
}
