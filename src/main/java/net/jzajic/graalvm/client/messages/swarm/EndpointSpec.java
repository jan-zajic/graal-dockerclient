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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class EndpointSpec {

  public enum Mode {
    RESOLUTION_MODE_VIP("vip"),
    RESOLUTION_MODE_DNSRR("dnsrr");

    private final String value;

    Mode(final String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }

  @Nullable
  @JsonProperty("Mode")
  public Mode mode;

  @JsonProperty("Ports")
  public List<PortConfig> ports;

  Builder toBuilder() {
  	Builder builder = new Builder();
  	if(ports != null)
  		builder.portsBuilder.addAll(ports);
  	builder.build.mode = mode;
  	return builder;
  }

  public EndpointSpec withVipMode() {
    return toBuilder().mode(Mode.RESOLUTION_MODE_VIP).build;
  }

  public EndpointSpec withDnsrrMode() {
    return toBuilder().mode(Mode.RESOLUTION_MODE_DNSRR).build;
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder mode(Mode mode) { build.mode = mode; return this; };

    List<PortConfig> portsBuilder = new ArrayList<>();

    public Builder addPort(final PortConfig portConfig) {
      portsBuilder.add(portConfig);
      return this;
    }

    public  Builder ports(PortConfig... ports) { portsBuilder.addAll(Arrays.asList(ports)); return this; };

    public  Builder ports(List<PortConfig> ports) { portsBuilder.addAll(ports); return this; };

    public EndpointSpec build;
    
    public Builder() {
			build = new EndpointSpec();
			build.ports = Collections.unmodifiableList(portsBuilder);
		}
  }

  public static EndpointSpec.Builder builder() {
    return new EndpointSpec.Builder();
  }

  @JsonCreator
  static EndpointSpec create(
      @JsonProperty("Mode") final Mode mode,
      @JsonProperty("Ports") final List<PortConfig> ports) {
    final Builder builder = builder()
        .mode(mode);

    if (ports != null) {
      builder.ports(ports);
    }

    return builder.build;
  }
}
