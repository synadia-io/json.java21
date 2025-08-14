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

import io.synadia.json.JsonSerializable;
import io.synadia.json.JsonValue;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.Duration;

import static io.nats.client.api.ConsumerConfiguration.getOrUnset;
import static io.nats.client.support.ApiConstants.INACTIVE_THRESHOLD;
import static io.nats.client.support.ApiConstants.MAX_ACK_PENDING;
import static io.synadia.json.JsonValueUtils.readInteger;
import static io.synadia.json.JsonValueUtils.readNanosAsDuration;
import static io.synadia.json.JsonWriteUtils.*;

/**
 * ConsumerLimits
 */
public class ConsumerLimits implements JsonSerializable {
    private final Duration inactiveThreshold;
    private final Integer maxAckPending;

    static ConsumerLimits optionalInstance(JsonValue vConsumerLimits) {
        return vConsumerLimits == null ? null : new ConsumerLimits(vConsumerLimits);
    }

    public ConsumerLimits(JsonValue vConsumerLimits) {
        inactiveThreshold = readNanosAsDuration(vConsumerLimits, INACTIVE_THRESHOLD);
        maxAckPending = readInteger(vConsumerLimits, MAX_ACK_PENDING);
    }

    /**
     * Maximum value for inactive_threshold for consumers of this stream. Acts as a default when consumers do not set this value.
     * @return the inactive threshold limit
     */
    @Nullable
    public Duration getInactiveThreshold() {
        return inactiveThreshold;
    }

    /**
     * Maximum value for max_ack_pending for consumers of this stream. Acts as a default when consumers do not set this value.
     * @return maximum ack pending limit
     */
    public long getMaxAckPending() {
        return getOrUnset(maxAckPending);
    }

    @Override
    @NonNull
    public String toJson() {
        StringBuilder sb = beginJson();
        addFieldAsNanos(sb, INACTIVE_THRESHOLD, inactiveThreshold);
        addField(sb, MAX_ACK_PENDING, maxAckPending);
        return endJson(sb).toString();
    }
}
