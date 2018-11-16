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


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Version {

  @JsonProperty("ApiVersion")
  public String apiVersion;

  @JsonProperty("Arch")
  public String arch;

  @Nullable
  @JsonProperty("BuildTime")
  public String buildTime;

  @JsonProperty("GitCommit")
  public String gitCommit;

  @JsonProperty("GoVersion")
  public String goVersion;

  @JsonProperty("KernelVersion")
  public String kernelVersion;

  @JsonProperty("Os")
  public String os;

  @JsonProperty("Version")
  public String version;

  @JsonCreator
  static Version create(
      @JsonProperty("ApiVersion") final String apiVersion,
      @JsonProperty("Arch") final String arch,
      @JsonProperty("BuildTime") final String buildTime,
      @JsonProperty("GitCommit") final String gitCommit,
      @JsonProperty("GoVersion") final String goVersion,
      @JsonProperty("KernelVersion") final String kernelVersion,
      @JsonProperty("Os") final String os,
      @JsonProperty("Version") final String version) {
    return new Version(apiVersion, arch, buildTime, gitCommit, goVersion, kernelVersion,
        os, version);
  }

	public Version(String apiVersion, String arch, String buildTime, String gitCommit, String goVersion, String kernelVersion, String os, String version) {
		super();
		this.apiVersion = apiVersion;
		this.arch = arch;
		this.buildTime = buildTime;
		this.gitCommit = gitCommit;
		this.goVersion = goVersion;
		this.kernelVersion = kernelVersion;
		this.os = os;
		this.version = version;
	}
  
}
