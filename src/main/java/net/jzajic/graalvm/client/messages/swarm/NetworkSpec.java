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

import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import net.jzajic.graalvm.client.messages.AbstractBuilder;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkSpec {

  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @JsonProperty("DriverConfiguration")
  public Driver driverConfiguration;

  @Nullable
  @JsonProperty("IPv6Enabled")
  public Boolean ipv6Enabled;

  @Nullable
  @JsonProperty("Internal")
  public Boolean internal;

  @Nullable
  @JsonProperty("Attachable")
  public Boolean attachable;

  @Nullable
  @JsonProperty("IPAMOptions")
  public IpamOptions ipamOptions;

  @JsonCreator
  static NetworkSpec create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("DriverConfiguration") final Driver driver,
      @JsonProperty("IPv6Enabled") final Boolean ipv6Enabled,
      @JsonProperty("Internal") final Boolean internal,
      @JsonProperty("Attachable") final Boolean attachable,
      @JsonProperty("IPAMOptions") final IpamOptions ipamOptions) {
    final ImmutableMap<String, String> labelsT = AbstractBuilder.safeMap(labels);
    return new NetworkSpec(name, labelsT, driver, ipv6Enabled, internal, attachable,
        ipamOptions);
  }

	public NetworkSpec(String name, ImmutableMap<String, String> labels, Driver driverConfiguration, Boolean ipv6Enabled, Boolean internal, Boolean attachable, IpamOptions ipamOptions) {
		super();
		this.name = name;
		this.labels = labels;
		this.driverConfiguration = driverConfiguration;
		this.ipv6Enabled = ipv6Enabled;
		this.internal = internal;
		this.attachable = attachable;
		this.ipamOptions = ipamOptions;
	}
    
}
