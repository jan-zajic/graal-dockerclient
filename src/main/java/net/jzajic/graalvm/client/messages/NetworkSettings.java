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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkSettings {

  @Nullable
  @JsonProperty("IPAddress")
  public String ipAddress;

  @Nullable
  @JsonProperty("IPPrefixLen")
  public Integer ipPrefixLen;

  @Nullable
  @JsonProperty("Gateway")
  public String gateway;

  @Nullable
  @JsonProperty("Bridge")
  public String bridge;

  @Nullable
  @JsonProperty("PortMapping")
  public ImmutableMap<String, Map<String, String>> portMapping;

  @Nullable
  @JsonProperty("Ports")
  public ImmutableMap<String, List<PortBinding>> ports;

  @Nullable
  @JsonProperty("MacAddress")
  public String macAddress;

  @Nullable
  @JsonProperty("Networks")
  public ImmutableMap<String, AttachedNetwork> networks;

  @Nullable
  @JsonProperty("EndpointID")
  public String endpointId;

  @Nullable
  @JsonProperty("SandboxID")
  public String sandboxId;

  @Nullable
  @JsonProperty("SandboxKey")
  public String sandboxKey;

  @Nullable
  @JsonProperty("HairpinMode")
  public Boolean hairpinMode;

  @Nullable
  @JsonProperty("LinkLocalIPv6Address")
  public String linkLocalIPv6Address;

  @Nullable
  @JsonProperty("LinkLocalIPv6PrefixLen")
  public Integer linkLocalIPv6PrefixLen;

  @Nullable
  @JsonProperty("GlobalIPv6Address")
  public String globalIPv6Address;

  @Nullable
  @JsonProperty("GlobalIPv6PrefixLen")
  public Integer globalIPv6PrefixLen;

  @Nullable
  @JsonProperty("IPv6Gateway")
  public String ipv6Gateway;

  @JsonCreator
  static NetworkSettings create(
      @JsonProperty("IPAddress") final String ipAddress,
      @JsonProperty("IPPrefixLen") final Integer ipPrefixLen,
      @JsonProperty("Gateway") final String gateway,
      @JsonProperty("Bridge") final String bridge,
      @JsonProperty("PortMapping") final Map<String, Map<String, String>> portMapping,
      @JsonProperty("Ports") final Map<String, List<PortBinding>> ports,
      @JsonProperty("MacAddress") final String macAddress,
      @JsonProperty("Networks") final Map<String, AttachedNetwork> networks,
      @JsonProperty("EndpointID") final String endpointId,
      @JsonProperty("SandboxID") final String sandboxId,
      @JsonProperty("SandboxKey") final String sandboxKey,
      @JsonProperty("HairpinMode") final Boolean hairpinMode,
      @JsonProperty("LinkLocalIPv6Address") final String linkLocalIPv6Address,
      @JsonProperty("LinkLocalIPv6PrefixLen") final Integer linkLocalIPv6PrefixLen,
      @JsonProperty("GlobalIPv6Address") final String globalIPv6Address,
      @JsonProperty("GlobalIPv6PrefixLen") final Integer globalIPv6PrefixLen,
      @JsonProperty("IPv6Gateway") final String ipv6Gateway) {

    final ImmutableMap.Builder<String, List<PortBinding>> portsCopy = ImmutableMap.builder();
    if (ports != null) {
      for (final Map.Entry<String, List<PortBinding>> entry : ports.entrySet()) {
        portsCopy.put(entry.getKey(),
            entry.getValue() == null ? Collections.<PortBinding>emptyList() : entry.getValue());
      }
    }

    return builder()
        .ipAddress(ipAddress)
        .ipPrefixLen(ipPrefixLen)
        .gateway(gateway)
        .bridge(bridge)
        .portMapping(portMapping)
        .ports(portsCopy.build())
        .macAddress(macAddress)
        .networks(networks)
        .endpointId(endpointId)
        .sandboxId(sandboxId)
        .sandboxKey(sandboxKey)
        .hairpinMode(hairpinMode)
        .linkLocalIPv6Address(linkLocalIPv6Address)
        .linkLocalIPv6PrefixLen(linkLocalIPv6PrefixLen)
        .globalIPv6Address(globalIPv6Address)
        .globalIPv6PrefixLen(globalIPv6PrefixLen)
        .ipv6Gateway(ipv6Gateway)
        .build;
  }

  private static Builder builder() {
    return new NetworkSettings.Builder();
  }

  static class Builder extends AbstractBuilder {

    Builder ipAddress(String ipAddress) { build.ipAddress = ipAddress; return this; };

    Builder ipPrefixLen(Integer ipPrefixLen) { build.ipPrefixLen = ipPrefixLen; return this; };

    Builder gateway(String gateway) { build.gateway = gateway; return this; };

    Builder bridge(String bridge) { build.bridge = bridge; return this; };

    Builder portMapping(Map<String, Map<String, String>> portMapping) { build.portMapping = safeMap(portMapping); return this; };

    Builder ports(Map<String, List<PortBinding>> ports) { build.ports = safeMap(ports); return this; };

    Builder macAddress(String macAddress) { build.macAddress = macAddress; return this; };

    Builder networks(Map<String, AttachedNetwork> networks) { build.networks = safeMap(networks); return this; };

    Builder endpointId(final String endpointId) { build.endpointId = endpointId; return this; };

    Builder sandboxId(final String sandboxId) { build.sandboxId = sandboxId; return this; };

    Builder sandboxKey(final String sandboxKey) { build.sandboxKey = sandboxKey; return this; };

    Builder hairpinMode(final Boolean hairpinMode) { build.hairpinMode = hairpinMode; return this; };

    Builder linkLocalIPv6Address(final String linkLocalIPv6Address) { build.linkLocalIPv6Address = linkLocalIPv6Address; return this; };

    Builder linkLocalIPv6PrefixLen(final Integer linkLocalIPv6PrefixLen) { build.linkLocalIPv6PrefixLen = linkLocalIPv6PrefixLen; return this; };

    Builder globalIPv6Address(final String globalIPv6Address) { build.globalIPv6Address = globalIPv6Address; return this; };

    Builder globalIPv6PrefixLen(final Integer globalIPv6PrefixLen) { build.globalIPv6PrefixLen = globalIPv6PrefixLen; return this; };

    Builder ipv6Gateway(final String ipv6Gateway) { build.ipv6Gateway = ipv6Gateway; return this; };

    NetworkSettings build = new NetworkSettings();
  }
}
