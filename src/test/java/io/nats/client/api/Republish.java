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

import static io.nats.client.support.ApiConstants.*;
import static io.synadia.json.JsonValueUtils.readBoolean;
import static io.synadia.json.JsonValueUtils.readString;
import static io.synadia.json.JsonWriteUtils.*;

/**
 * Republish Configuration
 */
public class Republish implements JsonSerializable {
    private final String source;
    private final String destination;
    private final boolean headersOnly;

    static Republish optionalInstance(JsonValue vRepublish) {
        return vRepublish == null ? null : new Republish(vRepublish);
    }

    Republish(JsonValue vRepublish) {
        source = readString(vRepublish, SRC);
        destination = readString(vRepublish, DEST);
        headersOnly = readBoolean(vRepublish, HEADERS_ONLY, false);
    }

    /**
     * Get source, the Published subject matching filter
     * @return the source
     */
    @NonNull
    public String getSource() {
        return source;
    }

    /**
     * Get destination, the RePublish Subject template
     * @return the destination
     */
    @NonNull
    public String getDestination() {
        return destination;
    }

    /**
     * Get headersOnly, Whether to RePublish only headers (no body)
     * @return headersOnly
     */
    public boolean isHeadersOnly() {
        return headersOnly;
    }

    @Override
    @NonNull
    public String toJson() {
        StringBuilder sb = beginJson();
        addField(sb, SRC, source);
        addField(sb, DEST, destination);
        addField(sb, HEADERS_ONLY, headersOnly);
        return endJson(sb).toString();
    }
}
