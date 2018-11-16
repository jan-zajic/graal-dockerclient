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

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkConnection {

  @JsonProperty("Container")
  public String containerId;

  @Nullable
  @JsonProperty("EndpointConfig")
  public EndpointConfig endpointConfig;

  public static Builder builder() {
    return new NetworkConnection.Builder();
  }

  public  static class Builder extends AbstractBuilder {
    public  Builder containerId(String containerId) { build.containerId = containerId; return this; };

    public  Builder endpointConfig(EndpointConfig endpointConfig) { build.endpointConfig = endpointConfig; return this; };

    public NetworkConnection build = new NetworkConnection();
  }
}
