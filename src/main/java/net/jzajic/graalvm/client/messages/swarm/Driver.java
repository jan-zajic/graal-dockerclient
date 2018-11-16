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
public class Driver {

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Options")
  public Map<String, String> options;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder name(String name) { build.name = name; return this; };

    private Map<String, String> optionsBuilder = new HashMap<>();

    public Builder addOption(final String name, final String value) {
      optionsBuilder.put(name, value);
      return this;
    }

    public  Builder options(Map<String, String> options) { optionsBuilder.clear(); optionsBuilder.putAll(options); return this; };

    public Driver build;
    
    public Builder() {
			build = new Driver();
			build.options = Collections.unmodifiableMap(optionsBuilder);
		}
    
  }

  public static Driver.Builder builder() {
    return new Driver.Builder();
  }

  @JsonCreator
  static Driver create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Options") final Map<String, String> options) {
    final Builder builder = builder()
        .name(name);

    if (options != null) {
      builder.options(options);
    }

    return builder.build;
  }
}
