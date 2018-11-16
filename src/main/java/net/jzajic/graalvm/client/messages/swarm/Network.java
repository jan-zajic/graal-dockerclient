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


import java.util.Date;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Network {

  @JsonProperty("ID")
  public String id;

  @JsonProperty("Version")
  public Version version;

  @JsonProperty("CreatedAt")
  public Date createdAt;

  @JsonProperty("UpdatedAt")
  public Date updatedAt;

  @JsonProperty("Spec")
  public NetworkSpec spec;

  @JsonProperty("DriverState")
  public Driver driverState;

  @JsonProperty("IPAMOptions")
  public IpamOptions ipamOptions;

  @JsonCreator
  static Network create(
      @JsonProperty("ID") final String id,
      @JsonProperty("Version") final Version version,
      @JsonProperty("CreatedAt") final Date createdAt,
      @JsonProperty("UpdatedAt") final Date updatedAt,
      @JsonProperty("Spec") final NetworkSpec spec,
      @JsonProperty("DriverState") final Driver driverState,
      @JsonProperty("IPAMOptions") final IpamOptions ipamOptions) {
    return new Network(id, version, createdAt, updatedAt, spec, driverState, ipamOptions);
  }

	public Network(String id, Version version, Date createdAt, Date updatedAt, NetworkSpec spec, Driver driverState, IpamOptions ipamOptions) {
		super();
		this.id = id;
		this.version = version;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.spec = spec;
		this.driverState = driverState;
		this.ipamOptions = ipamOptions;
	}
  
}
