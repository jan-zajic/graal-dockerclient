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

import javax.annotation.Nullable;

/**
 * An object that represents the JSON returned by the Docker API for low-level information about
 * exec commands.
 */

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ExecState {

  @JsonProperty("ID")
  public String id;

  @JsonProperty("Running")
  public Boolean running;

  @Nullable
  @JsonProperty("ExitCode")
  public Long exitCode;

  @JsonProperty("ProcessConfig")
  public ProcessConfig processConfig;

  @JsonProperty("OpenStdin")
  public Boolean openStdin;

  @JsonProperty("OpenStdout")
  public Boolean openStdout;

  @JsonProperty("OpenStderr")
  public Boolean openStderr;

  @Nullable
  @JsonProperty("Container")
  public ContainerInfo container;

  @Nullable
  @JsonProperty("ContainerID")
  public String containerId;
  
  @Nullable
  @JsonProperty("Pid")
  public String Pid; //since 1.25

  @JsonCreator
  static ExecState create(
      @JsonProperty("ID") final String id,
      @JsonProperty("Running") final Boolean running,
      @JsonProperty("ExitCode") final Long exitCode,
      @JsonProperty("ProcessConfig") final ProcessConfig processConfig,
      @JsonProperty("OpenStdin") final Boolean openStdin,
      @JsonProperty("OpenStdout") final Boolean openStdout,
      @JsonProperty("OpenStderr") final Boolean openStderr,
      @JsonProperty("Container") final ContainerInfo containerInfo,
      @JsonProperty("ContainerID") final String containerId,
      @JsonProperty("Pid") final String pid) {
    return new ExecState(id, running, exitCode, processConfig, openStdin, openStdout,
                                   openStderr, containerInfo, containerId, pid);
  }

	public ExecState(String id, Boolean running, Long exitCode, ProcessConfig processConfig, Boolean openStdin, Boolean openStdout, Boolean openStderr, 
			ContainerInfo container, String containerId, String pid) {
		super();
		this.id = id;
		this.running = running;
		this.exitCode = exitCode;
		this.processConfig = processConfig;
		this.openStdin = openStdin;
		this.openStdout = openStdout;
		this.openStderr = openStderr;
		this.container = container;
		this.containerId = containerId;
		this.Pid = pid;
	}
  
}
