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
import io.synadia.json.JsonValueUtils;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static io.nats.client.support.ApiConstants.DEST;
import static io.nats.client.support.ApiConstants.SRC;
import static io.synadia.json.JsonValueUtils.readString;
import static io.synadia.json.JsonWriteUtils.*;

/**
 * SubjectTransform
 */
public class SubjectTransform implements JsonSerializable {
    private final String source;
    private final String destination;

    static SubjectTransform optionalInstance(JsonValue vSubjectTransform) {
        return vSubjectTransform == null ? null : new SubjectTransform(vSubjectTransform);
    }

    static List<SubjectTransform> optionalListOf(JsonValue vSubjectTransforms) {
        return JsonValueUtils.listOfOrEmpty(vSubjectTransforms, SubjectTransform::new);
    }

    public SubjectTransform(JsonValue vSubjectTransform) {
        source = readString(vSubjectTransform, SRC);
        destination = readString(vSubjectTransform, DEST);
    }

    /**
     * Get source, the subject matching filter
     * @return the source
     */
    @NonNull
    public String getSource() {
        return source;
    }

    /**
     * Get destination, the SubjectTransform Subject template
     * @return the destination
     */
    @NonNull
    public String getDestination() {
        return destination;
    }

    @Override
    @NonNull
    public String toJson() {
        StringBuilder sb = beginJson();
        addField(sb, SRC, source);
        addField(sb, DEST, destination);
        return endJson(sb).toString();
    }
}
