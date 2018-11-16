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

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.jzajic.graalvm.client.messages.AbstractBuilder;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class EngineConfig {

  @JsonProperty("EngineVersion")
  public String engineVersion;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("Plugins")
  public ImmutableList<EnginePlugin> plugins;

  @JsonCreator
  static EngineConfig create(@JsonProperty("EngineVersion") final String engineVersion,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Plugins") final List<EnginePlugin> plugins) {
    final ImmutableMap<String, String> labelsT = AbstractBuilder.safeMap(labels);
    
    final ImmutableList<EnginePlugin> pluginsT = AbstractBuilder.safeList(plugins);
    return new EngineConfig(engineVersion, labelsT, pluginsT);
  }

	public EngineConfig(String engineVersion, ImmutableMap<String, String> labels, ImmutableList<EnginePlugin> plugins) {
		super();
		this.engineVersion = engineVersion;
		this.labels = labels;
		this.plugins = plugins;
	}

}
