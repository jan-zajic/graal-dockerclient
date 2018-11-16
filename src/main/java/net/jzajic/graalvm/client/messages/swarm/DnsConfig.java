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
public class DnsConfig {

  @Nullable
  @JsonProperty("Nameservers")
  public ImmutableList<String> nameServers;

  @Nullable
  @JsonProperty("Search")
  public ImmutableList<String> search;

  @Nullable
  @JsonProperty("Options")
  public ImmutableList<String> options;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder nameServers(String... nameServers) { build.nameServers = safeList(nameServers, true); return this; };

    public  Builder nameServers(List<String> nameServers) { build.nameServers = safeList(nameServers, true); return this; };
    
    public  Builder search(String... search) { build.search = safeList(search, true); return this; };

    public  Builder search(List<String> search) { build.search = safeList(search, true); return this; };
    
    public  Builder options(String... options) { build.options = safeList(options, true); return this; };

    public  Builder options(List<String> options) { build.options = safeList(options, true); return this; };

    public DnsConfig build = new DnsConfig();
  }

  public static Builder builder() {
    return new DnsConfig.Builder();
  }

  @JsonCreator
  static DnsConfig create(
      @JsonProperty("Nameservers") final List<String> nameServers,
      @JsonProperty("Search") final List<String> search,
      @JsonProperty("Options") final List<String> options) {
    return builder()
        .nameServers(nameServers)
        .search(search)
        .options(options)
        .build;
  }

}
