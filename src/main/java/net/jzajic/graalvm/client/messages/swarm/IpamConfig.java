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
public class IpamConfig {

  @JsonProperty("Subnet")
  public String subnet;

  @Nullable
  @JsonProperty("Range")
  public String range;

  @JsonProperty("Gateway")
  public String gateway;

  @JsonCreator
  static IpamConfig create(
      @JsonProperty("Subnet") final String subnet,
      @JsonProperty("Range") final String range,
      @JsonProperty("Gateway") final String gateway) {
    return new IpamConfig(subnet, range, gateway);
  }

	public IpamConfig(String subnet, String range, String gateway) {
		super();
		this.subnet = subnet;
		this.range = range;
		this.gateway = gateway;
	}
  
}
