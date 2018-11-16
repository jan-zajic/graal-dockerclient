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

package net.jzajic.graalvm.client.messages;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class EndpointConfig {

  @Nullable
  @JsonProperty("IPAMConfig")
  public EndpointIpamConfig ipamConfig;

  @Nullable
  @JsonProperty("Links")
  public ImmutableList<String> links;

  @Nullable
  @JsonProperty("Aliases")
  public ImmutableList<String> aliases;

  @Nullable
  @JsonProperty("Gateway")
  public String gateway;

  @Nullable
  @JsonProperty("IPAddress")
  public String ipAddress;

  @Nullable
  @JsonProperty("IPPrefixLen")
  public Integer ipPrefixLen;

  @Nullable
  @JsonProperty("IPv6Gateway")
  public String ipv6Gateway;

  @Nullable
  @JsonProperty("GlobalIPv6Address")
  public String globalIPv6Address;

  @Nullable
  @JsonProperty("GlobalIPv6PrefixLen")
  public Integer globalIPv6PrefixLen;

  @Nullable
  @JsonProperty("MacAddress")
  public String macAddress;

  public static Builder builder() {
    return new EndpointConfig.Builder();
  }

  public  static class Builder extends AbstractBuilder {

    public  Builder ipamConfig(EndpointIpamConfig ipamConfig) { build.ipamConfig = ipamConfig; return this; };

    public  Builder links(List<String> links) { build.links = safeList(links); return this; };

    public  Builder aliases(ImmutableList<String> aliases) { build.aliases = aliases; return this; };

    public  Builder gateway(String gateway) { build.gateway = gateway; return this; };

    public  Builder ipAddress(String ipAddress) { build.ipAddress = ipAddress; return this; };

    public  Builder ipPrefixLen(Integer ipPrefixLen) { build.ipPrefixLen = ipPrefixLen; return this; };

    public  Builder ipv6Gateway(String ipv6Gateway) { build.ipv6Gateway = ipv6Gateway; return this; };

    public  Builder globalIPv6Address(String globalIPv6Address) { build.globalIPv6Address = globalIPv6Address; return this; };

    public  Builder globalIPv6PrefixLen(Integer globalIPv6PrefixLen) { build.globalIPv6PrefixLen = globalIPv6PrefixLen; return this; };

    public  Builder macAddress(String macAddress) { build.macAddress = macAddress; return this; };

    public EndpointConfig build = new EndpointConfig();
  }

  
  public  static class EndpointIpamConfig {

    @Nullable
    @JsonProperty("IPv4Address")
    public String ipv4Address;

    @Nullable
    @JsonProperty("IPv6Address")
    public String ipv6Address;

    @Nullable
    @JsonProperty("LinkLocalIPs")
    public ImmutableList<String> linkLocalIPs;

    public static Builder builder() {
      return new EndpointIpamConfig.Builder();
    }

    public  static class Builder extends AbstractBuilder {

      public  Builder ipv4Address(String ipv4Address) { build.ipv4Address = ipv4Address; return this; };

      public  Builder ipv6Address(String ipv6Address) { build.ipv6Address = ipv6Address; return this; };

      public  Builder linkLocalIPs(List<String> linkLocalIPs) { build.linkLocalIPs = safeList(linkLocalIPs); return this; };

      public EndpointIpamConfig build = new EndpointIpamConfig();
    }
  }
}
