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
public class IpamOptions {

  @JsonProperty("Driver")
  public Driver driver;

  @Nullable
  @JsonProperty("Configs")
  public ImmutableList<IpamConfig> configs;

  @JsonCreator
  static IpamOptions create(
      @JsonProperty("Driver") final Driver driver,
      @JsonProperty("Configs") final List<IpamConfig> configs) {
    final ImmutableList<IpamConfig> configsT = AbstractBuilder.safeList(configs);
    return new IpamOptions(driver, configsT);
  }

	public IpamOptions(Driver driver, ImmutableList<IpamConfig> configs) {
		super();
		this.driver = driver;
		this.configs = configs;
	}
  
}
