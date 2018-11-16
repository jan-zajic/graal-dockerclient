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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class NodeDescription {

  @JsonProperty("Hostname")
  public String hostname;

  @JsonProperty("Platform")
  public Platform platform;

  @JsonProperty("Resources")
  public Resources resources;

  @JsonProperty("Engine")
  public EngineConfig engine;

  @JsonCreator
  static NodeDescription create(@JsonProperty("Hostname") final String hostname,
      @JsonProperty("Platform") final Platform platform,
      @JsonProperty("Resources") final Resources resources,
      @JsonProperty("Engine") final EngineConfig engine) {
    return new NodeDescription(hostname, platform, resources, engine);
  }

	public NodeDescription(String hostname, Platform platform, Resources resources, EngineConfig engine) {
		super();
		this.hostname = hostname;
		this.platform = platform;
		this.resources = resources;
		this.engine = engine;
	}

}
