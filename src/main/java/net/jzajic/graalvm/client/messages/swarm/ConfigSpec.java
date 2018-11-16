/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 - 2017 Spotify AB
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
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ConfigSpec {

  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("Data")
  public String data;

  public static ConfigSpec.Builder builder() {
    return new ConfigSpec.Builder();
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  ConfigSpec.Builder name(String name) { build.name = name; return this; };

    public  ConfigSpec.Builder labels(Map<String, String> labels) { build.labels = safeMap(labels); return this; };

    /**
     * Base64-url-safe-encoded secret data.
     *
     * @param data the config data.
     * @return the builder
     */
    public  ConfigSpec.Builder data(String data) { build.data = data; return this; };

    public ConfigSpec build = new ConfigSpec();
  }

  @JsonCreator
  static ConfigSpec create(
      @JsonProperty("Name") String name,
      @JsonProperty("Labels") Map<String, String> labels,
      @JsonProperty("Data") String data) {
    return builder()
        .name(name)
        .labels(labels)
        .data(data)
        .build;
  }
}
