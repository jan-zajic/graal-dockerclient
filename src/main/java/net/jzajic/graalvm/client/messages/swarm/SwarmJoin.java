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

import java.util.List;

import javax.annotation.Nullable;



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SwarmJoin {
  @JsonProperty("ListenAddr")
  public String listenAddr;

  @Nullable
  @JsonProperty("AdvertiseAddr")
  public String advertiseAddr;

  @JsonProperty("RemoteAddrs")
  public List<String> remoteAddrs;

  @JsonProperty("JoinToken")
  public String joinToken;

  public  static class Builder extends SwarmAbstractBuilder {
    public  Builder listenAddr(String listenAddr) { build.listenAddr = listenAddr; return this; };

    public  Builder advertiseAddr(String advertiseAddr) { build.advertiseAddr = advertiseAddr; return this; };

    public  Builder remoteAddrs(List<String> remoteAddrs) { build.remoteAddrs = remoteAddrs; return this; };

    public  Builder joinToken(String joinToken) { build.joinToken = joinToken; return this; };

    public SwarmJoin build = new SwarmJoin();
  }

  public static SwarmJoin.Builder builder() {
    return new SwarmJoin.Builder();
  }

  @JsonCreator
  static SwarmJoin create(
      @JsonProperty("ListenAddr") final String listenAddr,
      @JsonProperty("AdvertiseAddr") final String advertiseAddr,
      @JsonProperty("RemoteAddrs") final List<String> remoteAddrs,
      @JsonProperty("JoinToken") final String joinToken) {
    return builder()
        .listenAddr(listenAddr)
        .advertiseAddr(advertiseAddr)
        .remoteAddrs(remoteAddrs)
        .joinToken(joinToken)
        .build;
  }
}
