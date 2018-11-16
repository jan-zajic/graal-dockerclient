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
import com.google.common.collect.ImmutableMap;
import java.util.Date;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, setterVisibility = NONE, getterVisibility = NONE)
public class ContainerInfo {

  @Nullable
  @JsonProperty("Id")
  public String id;

  @JsonProperty("Created")
  public Date created;

  @JsonProperty("Path")
  public String path;

  @JsonProperty("Args")
  public ImmutableList<String> args;

  @JsonProperty("Config")
  public ContainerConfig config;

  @Nullable
  @JsonProperty("HostConfig")
  public HostConfig hostConfig;

  @JsonProperty("State")
  public ContainerState state;

  @JsonProperty("Image")
  public String image;

  @JsonProperty("NetworkSettings")
  public NetworkSettings networkSettings;

  @JsonProperty("ResolvConfPath")
  public String resolvConfPath;

  @JsonProperty("HostnamePath")
  public String hostnamePath;

  @JsonProperty("HostsPath")
  public String hostsPath;

  @JsonProperty("Name")
  public String name;

  @JsonProperty("Driver")
  public String driver;

  @Nullable
  @JsonProperty("ExecDriver")
  public String execDriver;

  @JsonProperty("ProcessLabel")
  public String processLabel;

  @JsonProperty("MountLabel")
  public String mountLabel;

  /**
   * Volumes returned by execInspect
   *
   * @return A map of volumes where the key is the source path on the local file system, and the key
   *         is the target path on the Docker host.
   * @deprecated Replaced by {@link #mounts()} in API 1.20.
   */
  @Nullable
  @Deprecated
  @JsonProperty("Volumes")
  public ImmutableMap<String, String> volumes;

  /**
   * Volumes returned by execInspect
   *
   * @return A map of volumes where the key is the source path on the local file system, and the key
   *         is the target path on the Docker host.
   * @deprecated Replaced by {@link #mounts()} in API 1.20.
   */
  @Nullable
  @Deprecated
  @JsonProperty("VolumesRW")
  public ImmutableMap<String, Boolean> volumesRw;

  @JsonProperty("AppArmorProfile")
  public String appArmorProfile;

  @Nullable
  @JsonProperty("ExecIDs")
  public ImmutableList<String> execIds;

  @JsonProperty("LogPath")
  public String logPath;

  @JsonProperty("RestartCount")
  public Long restartCount;

  @Nullable
  @JsonProperty("Mounts")
  public ImmutableList<ContainerMount> mounts;

  /**
   * This field is an extension defined by the Docker Swarm API, therefore it will only be populated
   * when communicating with a Swarm cluster.
   */
  @Nullable
  @JsonProperty("Node")
  public Node node;

  @JsonCreator
  static ContainerInfo create(
      @JsonProperty("Id") final String id,
      @JsonProperty("Created") final Date created,
      @JsonProperty("Path") final String path,
      @JsonProperty("Args") final List<String> args,
      @JsonProperty("Config") final ContainerConfig containerConfig,
      @JsonProperty("HostConfig") final HostConfig hostConfig,
      @JsonProperty("State") final ContainerState containerState,
      @JsonProperty("Image") final String image,
      @JsonProperty("NetworkSettings") final NetworkSettings networkSettings,
      @JsonProperty("ResolvConfPath") final String resolvConfPath,
      @JsonProperty("HostnamePath") final String hostnamePath,
      @JsonProperty("HostsPath") final String hostsPath,
      @JsonProperty("Name") final String name,
      @JsonProperty("Driver") final String driver,
      @JsonProperty("ExecDriver") final String execDriver,
      @JsonProperty("ProcessLabel") final String processLabel,
      @JsonProperty("MountLabel") final String mountLabel,
      @JsonProperty("Volumes") final Map<String, String> volumes,
      @JsonProperty("VolumesRW") final Map<String, Boolean> volumesRw,
      @JsonProperty("AppArmorProfile") final String appArmorProfile,
      @JsonProperty("ExecIDs") final List<String> execIds,
      @JsonProperty("LogPath") final String logPath,
      @JsonProperty("RestartCount") final Long restartCount,
      @JsonProperty("Mounts") final List<ContainerMount> mounts,
      @JsonProperty("Node") final Node node) {
    final ImmutableMap<String, String> volumesCopy = AbstractBuilder.safeMap(volumes);
    final ImmutableMap<String, Boolean> volumesRwCopy = AbstractBuilder.safeMap(volumesRw);
    final ImmutableList<String> execIdsCopy = AbstractBuilder.safeList(execIds);
    final ImmutableList<ContainerMount> mountsCopy = AbstractBuilder.safeList(mounts);
    return new ContainerInfo(
        id, created, path, AbstractBuilder.safeList(args), containerConfig, hostConfig, containerState,
        image, networkSettings, resolvConfPath, hostnamePath, hostsPath, name, driver, execDriver,
        processLabel, mountLabel, volumesCopy, volumesRwCopy,
        appArmorProfile, execIdsCopy, logPath, restartCount, mountsCopy, node);
  }
  
  public ContainerInfo(String id, Date created, String path, ImmutableList<String> args, ContainerConfig config, HostConfig hostConfig, ContainerState state, String image, NetworkSettings networkSettings, String resolvConfPath, String hostnamePath, String hostsPath, String name, String driver, String execDriver, String processLabel, String mountLabel, ImmutableMap<String, String> volumes, ImmutableMap<String, Boolean> volumesRw, String appArmorProfile, ImmutableList<String> execIds,
			String logPath, Long restartCount, ImmutableList<ContainerMount> mounts, Node node) {
		super();
		this.id = id;
		this.created = created;
		this.path = path;
		this.args = args;
		this.config = config;
		this.hostConfig = hostConfig;
		this.state = state;
		this.image = image;
		this.networkSettings = networkSettings;
		this.resolvConfPath = resolvConfPath;
		this.hostnamePath = hostnamePath;
		this.hostsPath = hostsPath;
		this.name = name;
		this.driver = driver;
		this.execDriver = execDriver;
		this.processLabel = processLabel;
		this.mountLabel = mountLabel;
		this.volumes = volumes;
		this.volumesRw = volumesRw;
		this.appArmorProfile = appArmorProfile;
		this.execIds = execIds;
		this.logPath = logPath;
		this.restartCount = restartCount;
		this.mounts = mounts;
		this.node = node;
	}



	public  static class Node {

    @JsonProperty("ID")
    public String id;

    @JsonProperty("IP")
    public String ip;

    @JsonProperty("Addr")
    public String addr;

    @JsonProperty("Name")
    public String name;

    @JsonCreator
    static Node create(
        @JsonProperty("ID") final String id,
        @JsonProperty("IP") final String ip,
        @JsonProperty("Addr") final String addr,
        @JsonProperty("Name") final String name) {
      return new Node(id, ip, addr, name);
    }

		public Node(String id, String ip, String addr, String name) {
			super();
			this.id = id;
			this.ip = ip;
			this.addr = addr;
			this.name = name;
		}
    
  }
}
