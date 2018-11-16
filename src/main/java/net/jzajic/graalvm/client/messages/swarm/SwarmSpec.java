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
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SwarmSpec {

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("Orchestration")
  public OrchestrationConfig orchestration;

  @Nullable
  @JsonProperty("Raft")
  public RaftConfig raft;

  @Nullable
  @JsonProperty("Dispatcher")
  public DispatcherConfig dispatcher;

  @Nullable
  @JsonProperty("CAConfig")
  public CaConfig caConfig;

  @Nullable
  @JsonProperty("EncryptionConfig")
  public EncryptionConfig encryptionConfig;

  @Nullable
  @JsonProperty("TaskDefaults")
  public TaskDefaults taskDefaults;

  @JsonCreator
  static SwarmSpec create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Orchestration") final OrchestrationConfig orchestration,
      @JsonProperty("Raft") final RaftConfig raft,
      @JsonProperty("Dispatcher") final DispatcherConfig dispatcher,
      @JsonProperty("CAConfig") final CaConfig caConfig,
      @JsonProperty("EncryptionConfig") final EncryptionConfig encryptionConfig,
      @JsonProperty("TaskDefaults") final TaskDefaults taskDefaults) {
    return builder()
        .name(name)
        .labels(labels)
        .orchestration(orchestration)
        .raft(raft)
        .dispatcher(dispatcher)
        .caConfig(caConfig)
        .encryptionConfig(encryptionConfig)
        .taskDefaults(taskDefaults)
        .build;
  }

  public  static class Builder extends SwarmAbstractBuilder {
    public  Builder name(String name) { build.name = name; return this; };

    public  Builder labels(Map<String, String> labels) { build.labels = safeMap(labels); return this; };

    public  Builder orchestration(OrchestrationConfig orchestration) { build.orchestration = orchestration; return this; };

    public  Builder raft(RaftConfig raft) { build.raft = raft; return this; };

    public  Builder dispatcher(DispatcherConfig dispatcher) { build.dispatcher = dispatcher; return this; };

    public  Builder caConfig(CaConfig caConfig) { build.caConfig = caConfig; return this; };

    public  Builder encryptionConfig(EncryptionConfig encryptionConfig) { build.encryptionConfig = encryptionConfig; return this; };

    public  Builder taskDefaults(TaskDefaults taskDefaults) { build.taskDefaults = taskDefaults; return this; };

    public SwarmSpec build = new SwarmSpec();
  }

  public static SwarmSpec.Builder builder() {
    return new SwarmSpec.Builder();
  }
}
