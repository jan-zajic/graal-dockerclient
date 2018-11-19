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
import com.fasterxml.jackson.annotation.JsonValue;


import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class PortConfig {

  public static final String PROTOCOL_TCP = "tcp";
  public static final String PROTOCOL_UDP = "udp";

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Protocol")
  public String protocol;

  @Nullable
  @JsonProperty("TargetPort")
  public Integer targetPort;

  @Nullable
  @JsonProperty("PublishedPort")
  public Integer publishedPort;

  @Nullable
  @JsonProperty("PublishMode")
  public PortConfigPublishMode publishMode;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder name(String name) { build.name = name; return this; };

    public  Builder protocol(String protocol) { build.protocol = protocol; return this; };

    public  Builder targetPort(Integer targetPort) { build.targetPort = targetPort; return this; };

    public  Builder publishedPort(Integer publishedPort) { build.publishedPort = publishedPort; return this; };

    public  Builder publishMode(PortConfigPublishMode publishMode) { build.publishMode = publishMode; return this; };

    public PortConfig build = new PortConfig();
  }

  public static PortConfig.Builder builder() {
    return new PortConfig.Builder();
  }

  @JsonCreator
  static PortConfig create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Protocol") final String protocol,
      @JsonProperty("TargetPort") final Integer targetPort,
      @JsonProperty("PublishedPort") final Integer publishedPort,
      @JsonProperty("PublishMode") final PortConfigPublishMode publishMode) {
    return builder()
        .name(name)
        .protocol(protocol)
        .targetPort(targetPort)
        .publishedPort(publishedPort)
        .publishMode(publishMode)
        .build;
  }

  public enum PortConfigPublishMode {
    INGRESS("ingress"),
    HOST("host");

    private final String name;

    PortConfigPublishMode(final String name) {
      this.name = name;
    }

    @JsonValue
    public String getName() {
      return name;
    }
    
    @JsonCreator
    public static PortConfigPublishMode fromName(String name) {
    	for (PortConfigPublishMode typ : PortConfigPublishMode.values()) {
				if(typ.name.equals(name))
					return typ;
			}
    	return null;
    }
    
  }
}
