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

import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableMap;

import net.jzajic.graalvm.client.MapBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ServiceSpec {

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public Map<String, String> labels;

  @JsonProperty("TaskTemplate")
  public TaskSpec taskTemplate;

  @Nullable
  @JsonProperty("Mode")
  public ServiceMode mode;

  @Nullable
  @JsonProperty("UpdateConfig")
  public UpdateConfig updateConfig;

  @Nullable
  @JsonProperty("Networks")
  public ImmutableList<NetworkAttachmentConfig> networks;

  @Nullable
  @JsonProperty("EndpointSpec")
  public EndpointSpec endpointSpec;

  public  static class Builder extends SwarmAbstractBuilder {

  	public ServiceSpec build = new ServiceSpec();
  	
    public  Builder name(String name) { build.name = name; return this; };

    private MapBuilder<String, String> labelsBuilder = nullableMapBuilder(builder -> build.labels = builder.immutableView());

    public Builder addLabel(final String label, final String value) {
      labelsBuilder.put(label, value);
      return this;
    }

    public  Builder labels(Map<String, String> labels) { labelsBuilder.putAll(labels); return this; };

		public  Builder taskTemplate(TaskSpec taskTemplate) { build.taskTemplate = taskTemplate; return this; };

    public  Builder mode(ServiceMode mode) { build.mode = mode; return this; };

    public  Builder updateConfig(UpdateConfig updateConfig) { build.updateConfig = updateConfig; return this; };

    public  Builder networks(NetworkAttachmentConfig... networks) { build.networks = safeList(networks, true); return this; };

    public  Builder networks(List<NetworkAttachmentConfig> networks) { build.networks = safeList(networks, true); return this; };

    public  Builder endpointSpec(EndpointSpec endpointSpec) { build.endpointSpec = endpointSpec; return this; };
    
  }

  public static ServiceSpec.Builder builder() {
    return new ServiceSpec.Builder();
  }

  @JsonCreator
  static ServiceSpec create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("TaskTemplate") final TaskSpec taskTemplate,
      @JsonProperty("Mode") final ServiceMode mode,
      @JsonProperty("UpdateConfig") final UpdateConfig updateConfig,
      @JsonProperty("Networks") final List<NetworkAttachmentConfig> networks,
      @JsonProperty("EndpointSpec") final EndpointSpec endpointSpec) {
    return builder()
        .name(name)
        .labels(labels)
        .taskTemplate(taskTemplate)
        .mode(mode)
        .updateConfig(updateConfig)
        .endpointSpec(endpointSpec)
        .networks(networks)
        .build;
  }
}
