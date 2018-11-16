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

import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class TaskSpec {

  @Nullable
  @JsonProperty("ContainerSpec")
  public ContainerSpec containerSpec;

  @Nullable
  @JsonProperty("Resources")
  public ResourceRequirements resources;

  @Nullable
  @JsonProperty("RestartPolicy")
  public RestartPolicy restartPolicy;

  @Nullable
  @JsonProperty("Placement")
  public Placement placement;

  @Nullable
  @JsonProperty("Networks")
  public ImmutableList<NetworkAttachmentConfig> networks;

  @Nullable
  @JsonProperty("LogDriver")
  public Driver logDriver;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder containerSpec(ContainerSpec containerSpec) { build.containerSpec = containerSpec; return this; };

    public  Builder resources(ResourceRequirements resources) { build.resources = resources; return this; };

    public  Builder restartPolicy(RestartPolicy restartPolicy) { build.restartPolicy = restartPolicy; return this; };

    public  Builder placement(Placement placement) { build.placement = placement; return this; };

    public  Builder networks(NetworkAttachmentConfig... networks) { build.networks = safeList(networks, true); return this; };

    public  Builder networks(List<NetworkAttachmentConfig> networks) { build.networks = safeList(networks, true); return this; };

    public  Builder logDriver(Driver logDriver) { build.logDriver = logDriver; return this; };

    public TaskSpec build = new TaskSpec();
  }

  public static TaskSpec.Builder builder() {
    return new TaskSpec.Builder();
  }

  @JsonCreator
  static TaskSpec create(
      @JsonProperty("ContainerSpec") final ContainerSpec containerSpec,
      @JsonProperty("Resources") final ResourceRequirements resources,
      @JsonProperty("RestartPolicy") final RestartPolicy restartPolicy,
      @JsonProperty("Placement") final Placement placement,
      @JsonProperty("Networks") final List<NetworkAttachmentConfig> networks,
      @JsonProperty("LogDriver") final Driver logDriver) {
    return builder()
        .containerSpec(containerSpec)
        .resources(resources)
        .restartPolicy(restartPolicy)
        .placement(placement)
        .logDriver(logDriver)
        .networks(networks)
        .build;
  }
}
