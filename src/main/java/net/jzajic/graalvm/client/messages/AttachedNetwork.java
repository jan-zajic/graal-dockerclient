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
import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class AttachedNetwork {

  @Nullable
  @JsonProperty("Aliases")
  public ImmutableList<String> aliases;

  @Nullable
  @JsonProperty("NetworkID")
  public String networkId;

  @JsonProperty("EndpointID")
  public String endpointId;

  @JsonProperty("Gateway")
  public String gateway;

  @JsonProperty("IPAddress")
  public String ipAddress;

  @JsonProperty("IPPrefixLen")
  public Integer ipPrefixLen;

  @JsonProperty("IPv6Gateway")
  public String ipv6Gateway;

  @JsonProperty("GlobalIPv6Address")
  public String globalIPv6Address;

  @JsonProperty("GlobalIPv6PrefixLen")
  public Integer globalIPv6PrefixLen;

  @JsonProperty("MacAddress")
  public String macAddress;

  @JsonCreator
  static AttachedNetwork create(
      @JsonProperty("Aliases") final List<String> aliases,
      @JsonProperty("NetworkID") final String networkId,
      @JsonProperty("EndpointID") final String endpointId,
      @JsonProperty("Gateway") final String gateway,
      @JsonProperty("IPAddress") final String ipAddress,
      @JsonProperty("IPPrefixLen") final Integer ipPrefixLen,
      @JsonProperty("IPv6Gateway") final String ipv6Gateway,
      @JsonProperty("GlobalIPv6Address") final String globalIPv6Address,
      @JsonProperty("GlobalIPv6PrefixLen") final Integer globalIPv6PrefixLen,
      @JsonProperty("MacAddress") final String macAddress) {
    return new AttachedNetwork(
            AbstractBuilder.safeList(aliases),
            networkId, endpointId, gateway, ipAddress, ipPrefixLen,
            ipv6Gateway, globalIPv6Address, globalIPv6PrefixLen, macAddress);
  }

	public AttachedNetwork(ImmutableList<String> aliases, String networkId, String endpointId, String gateway, String ipAddress, Integer ipPrefixLen, String ipv6Gateway, String globalIPv6Address, Integer globalIPv6PrefixLen, String macAddress) {
		super();
		this.aliases = aliases;
		this.networkId = networkId;
		this.endpointId = endpointId;
		this.gateway = gateway;
		this.ipAddress = ipAddress;
		this.ipPrefixLen = ipPrefixLen;
		this.ipv6Gateway = ipv6Gateway;
		this.globalIPv6Address = globalIPv6Address;
		this.globalIPv6PrefixLen = globalIPv6PrefixLen;
		this.macAddress = macAddress;
	}
  
}
