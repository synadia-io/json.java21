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

import java.time.ZonedDateTime;
import java.util.List;

import static io.nats.client.support.ApiConstants.*;
import static io.synadia.json.JsonValueUtils.readDate;
import static io.synadia.json.JsonValueUtils.readValue;

/**
 * The StreamInfo class contains information about a JetStream stream.
 */
public class StreamInfo extends ApiResponse<StreamInfo> {

    private final ZonedDateTime createTime;
    private final StreamConfiguration config;
    private final StreamState streamState;
    private final ClusterInfo clusterInfo;
    private final MirrorInfo mirrorInfo;
    private final List<SourceInfo> sourceInfos;
    private final List<StreamAlternate> alternates;
    private final ZonedDateTime timestamp;

    public StreamInfo(JsonValue vStreamInfo) {
        super(vStreamInfo);
        JsonValue jvConfig = readValue(jv, CONFIG);
        config = StreamConfiguration.instance(jvConfig);

        createTime = readDate(jv, CREATED);

        streamState = new StreamState(readValue(jv, STATE));
        clusterInfo = ClusterInfo.optionalInstance(readValue(jv, CLUSTER));
        mirrorInfo = MirrorInfo.optionalInstance(readValue(jv, MIRROR));
        sourceInfos = SourceInfo.optionalListOf(readValue(jv, SOURCES));
        alternates = StreamAlternate.optionalListOf(readValue(jv, ALTERNATES));
        timestamp = readDate(jv, TIMESTAMP);
    }

    /**
     * Gets the stream configuration. Same as getConfig
     * @return the stream configuration.
     */
    @NonNull
    public StreamConfiguration getConfiguration() {
        return config;
    }

    /**
     * Gets the stream configuration. Same as getConfiguration
     * @return the stream configuration.
     */
    @NonNull
    public StreamConfiguration getConfig() {
        return config;
    }

    /**
     * Gets the stream state.
     * @return the stream state
     */
    @NonNull
    public StreamState getStreamState() {
        return streamState;
    }

    /**
     * Gets the creation time of the stream.
     * @return the creation date and time.
     */
    @NonNull
    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    @Nullable
    public MirrorInfo getMirrorInfo() {
        return mirrorInfo;
    }

    @Nullable
    public List<SourceInfo> getSourceInfos() {
        return sourceInfos;
    }

    @Nullable
    public ClusterInfo getClusterInfo() {
        return clusterInfo;
    }

    @Nullable
    public List<StreamAlternate> getAlternates() {
        return alternates;
    }

    /**
     * Gets the server time the info was gathered
     * @return the server gathered timed
     */
    @Nullable // doesn't exist in some versions of the server
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "StreamInfo " + jv;
    }
}
