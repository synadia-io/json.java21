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

import java.time.ZonedDateTime;
import java.util.List;

import static io.nats.client.support.ApiConstants.*;
import static io.synadia.json.JsonValueUtils.*;
import static io.synadia.json.JsonWriteUtils.*;

public abstract class SourceBase implements JsonSerializable {
    private final String name;
    private final long startSeq;
    private final ZonedDateTime startTime;
    private final String filterSubject;
    private final External external;
    private final List<SubjectTransform> subjectTransforms;

    SourceBase(JsonValue jv) {
        name = readString(jv, NAME);
        startSeq = readLong(jv, OPT_START_SEQ, 0);
        startTime = readDate(jv, OPT_START_TIME);
        filterSubject = readString(jv, FILTER_SUBJECT);
        external = External.optionalInstance(readValue(jv, EXTERNAL));
        subjectTransforms = SubjectTransform.optionalListOf(readValue(jv, SUBJECT_TRANSFORMS));
    }

    /**
     * Returns a JSON representation of this mirror
     * @return json mirror json string
     */
    @Override
    @NonNull
    public String toJson() {
        StringBuilder sb = beginJson();
        addField(sb, NAME, name);
        addFieldWhenGreaterThan(sb, OPT_START_SEQ, startSeq, 0);
        addField(sb, OPT_START_TIME, startTime);
        addField(sb, FILTER_SUBJECT, filterSubject);
        addField(sb, EXTERNAL, external);
        addJsons(sb, SUBJECT_TRANSFORMS, subjectTransforms);
        return endJson(sb).toString();
    }
}
