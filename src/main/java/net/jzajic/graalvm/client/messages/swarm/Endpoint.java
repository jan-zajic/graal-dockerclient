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

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import net.jzajic.graalvm.client.messages.AbstractBuilder;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Endpoint {

  @JsonProperty("Spec")
  public EndpointSpec spec;

  @Nullable
  @JsonProperty("ExposedPorts")
  public ImmutableList<PortConfig> exposedPorts;

  @Nullable
  @JsonProperty("Ports")
  public ImmutableList<PortConfig> ports;

  @Nullable
  @JsonProperty("VirtualIPs")
  public ImmutableList<EndpointVirtualIp> virtualIps;

  @JsonCreator
  static Endpoint create(
      @JsonProperty("Spec") final EndpointSpec spec,
      @JsonProperty("ExposedPorts") final List<PortConfig> exposedPorts,
      @JsonProperty("Ports") final List<PortConfig> ports,
      @JsonProperty("VirtualIPs") final List<EndpointVirtualIp> virtualIps) {
    final ImmutableList<PortConfig> exposedPortsT = AbstractBuilder.safeList(exposedPorts);
    final ImmutableList<PortConfig> portsT = AbstractBuilder.safeList(ports);
    final ImmutableList<EndpointVirtualIp> virtualIpsT = AbstractBuilder.safeList(virtualIps);
    return new Endpoint(spec, exposedPortsT, portsT, virtualIpsT);
  }

	public Endpoint(EndpointSpec spec, ImmutableList<PortConfig> exposedPorts, ImmutableList<PortConfig> ports, ImmutableList<EndpointVirtualIp> virtualIps) {
		super();
		this.spec = spec;
		this.exposedPorts = exposedPorts;
		this.ports = ports;
		this.virtualIps = virtualIps;
	}
  
}
