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
import com.fasterxml.jackson.annotation.JsonValue;


import com.google.common.collect.ImmutableMap;

import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Network {

  @JsonProperty("Name")
  public String name;

  @JsonProperty("Id")
  public String id;

  @JsonProperty("Scope")
  public String scope;

  @JsonProperty("Driver")
  public String driver;

  @JsonProperty("IPAM")
  public Ipam ipam;

  @Nullable
  @JsonProperty("Containers")
  public ImmutableMap<String, Container> containers;

  @Nullable
  @JsonProperty("Options")
  public ImmutableMap<String, String> options;
  
  @Nullable
  @JsonProperty("Internal")
  public Boolean internal;
  
  @Nullable
  @JsonProperty("EnableIPv6")
  public Boolean enableIPv6;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("Attachable")
  public Boolean attachable;

  @JsonCreator
  static Network create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Id") final String id,
      @JsonProperty("Scope") final String scope,
      @JsonProperty("Driver") final String driver,
      @JsonProperty("IPAM") final Ipam ipam,
      @JsonProperty("Containers") final Map<String, Container> containers,
      @JsonProperty("Options") final Map<String, String> options,
      @JsonProperty("Internal") final Boolean internal,
      @JsonProperty("EnableIPv6") final Boolean enableIPv6,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Attachable") final Boolean attachable) {
    final ImmutableMap<String, Container> containersCopy = AbstractBuilder.safeMap(containers);
    final ImmutableMap<String, String> optionsCopy = AbstractBuilder.safeMap(options);
    final ImmutableMap<String, String> labelsCopy = AbstractBuilder.safeMap(labels);
    return new Network(name, id, scope, driver, ipam, containersCopy, optionsCopy,
            internal, enableIPv6, labelsCopy, attachable);
  }
  
  public Network(String name, String id, String scope, String driver, Ipam ipam, ImmutableMap<String, Container> containers, ImmutableMap<String, String> options, Boolean internal, Boolean enableIPv6, ImmutableMap<String, String> labels, Boolean attachable) {
		super();
		this.name = name;
		this.id = id;
		this.scope = scope;
		this.driver = driver;
		this.ipam = ipam;
		this.containers = containers;
		this.options = options;
		this.internal = internal;
		this.enableIPv6 = enableIPv6;
		this.labels = labels;
		this.attachable = attachable;
	}

	@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
  public  static class Container {

    @Nullable
    @JsonProperty("Name")
    public String name;

    @JsonProperty("EndpointID")
    public String endpointId;

    @JsonProperty("MacAddress")
    public String macAddress;

    @JsonProperty("IPv4Address")
    public String ipv4Address;

    @JsonProperty("IPv6Address")
    public String ipv6Address;

    @JsonCreator
    static Container create(
        @JsonProperty("Name") final String name,
        @JsonProperty("EndpointID") final String endpointId,
        @JsonProperty("MacAddress") final String macAddress,
        @JsonProperty("IPv4Address") final String ipv4Address,
        @JsonProperty("IPv6Address") final String ipv6Address) {
      return new Container(
          name, endpointId, macAddress, ipv4Address, ipv6Address);
    }

		public Container(String name, String endpointId, String macAddress, String ipv4Address, String ipv6Address) {
			super();
			this.name = name;
			this.endpointId = endpointId;
			this.macAddress = macAddress;
			this.ipv4Address = ipv4Address;
			this.ipv6Address = ipv6Address;
		}
    
  }
  
  /**
   * Docker networks come in two kinds: built-in or custom. 
   */
  public enum Type {
    /** Predefined networks that are built-in into Docker. */
    BUILTIN("builtin"),
    /** Custom networks that were created by users. */
    CUSTOM("custom");
    
    private final String name;

    Type(final String name) {
      this.name = name;
    }

    @JsonValue
    public String getName() {
      return name;
    }
    
    @JsonCreator
    public static Type fromName(String name) {
    	for (Type typ : Type.values()) {
				if(typ.name.equals(name))
					return typ;
			}
    	return null;
    }
    
  }
}
