/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package net.jzajic.graalvm.client.messages.swarm;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NodeSpec {

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public Map<String, String> labels;

  @JsonProperty("Role")
  public String role;

  @JsonProperty("Availability")
  public String availability;

  public  static class Builder extends SwarmAbstractBuilder {

		public  Builder name(String name) { build.name = name; return this; };

    private Map<String, String> labelsBuilder = new HashMap<>();

    public Builder addLabel(final String label, final String value) {
      labelsBuilder.put(label, value);
      return this;
    }

    public  Builder labels(Map<String, String> labels) { labelsBuilder.putAll(labels); return this; };

    public  Builder role(String role) { build.role = role; return this; };

    public  Builder availability(String availability) { build.availability = availability; return this; };

    public NodeSpec build;
    
    public Builder() {
			build = new NodeSpec();
			build.labels = Collections.unmodifiableMap(labelsBuilder);
		}
  }

  public static NodeSpec.Builder builder() {
    return new NodeSpec.Builder();
  }

  @JsonCreator
  static NodeSpec create(@JsonProperty("Name") final String name,
                         @JsonProperty("Labels") final Map<String, String> labels,
                         @JsonProperty("Role") final String role,
                         @JsonProperty("Availability") final String availability) {
    final Builder builder = builder()
        .name(name)
        .labels(labels)
        .role(role)
        .availability(availability);

    return builder.build;
  }
}
