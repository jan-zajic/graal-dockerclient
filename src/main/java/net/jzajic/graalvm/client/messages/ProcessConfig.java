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

import com.google.common.collect.ImmutableList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * An object that represents the JSON returned by the Docker API for an exec command's process
 * configuration.
 */

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ProcessConfig {

  @JsonProperty("privileged")
  public Boolean privileged;

  @Nullable
  @JsonProperty("user")
  public String user;

  @JsonProperty("tty")
  public Boolean tty;

  @JsonProperty("entrypoint")
  public String entrypoint;

  @JsonProperty("arguments")
  public ImmutableList<String> arguments;

  @JsonCreator
  static ProcessConfig create(
      @JsonProperty("privileged") final Boolean privileged,
      @JsonProperty("user") final String user,
      @JsonProperty("tty") final Boolean tty,
      @JsonProperty("entrypoint") final String entrypoint,
      @JsonProperty("arguments") final List<String> arguments) {
    return new ProcessConfig(privileged, user, tty, entrypoint,
    		AbstractBuilder.safeList(arguments));
  }

	public ProcessConfig(Boolean privileged, String user, Boolean tty, String entrypoint, ImmutableList<String> arguments) {
		super();
		this.privileged = privileged;
		this.user = user;
		this.tty = tty;
		this.entrypoint = entrypoint;
		this.arguments = arguments;
	}
  
}
