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


import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ServiceMode {

  @Nullable
  @JsonProperty("Replicated")
  public ReplicatedService replicated;

  @Nullable
  @JsonProperty("Global")
  public GlobalService global;

  public static ServiceMode withReplicas(final long replicas) {
    return ServiceMode.builder()
        .replicated(ReplicatedService.builder().replicas(replicas).build)
        .build;
  }

  public static ServiceMode withGlobal() {
    return ServiceMode.builder().global(GlobalService.builder().build).build;
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder replicated(ReplicatedService replicated) { build.replicated = replicated; return this; };

    public  Builder global(GlobalService global) { build.global = global; return this; };

    public ServiceMode build = new ServiceMode();
    
  }

  public static ServiceMode.Builder builder() {
    return new ServiceMode.Builder();
  }

  @JsonCreator
  static ServiceMode create(
      @JsonProperty("Replicated") final ReplicatedService replicated,
      @JsonProperty("Global") final GlobalService global) {
    return builder()
        .replicated(replicated)
        .global(global)
        .build;
  }
}
