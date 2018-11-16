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

package net.jzajic.graalvm.client.messages.mount;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import com.google.common.collect.ImmutableMap;

import net.jzajic.graalvm.client.messages.AbstractBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class VolumeOptions {

  @Nullable
  @JsonProperty("NoCopy")
  public Boolean noCopy;

  @Nullable
  @JsonProperty("Labels")
  public Map<String, String> labels;

  @Nullable
  @JsonProperty("DriverConfig")
  public Driver driverConfig;

  public  static class Builder extends AbstractBuilder {

    public  Builder noCopy(Boolean noCopy) { build.noCopy = noCopy; return this; };

    public  Builder labels(Map<String, String> labels) { build.labels = safeMap(labels); return this; };

    private Map<String, String> labelsBuilder = new HashMap<>();

    public Builder addLabel(final String label, final String value) {
      labelsBuilder.put(label, value);
      return this;
    }

    public  Builder driverConfig(Driver driverConfig) { build.driverConfig = driverConfig; return this; };

    public VolumeOptions build;
    
    public Builder() {
			build = new VolumeOptions();
			build.labels = Collections.unmodifiableMap(labelsBuilder);
		}
    
  }

  public static VolumeOptions.Builder builder() {
    return new VolumeOptions.Builder();
  }

  @JsonCreator
  static VolumeOptions create(
      @JsonProperty("NoCopy") final Boolean noCopy,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("DriverConfig") final Driver driverConfig) {
    return builder()
        .noCopy(noCopy)
        .labels(labels)
        .driverConfig(driverConfig)
        .build;
  }
}
