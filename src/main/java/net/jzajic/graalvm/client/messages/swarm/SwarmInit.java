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

import javax.annotation.Nullable;



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SwarmInit {

  @JsonProperty("ListenAddr")
  public String listenAddr;

  @JsonProperty("AdvertiseAddr")
  public String advertiseAddr;

  @Nullable
  @JsonProperty("ForceNewCluster")
  public Boolean forceNewCluster;

  @Nullable
  @JsonProperty("Spec")
  public SwarmSpec swarmSpec;

  public  static class Builder extends SwarmAbstractBuilder {
    public  Builder listenAddr(String listenAddr) { build.listenAddr = listenAddr; return this; };

    public  Builder advertiseAddr(String advertiseAddr) { build.advertiseAddr = advertiseAddr; return this; };

    public  Builder forceNewCluster(Boolean forceNewCluster) { build.forceNewCluster = forceNewCluster; return this; };

    public  Builder swarmSpec(SwarmSpec swarmSpec) { build.swarmSpec = swarmSpec; return this; };

    public SwarmInit build = new SwarmInit();
  }

  public static SwarmInit.Builder builder() {
    return new SwarmInit.Builder();
  }

  @JsonCreator
  static SwarmInit create(
      @JsonProperty("ListenAddr") final String listenAddr,
      @JsonProperty("AdvertiseAddr") final String advertiseAddr,
      @JsonProperty("ForceNewCluster") final Boolean forceNewCluster,
      @JsonProperty("Spec") final SwarmSpec swarmSpec) {
    return builder()
        .listenAddr(listenAddr)
        .advertiseAddr(advertiseAddr)
        .forceNewCluster(forceNewCluster)
        .swarmSpec(swarmSpec)
        .build;
  }
}
